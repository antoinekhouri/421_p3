package commands;

import java.sql.*;
import java.util.ArrayList;

public class BuyTicket {

    public static boolean buyTicket(String venueName, String startTime, String actName, String eventDate, int sectionNo,
                                    int rowNo, int seatNo, String email, String ccno) throws SQLException {
        boolean availability;
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url,"cs421g13", "Comp421!") ;

        Statement stmt = con.createStatement();

        // Get availability of given seat
        String sqlString = "SELECT availability FROM ticket NATURAL JOIN offers NATURAL JOIN admits NATURAL JOIN venue" +
                " WHERE rowno = " + rowNo + " AND seatno = " + seatNo + " AND sectionno = " + sectionNo + " AND name = " + venueName +
                " AND eventdate = " + eventDate + " AND actname = " + actName + " AND starttime = " + startTime + ";";
        ResultSet rs = stmt.executeQuery(sqlString);

        // Check if seat exists for given event
        if(!rs.next())
            return false;

        // Check if seat is available
        availability = rs.getBoolean(0);
        if(!availability)
            return false;

        String ticketid = rs.getString(1);
        // Mark seat as unavailable
        sqlString = "UPDATE ticket SET availability = false WHERE ticketid = " + ticketid;
        stmt.executeUpdate(sqlString);

        //Assign Ticket to Customer
        sqlString = "INSERT INTO Buys VALUES (" + ticketid +", " + ccno + ", " + email + ")";
        stmt.executeUpdate(sqlString);

        rs.close();
        stmt.close();
        con.close();

        return true;
    }
}

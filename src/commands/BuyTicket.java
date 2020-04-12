package commands;

import java.sql.*;

public class BuyTicket {

    public static boolean buyTicket(String ticketId, String email, String ccno) {
        try {
            boolean availability;
            String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
            Connection con = DriverManager.getConnection(url, "cs421g13", "Comp421!");

            Statement stmt = con.createStatement();

            // Get availability of given seat
            String sqlString = "SELECT availability FROM ticket WHERE ticketid = '" + ticketId + "';";
            ResultSet rs = stmt.executeQuery(sqlString);
            // Check if seat exists for given event
            if (!rs.next())
                return false;

            // Check if seat is available
            availability = rs.getBoolean(1);
            System.out.println(rs.getBoolean(1));
            if (!availability)
                return false;

            // Mark seat as unavailable
            sqlString = "UPDATE ticket SET availability = false WHERE ticketid = '" + ticketId+"';";
            stmt.executeUpdate(sqlString);

            //Assign Ticket to Customer
            sqlString = "INSERT INTO Buys VALUES ('" + ticketId + "', '" + ccno + "', '" + email + "');";
            stmt.executeUpdate(sqlString);

            rs.close();
            stmt.close();
            con.close();

            return true;
        } catch (SQLException e)
        {
            Integer sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
            System.out.println(e.getMessage());
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            return false;
        }
    }
}
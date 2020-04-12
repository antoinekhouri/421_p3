package commands;

import java.sql.*;
import java.util.ArrayList;

public class GetPerformancesBetweenDates {

    public static ArrayList<String> getPerformacesBetweenDates(String startDate, String endDate, String actName) throws SQLException {
        ArrayList<String> result = new ArrayList<String>();
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url,"cs421g13", "Comp421!") ;

        Statement stmt = con.createStatement();

        String sqlString = "SELECT starttime, endtime, maxticketno, actname, genre, eventdate, minage, venueid" +
                "FROM Event NATURAL JOIN performs NATURAL JOIN performer" +
                "WHERE eventdate > " + startDate + " AND eventdate < " + endDate + " AND performername = " + actName + " " +
                "ORDER BY eventdate ASC;";
        ResultSet rs = stmt.executeQuery(sqlString);

        while (rs.next()) {
            Time startTime = rs.getTime(0);
            Time endTime = rs.getTime(1);
            int maxTicketNo = rs.getInt(2);
            String actname = rs.getString(3);
            String genre = rs.getString(4);
            Date eventDate = rs.getDate(5);
            int minAge = rs.getInt(6);
            int venueId = rs.getInt(7);
            String output = startTime.toString() + " " + endTime.toString() + " " + maxTicketNo + " " + actname + " " +
                    genre + " " + eventDate.toString() + " " + minAge + " " + venueId;
            result.add(output);
        }

        rs.close();
        stmt.close();
        con.close();

        return result;
    }
}

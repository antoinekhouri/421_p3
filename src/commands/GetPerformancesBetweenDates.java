package commands;

import java.sql.*;

import javax.swing.table.DefaultTableModel;

public class GetPerformancesBetweenDates {

    public static DefaultTableModel getPerformacesBetweenDates(String startDate, String endDate, String performerName) {

        try {
        	DefaultTableModel model = new DefaultTableModel(new String[]{ "Act name", "City", "Event Date", "Start time", "End time"}, 0);
            String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
            Connection con = DriverManager.getConnection(url, "cs421g13", "Comp421!");

            Statement stmt = con.createStatement();

            String sqlString = "SELECT starttime, endtime, maxticketno, actname, genre, eventdate, minage, venueid,city " +
                    "FROM event NATURAL JOIN venue NATURAL JOIN performs NATURAL JOIN performer " +
                    "WHERE eventdate > '" + startDate + "' AND eventdate < '" + endDate + "' AND performername = '" + performerName + "' " +
                    "ORDER BY eventdate ASC;";
            ResultSet rs = stmt.executeQuery(sqlString);

            while (rs.next()) {
                Time startTime = rs.getTime(1);
                Time endTime = rs.getTime(2);
                int maxTicketNo = rs.getInt(3);
                String actname = rs.getString(4);
                String genre = rs.getString(5);
                Date eventDate = rs.getDate(6);
                int minAge = rs.getInt(7);
                int venueId = rs.getInt(8);
                String city = rs.getString(9);
                model.addRow(new Object[] {actname, city, eventDate, startTime, endTime});

            }

            rs.close();
            stmt.close();
            con.close();
            
            return model;
        }
        catch (SQLException e)
        {
            Integer sqlCode = e.getErrorCode(); 
            String sqlState = e.getSQLState();
            String sqlMessage = e.getMessage();

            DefaultTableModel model = new DefaultTableModel(new String[]{"sqlCode", "sqlState", "sqlMessage"}, 0);
            model.addRow(new String[]{sqlCode.toString(), sqlState, sqlMessage});
           
            return model;
        }
    }
    
}
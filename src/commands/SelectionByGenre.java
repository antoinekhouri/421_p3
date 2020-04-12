package commands;

import java.sql.* ;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class SelectionByGenre {
	public static ArrayList<String> select_event_by_genre(String genre){
		ArrayList<String> array_output = new ArrayList<String>() ;
		try {
			
	        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";	  
	        Connection con = DriverManager.getConnection(url,"cs421g13", "Comp421!") ;
	
	        Statement stmt = con.createStatement();
		        
	        String sqlString = "SELECT * FROM Event WHERE genre = " + "'"+genre+"';";
	        ResultSet rs = stmt.executeQuery(sqlString) ;
	   
	        while (rs.next()) {
	            String actname = rs.getString(4) ;
	            Date eventdate = rs.getDate(6) ;
	            String output = "Act: " + actname + " Date: "+eventdate.toString() ;
	            array_output.add(output) ;
	        }
	        
	        rs.close();
	        stmt.close();
	        con.close();
	
	        return array_output;
			}
		catch (SQLException e)
        {
            Integer sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE
            System.out.println(e.getMessage());
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            array_output = new ArrayList<String>() ;
            array_output.add(e.getMessage());
            return array_output;
        }
		
		
		
    }
}

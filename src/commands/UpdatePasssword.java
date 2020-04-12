package commands;

import java.sql.* ;
import java.util.Date;
import java.text.SimpleDateFormat;

public class UpdatePasssword {
	public static String newpassword(String email, String oldpassword, String newpassword) {
		try {
			
			String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
			Connection con = DriverManager.getConnection (url,"cs421g13", "Comp421!") ;
			
			Statement stmt = con.createStatement();
		    String updateSQL = "UPDATE " +  "IndividualCustomer" + " SET password = '" + newpassword 
		    		+"' WHERE email = '" + email + "' AND " + "password='" + oldpassword + "';";
		    
		    int count = stmt.executeUpdate(updateSQL);
		    
		    if (count > 0) {
		    	return "Success";
		    	}
		    else {
		    	return "Update failed";
		    }
		    
			
		} catch (SQLException e){
			
			Integer sqlCode = e.getErrorCode(); // Get SQLCODE
			String sqlState = e.getSQLState(); // Get SQLSTATE
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            
            return e.getMessage();
		}
		
	}

}

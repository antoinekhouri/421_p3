package commands;

import java.sql.* ;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.Date;
import java.text.SimpleDateFormat;


public class RegistrationIndividual {
    public static String signup(String email, String password, String lastname, String firstname){

        
        try {
        	String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
            Connection con = DriverManager.getConnection (url,"cs421g13", "Comp421!") ;


            // Inserting Data into the table
            Statement stmt = con.createStatement();
            Date todayDate = new Date();
            String todayDateMod = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);

            String insertSQL =  "INSERT INTO " + "IndividualCustomer (email, signupDate, lastName , firstName, password)"
                    + " VALUES ('" + email + "','" + todayDateMod + "','" + lastname + "','" + firstname + "','" + password +"')";

            stmt.executeUpdate ( insertSQL ) ;
            System.out.println ( "DONE" ) ;
            stmt.close();
            con.close ();
            return "Success";

        } catch (SQLException e)
        {
            Integer sqlCode = e.getErrorCode(); // Get SQLCODE
            String sqlState = e.getSQLState(); // Get SQLSTATE

            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            return e.getMessage();
        }



    }

}

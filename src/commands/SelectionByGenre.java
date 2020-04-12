package commands;

import java.sql.* ;
import javax.swing.table.DefaultTableModel;

public class SelectionByGenre {
	public static DefaultTableModel select_event_by_genre(String genre){
		try {
			DefaultTableModel model = new DefaultTableModel(new String[]{"Performer Name", "Act name", "City", "Event Date"}, 0);
	        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";	  
	        Connection con = DriverManager.getConnection(url,"cs421g13", "Comp421!") ;
	
	        Statement stmt = con.createStatement();
		        
	        String sqlString = "SELECT actname, performername, city, eventdate "
	        		+ "FROM event NATURAL JOIN venue NATURAL JOIN performs NATURAL JOIN performer "
	        		+ "WHERE genre = " + "'"+genre+"';";
	   
	        ResultSet rs = stmt.executeQuery(sqlString) ;    
	   
	        while (rs.next()) {
	            String actname = rs.getString(1) ;
	            String performername = rs.getString(2) ;
	            String city = rs.getString(3) ;
	            Date eventdate = rs.getDate(4) ;

	            model.addRow(new Object[]{performername, actname , city , eventdate});
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

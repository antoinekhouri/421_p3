package view;

import java.awt.Color;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import commands.BuyTicket;
import commands.GetPerformancesBetweenDates;
import commands.RegistrationIndividual;
import commands.SelectionByGenre;
import commands.UpdatePasssword;

public class MainPage extends JFrame{

	private static final long serialVersionUID = 2012446709379049017L;
	private JLabel errorMessage;
	private String error = null;
	private String[] res = null;
	private JLabel resMessage;
	private JLabel dropDownLabel;
	private JComboBox<String> commandDropDown;
	private JButton goButton;
	private JScrollPane scrollpane;

	public MainPage() {
		init();
		refreshData();
	}

	private void init() {
		//Global settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Canadian Entertainment Ticket Center");
		setSize(600,200);

		//Elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setText("");

		//Individual element inits
		dropDownLabel = new JLabel();
		commandDropDown = new JComboBox<String>(new String[0]);
		goButton = new JButton();
		resMessage = new JLabel();
		scrollpane = new JScrollPane();

		//Default texts
		dropDownLabel.setText("Select the command to perform");
		goButton.setText("Go!");
		commandDropDown.addItem("--");
		
		commandDropDown.addItem("Register as Individual");
		commandDropDown.addItem("Update Password");
		commandDropDown.addItem("Buy Ticket");
		commandDropDown.addItem("Selection By Genre");
		commandDropDown.addItem("Get Performance Between Dates");
		
		resMessage.setText("The results will appear here");

		//layout
		JPanel daPanel = new JPanel();
		daPanel.add(errorMessage);
		daPanel.add(dropDownLabel);
		daPanel.add(commandDropDown);
		daPanel.add(goButton);
		daPanel.add(resMessage);
		daPanel.add(scrollpane);
		getContentPane().add(daPanel);

		GroupLayout layout = new GroupLayout(daPanel);
		layout.setAutoCreateContainerGaps(true);
		daPanel.setLayout(layout);

		GroupLayout.Group hg0 = layout.createParallelGroup();
		GroupLayout.Group hg1 = layout.createParallelGroup();

		GroupLayout.Group vg0 = layout.createParallelGroup();
		GroupLayout.Group vg1 = layout.createParallelGroup();
		GroupLayout.Group vg2 = layout.createParallelGroup();
		GroupLayout.Group vg3 = layout.createParallelGroup();


		//Set errormessage location
		hg1.addComponent(errorMessage);
		vg2.addComponent(errorMessage);

		//Set drop down location

		hg0.addComponent(dropDownLabel);
		vg0.addComponent(dropDownLabel);

		hg0.addComponent(commandDropDown);
		vg1.addComponent(commandDropDown);

		//Set go button location

		hg1.addComponent(goButton);
		vg1.addComponent(goButton);

		//Response
		hg0.addComponent(resMessage);
		vg2.addComponent(resMessage);
		
		hg0.addComponent(scrollpane);
		vg3.addComponent(scrollpane);

		//Make seq groups
		GroupLayout.SequentialGroup hseq1 = layout.createSequentialGroup();
		hseq1.addGroup(hg0);
		hseq1.addGroup(hg1);

		GroupLayout.SequentialGroup vseq1 = layout.createSequentialGroup();
		vseq1.addGroup(vg0);
		vseq1.addGroup(vg1);
		vseq1.addGroup(vg2);
		vseq1.addGroup(vg3);

		//Create horizontal layout
		layout.setHorizontalGroup(hseq1);

		//Create vertical layout
		layout.setVerticalGroup(vseq1);

		goButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				goButtonActionPerformed();
			}
		});

	}

	public void refreshData() {
		errorMessage.setText(error);
		error = "";
		if(error == null || error.length()==0) {

		}
		pack();
	}

	public void goButtonActionPerformed() {
		error = null;
		if(commandDropDown.getSelectedItem().toString().equals("--")) {
			error = "Please select a command to run.";
			refreshData();
			return;
		} else if(commandDropDown.getSelectedItem().toString().equals("Selection By Genre")) {
			//String genre = JOptionPane.showInputDialog("Enter the genre");
			String[] genreList = { "Pop", "Rock", "Alternative" };
		    String genre = (String) JOptionPane.showInputDialog(null, "Select the genre", "genre", 
		    		JOptionPane.QUESTION_MESSAGE, null,genreList, genreList[1]); 

			DefaultTableModel resTable = SelectionByGenre.select_event_by_genre(genre);
			
			// if error
			if (resTable.getColumnName(0).equals("sqlCode")) {
	            String sqlCode =  (String) resTable.getValueAt(0,0); 
	            String sqlState = (String) resTable.getValueAt(0,1);
	            String sqlMessage = (String) resTable.getValueAt(0,2);
	            error = sqlMessage;
				resMessage.setText("Unsuccessful operation");
				
				
			}
			else {
				JTable jresTable = new JTable(resTable);
		        scrollpane.setViewportView(new JScrollPane(jresTable)); 
				
			}
			refreshData();
			return;


		} else if(commandDropDown.getSelectedItem().toString().equals("Register as individual")) {
			String email = JOptionPane.showInputDialog("Enter your email");
			String password = JOptionPane.showInputDialog("Enter your password");
			String lastname = JOptionPane.showInputDialog("Enter your last name");
			String firstname = JOptionPane.showInputDialog("Enter your first name");
			String err = RegistrationIndividual.signup(email, password, lastname, firstname);
			if (!err.contentEquals("Success")) {
				error=err;
			}
			resMessage.setText(err);
			
			refreshData();
			return;
		} else if(commandDropDown.getSelectedItem().toString().equals("Buy Ticket")) {
 			String ticketid = JOptionPane.showInputDialog("Enter ticket id");
			String email = JOptionPane.showInputDialog("Enter email address");
			String ccno = JOptionPane.showInputDialog("Enter credit card number");
			boolean result = BuyTicket.buyTicket(ticketid, email, ccno);
			String resMsg = "";
			if(result)
				resMsg = "Ticket " + ticketid + " successfully purchased";
			else
				resMsg = ticketid + " was not able to be purchased. Check that this ticket is still available and " +
			"that you have entered your customer details correctly";
			resMessage.setText(resMsg);
		
		
		} else if(commandDropDown.getSelectedItem().toString().equals("Get Performance Between Dates")) {

			
			String startDate = JOptionPane.showInputDialog("Enter the earliest date (YYYY-MM-dd)");
			String endDate = JOptionPane.showInputDialog("Enter the latest date (YYYY-MM-dd)");
			String actName = JOptionPane.showInputDialog("Enter the performer");
			
			
			DefaultTableModel resTable =  GetPerformancesBetweenDates.getPerformacesBetweenDates(startDate, endDate, actName);
			
			// if error
			if (resTable.getColumnName(0).equals("sqlCode")) {
	            String sqlCode =  (String) resTable.getValueAt(0,0); 
	            String sqlState = (String) resTable.getValueAt(0,1);
	            String sqlMessage = (String) resTable.getValueAt(0,2);
	            error = sqlMessage;
				resMessage.setText("Unsuccessful operation");

				
			}
			else {
				JTable jresTable = new JTable(resTable);
		        scrollpane.setViewportView(new JScrollPane(jresTable)); 
				
			}
			refreshData();
	        return;
			
			
			
			
		}else if(commandDropDown.getSelectedItem().toString().equals("Update Password")) {
			String email = JOptionPane.showInputDialog("Enter your email");
			String oldPass = JOptionPane.showInputDialog("Enter your old password");
			String newPass1 = JOptionPane.showInputDialog("Enter your new password");
			String newPass2 = JOptionPane.showInputDialog("Enter your new password again");
			if(!newPass1.equals(newPass2)) {
				resMessage.setText("Password update unsuccessful.");
				error = "Passwords do not match";
				refreshData();
				return;
			}
			else {
				String res = UpdatePasssword.newpassword(email, oldPass, newPass2);
				if(res.equals("Success")) {
					resMessage.setText("Password successfully update.");
					refreshData();
					return;
				}
				else {
				resMessage.setText("Password update unsuccessful.");
				error = res;
				refreshData();
				}
			}
		}
		pack();
	}
}

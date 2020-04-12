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
import javax.swing.WindowConstants;

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

	public MainPage() {
		init();
		refreshData();
	}

	private void init() {
		//Global settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Weird Database App");
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

		//Default texts
		dropDownLabel.setText("Select the command to perform");
		goButton.setText("Go!");
		commandDropDown.addItem("--");
		commandDropDown.addItem("Selection By Genre");
		commandDropDown.addItem("Register as individual");
		commandDropDown.addItem("Buy Ticket");
		commandDropDown.addItem("Get Performance Between Dates");
		commandDropDown.addItem("Update Password");
		resMessage.setText("The results will appear here");

		//layout
		JPanel daPanel = new JPanel();
		daPanel.add(errorMessage);
		daPanel.add(dropDownLabel);
		daPanel.add(commandDropDown);
		daPanel.add(goButton);
		daPanel.add(resMessage);
		getContentPane().add(daPanel);

		GroupLayout layout = new GroupLayout(daPanel);
		layout.setAutoCreateContainerGaps(true);
		daPanel.setLayout(layout);

		GroupLayout.Group hg0 = layout.createParallelGroup();
		GroupLayout.Group hg1 = layout.createParallelGroup();

		GroupLayout.Group vg0 = layout.createParallelGroup();
		GroupLayout.Group vg1 = layout.createParallelGroup();
		GroupLayout.Group vg2 = layout.createParallelGroup();


		//Set errormessage location
		hg1.addComponent(errorMessage);
		vg2.addComponent(errorMessage);

		//Set drop down location

		hg0.addComponent(dropDownLabel);
		vg0.addComponent(dropDownLabel);

		hg0.addComponent(commandDropDown);
		vg1.addComponent(commandDropDown);

		//Set go button location

		hg0.addComponent(goButton);
		vg2.addComponent(goButton);

		//Response
		hg1.addComponent(resMessage);
		vg0.addComponent(resMessage);

		//Make seq groups
		GroupLayout.SequentialGroup hseq1 = layout.createSequentialGroup();
		hseq1.addGroup(hg0);
		hseq1.addGroup(hg1);

		GroupLayout.SequentialGroup vseq1 = layout.createSequentialGroup();
		vseq1.addGroup(vg0);
		vseq1.addGroup(vg1);
		vseq1.addGroup(vg2);

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
			String genre = JOptionPane.showInputDialog("Enter the genre");
			String resMsg = "";
			ArrayList<String> resList = SelectionByGenre.select_event_by_genre(genre);
			for (String s : resList) {
				resMsg = resMsg + "<br>" + s;
			}
			resMessage.setText("<html>"+ resMsg + "<html>");
			//			resMessage.setText("<html>line one<br> line two</html>");
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
			String venueName = JOptionPane.showInputDialog("Enter the venue name ");
			String startTime = JOptionPane.showInputDialog("Enter the start time");
			String actName = JOptionPane.showInputDialog("Enter the act name");
			String eventDate = JOptionPane.showInputDialog("Enter the event date (YYYY-MM-dd)");
			int sectionNo = 0;
			int rowNo = 0;
			int seatNo =0;
			try {
				sectionNo = Integer.parseInt(JOptionPane.showInputDialog("Enter the section number "));
				rowNo = Integer.parseInt(JOptionPane.showInputDialog("Enter the row number"));
				seatNo = Integer.parseInt(JOptionPane.showInputDialog("Enter the seat number"));
			} catch (Exception e) {
				error = "Section number, row number and seat number must be integers.";
				refreshData();
				return;
			}
			String email = JOptionPane.showInputDialog("Enter your email");
			String ccno = JOptionPane.showInputDialog("Enter your credit card number");
			try {
				boolean tf = BuyTicket.buyTicket(venueName, startTime, actName, eventDate, sectionNo, rowNo, seatNo, email, ccno);
				if(tf) {
					resMessage.setText("Ticket successfully purchased!");
				} else {
					resMessage.setText("Failed to purchase ticket");
				}
				refreshData();
				return;
			} catch (SQLException e) {
				error = e.getMessage();
				refreshData();
				return;
			}
		} else if(commandDropDown.getSelectedItem().toString().equals("Get Performance Between Dates")) {
			String startDate = JOptionPane.showInputDialog("Enter the earliest date (YYYY-MM-dd)");
			String endDate = JOptionPane.showInputDialog("Enter the latest date (YYYY-MM-dd)");
			String actName = JOptionPane.showInputDialog("Enter the act name");
			ArrayList<String> resList;
			try {
				resList = GetPerformancesBetweenDates.getPerformacesBetweenDates(startDate, endDate, actName);
			} catch (SQLException e) {
				error = e.getMessage();
				refreshData();
				return;
			}
			String resMsg = "";
			for (String s : resList) {
				resMsg = resMsg + "<br>" + s;
			}
			resMessage.setText("<html>"+ resMsg + "<html>");
		}else if(commandDropDown.getSelectedItem().toString().equals("Update Password")) {
			String email = JOptionPane.showInputDialog("Enter your email");
			String oldPass = JOptionPane.showInputDialog("Enter your old password");
			String newPass1 = JOptionPane.showInputDialog("Enter your new password");
			String newPass2 = JOptionPane.showInputDialog("Enter your new password again");
			if(!newPass1.equals(newPass2)) {
				error = "Passwords do not match";
				refreshData();
				return;
			}
			String res = UpdatePasssword.newpassword(email, oldPass, newPass2);
			if(res.equals("Success")) {
				resMessage.setText("Password successfully update.");
				refreshData();
				return;
			}
			resMessage.setText("Password update unsuccessful.");
			error = res;
			refreshData();
		}
		pack();
	}
}

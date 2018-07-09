package bank;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Displays all customer account data in a table. The displayed data include
 * customers' First Name, Last Name, Account Type, Account Number, Account
 * Balance, Gender, Date Of Birth, Marital Status, Address Lines, and Phone
 * Number.
 * 
 * @author Albert Jozsa-Kiraly
 */
public class DisplayTable extends JFrame {
	
	/* Size constants of the JTable */
	private static final int TABLE_WIDTH = 1300;
	private static final int TABLE_HEIGHT= 80;
	
	/**
	 * The launcher method which configures the frame and makes it visible.
	 */
	public static void displayTableWindow() {
		DisplayTable frame = new DisplayTable();

		// Keep the window of MainInterface.java open after this one is closed
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		frame.setTitle("Bank Accounts of All Customers");
		frame.createTable();
		frame.pack(); // sets preferred size for the frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * This method gets all customer account data from the database, and
	 * displays them in a table.
	 */
	public void createTable() {
		// Column names of the table
		String[] columnNames = { "First Name", "Last Name", "Account Type", "Account Number", "Account Balance",
				"Gender", "Date of Birth", "Marital Status", "Address Line 1", "Address Line 2", "Address Line 3",
				"Phone Number" };

		BankAccountBean bean = new BankAccountBean();

		Container window = getContentPane();
		window.setLayout(new GridLayout(1, 0));

		// Create table which displays the data
		JTable table = new JTable(bean.getAccountData(), columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		table.setFillsViewportHeight(true);

		// Create a scroll pane so the user can scroll through the records
		JScrollPane scrollPane = new JScrollPane(table);
		window.add(scrollPane);
	}
}
package bank;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * This class makes it possible for account owners at the bank to withdraw a
 * certain amount of cash from their account.
 * 
 * @author Albert Jozsa-Kiraly
 */
public class Withdraw extends JFrame implements ActionListener {
	
	/* Width and height constants of the window */
	public static final int FRAME_WIDTH = 458;
	public static final int FRAME_HEIGHT = 320;

	/* Other GUI size and positioning constants */
	private static final int TEXT_FIELD_HEIGHT = 21;
	private static final int LABEL_X = 15;    
	private static final int TEXT_FIELD_X = 175;
	private static final int SEARCH_LABEL_X = 7;
	private static final int SEARCH_LABEL_Y = 10;
	private static final int SEARCH_TEXT_FIELD_X = 195;
	private static final int SEARCH_TEXT_FIELD_Y = 35;
	private static final int SEARCH_BUTTON_X = 188;
	private static final int SEARCH_BUTTON_Y = 65;
	private static final int NAME_LABEL_Y = 110;
	private static final int NAME_TEXT_FIELD_Y = 108;
	private static final int ACCOUNT_NUMBER_LABEL_Y = 140;
	private static final int ACCOUNT_NUMBER_TEXT_FIELD_Y = 138;
	private static final int CURRENT_BALANCE_LABEL_Y = 170;
	private static final int CURRENT_BALANCE_TEXT_FIELD_Y = 168;
	private static final int AMOUNT_TO_WITHDRAW_LABEL_Y = 200;
	private static final int AMOUNT_TO_WITHDRAW_TEXT_FIELD_Y = 198;
	private static final int WITHDRAW_BUTTON_X = 163;
	private static final int WITHDRAW_BUTTON_Y = 240;
	private static final int START_AGAIN_BUTTON_X = 272;

	/* Account number length: 8 digits */
	private static final int SEARCH_TEXT_FIELD_WIDTH = 61;
	
	/* The font used for labels and buttons */
	private static final String LABEL_BUTTON_FONT = "Tahoma";
	
	/* Array list index constants. They are used in the action handler method.
	They make the code easier to read. */
	public static final int FIRST_NAME_INDEX = 0;
	public static final int LAST_NAME_INDEX = 1;
	public static final int GENDER_INDEX = 2;
	public static final int DATE_OF_BIRTH_INDEX = 3;
	public static final int MARITAL_STATUS_INDEX = 4;
	public static final int ADDRESS_LINE1_INDEX = 5;
	public static final int ADDRESS_LINE2_INDEX = 6;
	public static final int ADDRESS_LINE3_INDEX  = 7;
	public static final int PHONE_NUMBER_INDEX = 8;
	public static final int ACCOUNT_TYPE_INDEX = 9;
	public static final int ACCOUNT_BALANCE_INDEX = 10;
	public static final int ACCOUNT_NUMBER_INDEX = 11;
	public static final int CURRENT_ROW_INDEX = 12;
	
	/* These GUI components are declared as globals, so,
	they can be accessed in multiple methods. */
	private JTextField searchTextField;
	private JButton searchButton;
	private JButton startAgainButton;
	private JTextField nameTextField;
	private JTextField accountNumberTextField;
	private JTextField currentBalanceTextField;	
	private JTextField amountToWithdrawTextField;	
	private JButton withdrawButton;	
	
	/* These variables are declared as globals, because we want to update
	their values in the searchDatabase method, so that we can use them later
	to withdraw the money. */
	private int currentRow = 0;	
	private double currentBalance = 0;   

	/**
	 * The launcher method which configures the frame and makes it visible.
	 */
	public static void withdrawWindow()	{
		Withdraw frame = new Withdraw();

		// Keep the window of MainInterface.java open after this one is closed
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setTitle("Withdraw Cash");
		frame.setResizable(false); 
		frame.createGUI();                
		frame.setVisible(true);
	}

	/**
	 * Builds the graphical user interface.
	 * Creates the labels, text fields, and buttons, sets their font size, 
	 * position, and size, and adds them to the window.
	 */
	private void createGUI() {
		// Standard window set up
		Container window = getContentPane();

		/* The window layout is set to null as we will adjust the positions of
		components */
		window.setLayout(null);
		
		/* Only the search text field is editable and the search button is
		enabled at the start, since no account is found yet where the cash
		could be withdrawn from. */
		JLabel searchLabel = new JLabel("Search the account number from which the withdrawal should be made");
		searchLabel.setFont(new Font(LABEL_BUTTON_FONT, Font.PLAIN, 14));
		searchLabel.setBounds(SEARCH_LABEL_X, SEARCH_LABEL_Y, searchLabel.getPreferredSize().width,
				searchLabel.getPreferredSize().height);
		window.add(searchLabel);

		searchTextField = new JTextField("");
		searchTextField.setBounds(SEARCH_TEXT_FIELD_X, SEARCH_TEXT_FIELD_Y, SEARCH_TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		window.add(searchTextField);

		searchButton = new JButton("Search");
		searchButton.setFont(new Font(LABEL_BUTTON_FONT, Font.PLAIN, 14));
		searchButton.setBounds(SEARCH_BUTTON_X, SEARCH_BUTTON_Y, searchButton.getPreferredSize().width,
				searchButton.getPreferredSize().height);
		searchButton.addActionListener(this);
		window.add(searchButton);

		startAgainButton = new JButton("Clear all and start again");
		startAgainButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		startAgainButton.setBounds(START_AGAIN_BUTTON_X, SEARCH_BUTTON_Y, startAgainButton.getPreferredSize().width,
				startAgainButton.getPreferredSize().height);
		startAgainButton.addActionListener(this);
		startAgainButton.setEnabled(false);
		window.add(startAgainButton);

		JLabel nameLabel = new JLabel("Account holder's name:");
		nameLabel.setFont(new Font(LABEL_BUTTON_FONT, Font.PLAIN, 14));
		nameLabel.setBounds(LABEL_X, NAME_LABEL_Y, nameLabel.getPreferredSize().width,
				nameLabel.getPreferredSize().height);
		window.add(nameLabel);

		nameTextField = new JTextField("", 23);
		nameTextField.setBounds(TEXT_FIELD_X, NAME_TEXT_FIELD_Y, nameTextField.getPreferredSize().width,
				nameTextField.getPreferredSize().height);
		nameTextField.setEditable(false);
		window.add(nameTextField);

		JLabel accountNumberLabel = new JLabel("Account number:");
		accountNumberLabel.setFont(new Font(LABEL_BUTTON_FONT, Font.PLAIN, 14));
		accountNumberLabel.setBounds(LABEL_X, ACCOUNT_NUMBER_LABEL_Y, accountNumberLabel.getPreferredSize().width,
				accountNumberLabel.getPreferredSize().height);
		window.add(accountNumberLabel);

		accountNumberTextField = new JTextField("");
		accountNumberTextField.setBounds(TEXT_FIELD_X, ACCOUNT_NUMBER_TEXT_FIELD_Y, SEARCH_TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT);
		accountNumberTextField.setEditable(false);
		window.add(accountNumberTextField);

		JLabel currentBalanceLabel = new JLabel("Current balance:");
		currentBalanceLabel.setFont(new Font(LABEL_BUTTON_FONT, Font.PLAIN, 14));
		currentBalanceLabel.setBounds(LABEL_X, CURRENT_BALANCE_LABEL_Y, currentBalanceLabel.getPreferredSize().width,
				currentBalanceLabel.getPreferredSize().height);
		window.add(currentBalanceLabel);

		currentBalanceTextField = new JTextField("", 19);
		currentBalanceTextField.setBounds(TEXT_FIELD_X, CURRENT_BALANCE_TEXT_FIELD_Y,
				currentBalanceTextField.getPreferredSize().width, currentBalanceTextField.getPreferredSize().height);
		currentBalanceTextField.setEditable(false);
		window.add(currentBalanceTextField);

		JLabel amountToWithdrawLabel = new JLabel("Amount to withdraw:     £");
		amountToWithdrawLabel.setFont(new Font(LABEL_BUTTON_FONT, Font.PLAIN, 14));
		amountToWithdrawLabel.setBounds(LABEL_X, AMOUNT_TO_WITHDRAW_LABEL_Y,
				amountToWithdrawLabel.getPreferredSize().width, amountToWithdrawLabel.getPreferredSize().height);
		window.add(amountToWithdrawLabel);

		amountToWithdrawTextField = new JTextField("", 19);
		amountToWithdrawTextField.setBounds(TEXT_FIELD_X, AMOUNT_TO_WITHDRAW_TEXT_FIELD_Y,
				amountToWithdrawTextField.getPreferredSize().width,
				amountToWithdrawTextField.getPreferredSize().height);
		amountToWithdrawTextField.setEditable(false);
		window.add(amountToWithdrawTextField);

		withdrawButton = new JButton("Withdraw Cash");
		withdrawButton.setFont(new Font(LABEL_BUTTON_FONT, Font.PLAIN, 14));
		withdrawButton.setBounds(WITHDRAW_BUTTON_X, WITHDRAW_BUTTON_Y, withdrawButton.getPreferredSize().width,
				withdrawButton.getPreferredSize().height);
		withdrawButton.addActionListener(this);
		withdrawButton.setEnabled(false);
		window.add(withdrawButton);
	}

	/**
	 * The action handler method.
	 * 
	 * Actions to handle on button clicks:
	 * o Search the account number from which the withdrawal should be made
	 * o Clear all data and start searching again
	 * o Withdraw the money from the account
	 */
	public void actionPerformed(ActionEvent event) {	
		
		BankAccountBean bean = new BankAccountBean();
		
		/* If the Search button is clicked, and the entered account number is of
		correct length, search the account number in the database */
		if(event.getSource() == searchButton) {			
			if(searchTextField.getText().trim().length() == 8) {

				ArrayList<String> accountData = bean.searchAccount(searchTextField.getText());

				/* If the returned array list has elements, the account was
				found. Display the account owner's name, account number, and
				current balance in the text fields. */
				if (!accountData.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Account found");

					String displayedName = accountData.get(FIRST_NAME_INDEX) + " " + accountData.get(LAST_NAME_INDEX);
					nameTextField.setText(displayedName);
					accountNumberTextField.setText(accountData.get(ACCOUNT_NUMBER_INDEX));
					currentBalanceTextField.setText(accountData.get(ACCOUNT_BALANCE_INDEX));

					// Allow the user to make the withdrawal
					amountToWithdrawTextField.setEditable(true);
					amountToWithdrawTextField.requestFocus();
					startAgainButton.setEnabled(true);
					withdrawButton.setEnabled(true);

					/* These values are passed as parameters when calling the
					withdraw method on the bean object. */
					currentBalance = Double.parseDouble(accountData.get(ACCOUNT_BALANCE_INDEX)); 
					currentRow = Integer.parseInt(accountData.get(CURRENT_ROW_INDEX)); 
				}
				// If the array list has no elements, the account was not found
				else {
					JOptionPane.showMessageDialog(null, "There is no such account in the database");
					
					// Allow the user to start searching again
					clearAll();					
					searchTextField.setText("");					
					searchTextField.requestFocus(); 
				}					
			}
			/* If the entered account number is not of correct length, 
			notify the user */
			else {
				JOptionPane.showMessageDialog(null, "Please enter a valid account number");
			}
		}
		/* If the Start Again button is clicked, clear everything and allow the
		user to begin a new search. */
		else if (event.getSource() == startAgainButton) {
			clearAll();
		}
		/* If the Withdraw Cash button is clicked, withdraw the specified amount
		from the account */
		else if (event.getSource() == withdrawButton) {

			// If the amount was entered correctly, continue
			if (!amountToWithdrawTextField.getText().trim().isEmpty()) {
				
				double amount = Double.parseDouble(amountToWithdrawTextField.getText().trim());

				/* If the amount is greater than zero, and the current balance has enough money to 
				withdraw from it, show a dialog asking the user to confirm the withdrawal. 
				If they confirm it, make the withdrawal. */
				if (amount > 0 && currentBalance >= amount) {

					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to withdraw the amount of cash from this account?", "Warning",
							JOptionPane.YES_NO_OPTION);

					if (dialogResult == JOptionPane.YES_OPTION) {
						bean.withdraw(currentRow, currentBalance, amount);
						JOptionPane.showMessageDialog(null, "Cash withdrawn");
						clearAll();
					}
				}
				/* If the balance does not contain enough money to be withdrawn,
				tell the user */
				else if (currentBalance < amount) {
					JOptionPane.showMessageDialog(null, "Insufficient funds to withdraw from this account.");
				}
			}
			// If the amount to withdraw is not entered, notify the user
			else {
				JOptionPane.showMessageDialog(null, "Please enter a valid amount");
			}		
		}
	}

	/**
	 * This method is called when the Start Again button is clicked. Clears text
	 * fields, and sets the amount to deposit uneditable and the buttons
	 * disabled. Therefore, it allows the user to start a new search for an
	 * account.
	 */
	public void clearAll() {
		searchTextField.setText("");
		startAgainButton.setEnabled(false);
		nameTextField.setText("");
		accountNumberTextField.setText("");
		currentBalanceTextField.setText("");
		amountToWithdrawTextField.setText("");
		amountToWithdrawTextField.setEditable(false);
		withdrawButton.setEnabled(false);
		searchTextField.requestFocus();
	}
}
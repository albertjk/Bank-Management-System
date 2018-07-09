package bank;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class makes it possible for an account owner to transfer a certain
 * amount of cash from their account to another account.
 * 
 * @author Albert Jozsa-Kiraly
 */
public class Transfer extends JFrame implements ActionListener {
	
	/* Width and height constants of the window */
	private static final int FRAME_WIDTH = 622;
	private static final int FRAME_HEIGHT = 304;
        
    /* Other GUI size and positioning constants */
    private static final int TOP_LABEL_X = 122;
	private static final int TOP_LABEL_Y = 5;
	private static final int CHECK_LABEL_X = 157;
	private static final int CHECK_LABEL_Y = 20;
    private static final int ACCOUNT_LABEL_Y = 45; 
    private static final int REMITTER_ACCOUNT_LABEL_X = 36;
	private static final int REMITTER_ACCOUNT_NUMBER_TEXT_FIELD_X = 88;
	private static final int REMITTER_NAME_LABEL_X = 20;
	private static final int REMITTER_NAME_LABEL_Y = 95;
	private static final int REMITTER_NAME_CHECK_BUTTON_X = 154;
	private static final int REMITTER_NAME_TEXT_FIELD_X = 20;
	private static final int AVAILABLE_AMOUNT_LABEL_Y = 155;
	private static final int AVAILABLE_AMOUNT_TEXT_FIELD_X = 142;
	private static final int AVAILABLE_AMOUNT_TEXT_FIELD_Y = 154;
	private static final int PAYEE_ACCOUNT_LABEL_X = 395;
	private static final int PAYEE_ACCOUNT_NUMBER_TEXT_FIELD_X = 442;
	private static final int PAYEE_NAME_LABEL_X = 390;
	private static final int PAYEE_NAME_LABEL_Y = 95;
	private static final int PAYEE_NAME_CHECK_BUTTON_X = 508;
	private static final int PAYEE_NAME_TEXT_FIELD_X = 375;
    private static final int TEXT_FIELD_HEIGHT = 21;
    private static final int ACCOUNT_NUMBER_TEXT_FIELD_Y = 70;
    private static final int CHECK_BUTTON_Y = 91;
    private static final int NAME_TEXT_FIELD_Y = 127;    
    private static final int TRANSFERRED_AMOUNT_LABEL_X = 130;
    private static final int TRANSFERRED_AMOUNT_LABEL_Y = 187;
	private static final int TRANSFERRED_AMOUNT_TEXT_FIELD_X = 275;
    private static final int TRANSFERRED_AMOUNT_TEXT_FIELD_Y = 186;
    private static final int TRANSFER_CHARGE_LABEL_X = 214;
	private static final int TRANSFER_CHARGE_LABEL_Y = 213;
	private static final int MAKE_TRANSFER_BUTTON_X = 253;
	private static final int MAKE_TRANSFER_BUTTON_Y = 238;	
	
	/* Account number length: 8 digits */
    private static final int ACCOUNT_NUMBER_TEXT_FIELD_WIDTH = 61;
    
    /* The font used for labels */
	private static final String LABEL_FONT = "Tahoma";
    
    /* These GUI components are declared as globals, so,
	they can be accessed in multiple methods. */
	private JTextField remitterAccountNumberTextField;
	private JButton remitterNameCheckButton;
	private JTextField remitterNameTextField;
	private JTextField availableAmountTextField;
	private JTextField payeeAccountNumberTextField;
	private JButton payeeNameCheckButton;
	private JTextField payeeNameTextField;	
	private JTextField transferredAmountTextField;	 
	private JButton makeTransferButton;
	
	/* The remitter's balance must be stored in this global double because first
	we check the remitter account, then we get their account balance, and
	later when the transfer is about to be made, we check if the remitter's
	account has enough money to be transferred to the payee's account. */
	double remittersBalance = 0;
	
	/**
	 * The launcher method which configures the frame and makes it visible.
	 */
	public static void transferWindow() {
		Transfer frame = new Transfer();
		
		// Keep the window of MainInterface.java open after this one is closed
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setTitle("Transfer Cash");
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
		
		JLabel topLabel = new JLabel("Transfer cash to a third party account at Albert's bank");
		topLabel.setFont(new Font(LABEL_FONT, Font.BOLD, 14));
		topLabel.setBounds(TOP_LABEL_X, TOP_LABEL_Y, topLabel.getPreferredSize().width,
				topLabel.getPreferredSize().height);
		window.add(topLabel);

		JLabel checkLabel = new JLabel("Check that the accounts belong to the correct owners");
		checkLabel.setBounds(CHECK_LABEL_X, CHECK_LABEL_Y, checkLabel.getPreferredSize().width,
				checkLabel.getPreferredSize().height);
		window.add(checkLabel);

		JLabel remitterAccountLabel = new JLabel("Remitter's account number:");
		remitterAccountLabel.setFont(new Font(LABEL_FONT, Font.PLAIN, 14));
		remitterAccountLabel.setBounds(REMITTER_ACCOUNT_LABEL_X, ACCOUNT_LABEL_Y,
				remitterAccountLabel.getPreferredSize().width, remitterAccountLabel.getPreferredSize().height);
		window.add(remitterAccountLabel);

		remitterAccountNumberTextField = new JTextField("");
		remitterAccountNumberTextField.setBounds(REMITTER_ACCOUNT_NUMBER_TEXT_FIELD_X, ACCOUNT_NUMBER_TEXT_FIELD_Y,
				ACCOUNT_NUMBER_TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		window.add(remitterAccountNumberTextField);

		JLabel remitterNameLabel = new JLabel("Remitter's full name:");
		remitterNameLabel.setFont(new Font(LABEL_FONT, Font.PLAIN, 14));
		remitterNameLabel.setBounds(REMITTER_NAME_LABEL_X, REMITTER_NAME_LABEL_Y,
				remitterNameLabel.getPreferredSize().width, remitterNameLabel.getPreferredSize().height);
		window.add(remitterNameLabel);

		remitterNameCheckButton = new JButton("Check");
		Dimension sizeOfRemitterNameCheckButton = remitterNameCheckButton.getPreferredSize();
		remitterNameCheckButton.setBounds(REMITTER_NAME_CHECK_BUTTON_X, CHECK_BUTTON_Y,
				sizeOfRemitterNameCheckButton.width, sizeOfRemitterNameCheckButton.height);
		remitterNameCheckButton.addActionListener(this);
		window.add(remitterNameCheckButton);

		remitterNameTextField = new JTextField("", 20);
		Dimension sizeOfRemitterNameTextField = remitterNameTextField.getPreferredSize();
		remitterNameTextField.setBounds(REMITTER_NAME_TEXT_FIELD_X, NAME_TEXT_FIELD_Y,
				sizeOfRemitterNameTextField.width, sizeOfRemitterNameTextField.height);
		remitterNameTextField.setEditable(false);
		window.add(remitterNameTextField);

		JLabel availableAmountLabel = new JLabel("Available amount: £");
		availableAmountLabel.setFont(new Font(LABEL_FONT, Font.PLAIN, 14));
		availableAmountLabel.setBounds(REMITTER_NAME_TEXT_FIELD_X, AVAILABLE_AMOUNT_LABEL_Y,
				availableAmountLabel.getPreferredSize().width, availableAmountLabel.getPreferredSize().height);
		window.add(availableAmountLabel);

		availableAmountTextField = new JTextField("", 9);
		availableAmountTextField.setBounds(AVAILABLE_AMOUNT_TEXT_FIELD_X, AVAILABLE_AMOUNT_TEXT_FIELD_Y,
				availableAmountTextField.getPreferredSize().width, availableAmountTextField.getPreferredSize().height);
		availableAmountTextField.setEditable(false);
		window.add(availableAmountTextField);

		JLabel payeeAccountLabel = new JLabel("Payee's account number:");
		payeeAccountLabel.setFont(new Font(LABEL_FONT, Font.PLAIN, 14));

		payeeAccountLabel.setBounds(PAYEE_ACCOUNT_LABEL_X, ACCOUNT_LABEL_Y, payeeAccountLabel.getPreferredSize().width,
				payeeAccountLabel.getPreferredSize().height);
		window.add(payeeAccountLabel);

		payeeAccountNumberTextField = new JTextField("");
		payeeAccountNumberTextField.setBounds(PAYEE_ACCOUNT_NUMBER_TEXT_FIELD_X, ACCOUNT_NUMBER_TEXT_FIELD_Y,
				ACCOUNT_NUMBER_TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		window.add(payeeAccountNumberTextField);

		JLabel payeeNameLabel = new JLabel("Payee's full name:");
		payeeNameLabel.setFont(new Font(LABEL_FONT, Font.PLAIN, 14));
		payeeNameLabel.setBounds(PAYEE_NAME_LABEL_X, PAYEE_NAME_LABEL_Y, payeeNameLabel.getPreferredSize().width,
				payeeNameLabel.getPreferredSize().height);
		window.add(payeeNameLabel);

		payeeNameCheckButton = new JButton("Check");
		payeeNameCheckButton.setBounds(PAYEE_NAME_CHECK_BUTTON_X, CHECK_BUTTON_Y, sizeOfRemitterNameCheckButton.width,
				sizeOfRemitterNameCheckButton.height);
		payeeNameCheckButton.addActionListener(this);
		window.add(payeeNameCheckButton);

		payeeNameTextField = new JTextField("", 20);
		payeeNameTextField.setBounds(PAYEE_NAME_TEXT_FIELD_X, NAME_TEXT_FIELD_Y, sizeOfRemitterNameTextField.width,
				sizeOfRemitterNameTextField.height);
		payeeNameTextField.setEditable(false);
		window.add(payeeNameTextField);

		JLabel transferredAmountLabel = new JLabel("Transferred amount:  £");
		transferredAmountLabel.setFont(new Font(LABEL_FONT, Font.PLAIN, 14));
		transferredAmountLabel.setBounds(TRANSFERRED_AMOUNT_LABEL_X, TRANSFERRED_AMOUNT_LABEL_Y,
				transferredAmountLabel.getPreferredSize().width, transferredAmountLabel.getPreferredSize().height);
		window.add(transferredAmountLabel);

		transferredAmountTextField = new JTextField("", 10);
		transferredAmountTextField.setBounds(TRANSFERRED_AMOUNT_TEXT_FIELD_X, TRANSFERRED_AMOUNT_TEXT_FIELD_Y,
				transferredAmountTextField.getPreferredSize().width,
				transferredAmountTextField.getPreferredSize().height);
		window.add(transferredAmountTextField);

		JLabel transferChargeLabel = new JLabel("Every transfer incurs a £1 charge");
		transferChargeLabel.setBounds(TRANSFER_CHARGE_LABEL_X, TRANSFER_CHARGE_LABEL_Y,
				transferChargeLabel.getPreferredSize().width, transferChargeLabel.getPreferredSize().height);
		window.add(transferChargeLabel);

		makeTransferButton = new JButton("Make Transfer");
		makeTransferButton.setBounds(MAKE_TRANSFER_BUTTON_X, MAKE_TRANSFER_BUTTON_Y,
				makeTransferButton.getPreferredSize().width, makeTransferButton.getPreferredSize().height);
		makeTransferButton.addActionListener(this);
		window.add(makeTransferButton);
	}
	
	/**
	 * The action handler method
	 * 
	 * Actions to handle on button clicks:
	 * o Get the remitter's account number and check their name and available balance
     * o Get the payee's account number and check their name
     * These checks are to ensure that the transfer will happen between the correct 
     * accounts, and that the remitter has enough money to transfer from.
     * o Make the transfer between the two accounts
	 */
	public void actionPerformed(ActionEvent event) {
		
		BankAccountBean bean = new BankAccountBean();
		
		/* If the Remitter's name check button is clicked, 
		and the entered account number is of correct length,
		call the method to check the remitter's name and balance,
		and display them in the text fields. */
		if (event.getSource() == remitterNameCheckButton) {
			if (remitterAccountNumberTextField.getText().trim().length() == 8) {

				String[] remittersAccount = bean.getAccountOwner(remitterAccountNumberTextField.getText());
				
				// Get the remitter's name from the returned array
				String name = remittersAccount[0];
				
				/* Get the balance. This is essential, so the value can be
				checked later when the Make Transfer button is clicked but
				before the transfer is made. */
				remittersBalance = Double.parseDouble(remittersAccount[1]);			
				
				if (name != null) {
					remitterNameTextField.setText(name);
					availableAmountTextField.setText(String.valueOf(remittersBalance));
					
					/* The account number text field is not editable, since the
					account number cannot be changed. */
					remitterAccountNumberTextField.setEditable(false);
				}
				/* If the account was not found, allow the user 
				to start searching for a new account. */
				else {
					JOptionPane.showMessageDialog(null, "Account not found in the database");
					remitterAccountNumberTextField.setText("");
					remitterAccountNumberTextField.requestFocus();  
				}
			}
			/* If the entered account number is not of correct length, 
			notify the user */
			else {
				JOptionPane.showMessageDialog(null, "Please enter a valid account number");
			}
		}
		/* If the Payee's name check button is clicked, 
		and the entered account number is of correct length, 
		call the method to check the payee's name 
		and display it in the text field */
		else if (event.getSource() == payeeNameCheckButton) {
			if (payeeAccountNumberTextField.getText().trim().length() == 8) {
				
				// Get the payee's name from the returned array
				String name = bean.getAccountOwner(payeeAccountNumberTextField.getText())[0];				
				
				/* The account number text field is not editable, since the
				account number cannot be changed. */
				if (name != null) {
					payeeNameTextField.setText(name);
					payeeAccountNumberTextField.setEditable(false);
				}
				/* If the account was not found, allow the user 
				to start searching for a new account. */
				else {
					JOptionPane.showMessageDialog(null, "Account not found in the database");
					payeeAccountNumberTextField.setText("");
					payeeAccountNumberTextField.requestFocus();
				}
			}
			/* If the entered account number is not of correct length, 
			notify the user */
			else {
				JOptionPane.showMessageDialog(null, "Please enter a valid account number");
			}
		}
		/* If the Make Transfer button is clicked, check that the length of the
		amount to transfer is greater than zero. */
		else if (event.getSource() == makeTransferButton) {
			if (!transferredAmountTextField.getText().trim().isEmpty()) {	
				
				double amount = Double.parseDouble(transferredAmountTextField.getText().trim());
				
				/* If the amount is greater than zero, and the current balance
				has enough money to transfer from it (including the £1
				charge), show a dialog asking the user to confirm the
			 	transfer. If they confirm it, make the transfer. */
				if (amount > 0 && remittersBalance - 1 >= amount) {
				
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Are you sure you would like to make the transfer?", "Warning", JOptionPane.YES_NO_OPTION);		
					
					/* The transfer is done by calling 2 methods from
					BankAccountBean. First, the transferred amount from the
					remitter's account is debited. Then, the transferred
					amount to the payee's account is credited. */
					if (dialogResult == JOptionPane.YES_OPTION) {
						bean.debitTransferredAmount(amount, remitterAccountNumberTextField.getText());
						bean.creditTransferredAmount(amount, payeeAccountNumberTextField.getText());

						JOptionPane.showMessageDialog(null, "Payment transferred");

						/* Clear the remitter and payee fields, so, a new search
						can be started */
						remitterAccountNumberTextField.setEditable(true);
						remitterAccountNumberTextField.setText("");
						remitterNameTextField.setText("");
						availableAmountTextField.setText("");
						payeeAccountNumberTextField.setEditable(true);
						payeeAccountNumberTextField.setText("");
						payeeNameTextField.setText("");
						transferredAmountTextField.setText("");
					}
				}
				/* If the remitter's account balance does not have enough money,
				tell the user */
				else if (remittersBalance - 1 < amount) {
					JOptionPane.showMessageDialog(null, "Insufficient funds to transfer from the remitter's account.");
				}
			}
			// If the amount to transfer is not entered, notify the user
			else {
				JOptionPane.showMessageDialog(null, "Please enter a valid amount to transfer");
			}
		}
	}	
}
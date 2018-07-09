package bank;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * This is the controller bean class through which
 * every communication with the database is accomplished.
 * To ensure that a connection is established before doing 
 * bank operations, each method first connects to the 
 * database. The Connection and Statement objects are 
 * closed at the end of each method.
 * 
 * @author Albert Jozsa-Kiraly 
 */
public class BankAccountBean {
	
	private static final String DB_URL = "jdbc:derby://localhost:1527/BankAccountDB";
	private static final String DB_USERNAME = "username";
	private static final String DB_PASSWORD = "password";

	private static final Logger logger = Logger.getAnonymousLogger();

	/* The Connection, Statement, and ResultSet objects. These are reachable in
	the methods below which require access to the database. */
	Connection myConnection;
	Statement myStatement;
	ResultSet results;

	/**
	 * This method sets up the connection to the database.    
	 * This is called at the start of each method of this class.
	 */
	public void doConnect() {
		try {
			myConnection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			/* Execute the SQL query and load all rows from the database table
			to the ResultSet */
			String query = "SELECT * FROM ALBERT.BANKACCOUNT";
			results = myStatement.executeQuery(query);

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		}
	}

	/**
	 * The constructor instantiates a new BankAccountBean.
	 * Its body is intentionally left blank, as we use this 
	 * in the other classes only to call methods of this class. 
	 * 
	 */
	public BankAccountBean() {}	

	/**
	 * Adds a new bank account to the database. Assigns a new ID to the
	 * customer.
	 * 
	 * @param account
	 *            the account to be added to the database
	 */
	public void createAccount(BankAccount account) {
		try {
			doConnect(); // Connect to the database

			int newID = 0;

			/* If there are rows in the table, get the ID from the last row and
			increment it, so, we get the new ID */
			if (results.last()) {
				newID = results.getInt("CustomerID") + 1;
			}
			// If the table has no rows, the ID will be 1
			else if (!results.last()) {
				results.first();
				newID = 1;
			}

			/* Move to a new row and update all cells in that row with the
			account data */
			results.moveToInsertRow();
			results.updateInt("CustomerID", newID);
			results.updateString("FirstName", account.getFirstName());
			results.updateString("LastName", account.getLastName());
			results.updateString("Gender", account.getGender());
			results.updateString("DateOfBirth", account.getDateOfBirth());
			results.updateString("MaritalStatus", account.getMaritalStatus());
			results.updateString("AddressLine1", account.getAddressLine1());
			results.updateString("AddressLine2", account.getAddressLine2());
			results.updateString("AddressLine3", account.getAddressLine3());
			results.updateString("PhoneNumber", account.getPhoneNumber());
			results.updateString("AccountType", account.getAccountType());
			results.updateString("AccountNumber", account.getAccountNumber());
			results.updateDouble("AccountBalance", account.getBalance());
			results.insertRow();

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
	}

	/**
	 * This method generates eight random numbers between 0 and 9 inclusive,
	 * converts them to strings, and concatenates them which will result in a
	 * new random account number to allocate to a new account when it is
	 * created. The method also checks whether the generated account number is
	 * already allocated to an account in the database.
	 * 
	 * @return the new randomly generated account number
	 */
	public String generateRandomAccountNumber() {
		String randomAccountNumber = null;
		try {
			doConnect(); // Connect to the database			

			/* Generate 8 random numbers, convert them to strings, 
             and put them into an array. Concatenate the elements 
             to make a single string. */
			
			String[] randomNumbers = new String[8];
			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < 8; i++) {
				int randomNumber = (int) (Math.random() * (9 - 0 + 1) + 0);
				randomNumbers[i] = String.valueOf(randomNumber);
				builder.append(randomNumbers[i]);
			}
			randomAccountNumber = builder.toString();	

			// Move to the last row and get the number of rows
			results.last();
			int numberOfRows = results.getRow();		
			
			/* Loop through all existing rows and check whether 
			 the new account number is already allocated to 
			 an existing account. If so, a new number is generated. */
			if (numberOfRows > 0) {
				for (int i = 1; i <= numberOfRows; i++) {
					results.absolute(i);

					if (results.getString("AccountNumber").equals(randomAccountNumber)) {
						generateRandomAccountNumber();
					}
				}				
			}	
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
		return randomAccountNumber;
	}

	/**
	 * Updates (overwrites) a customer's account data in the database table.
	 * 
	 * @param account
	 *            the account to be updated
	 * @param rowNumber
	 *            the row where the account is located in the table
	 */
	public void updateAccount(BankAccount account, int rowNumber) {
		try {
			doConnect(); // Connect to the database

			// Move to the specified row and update the data in the ResultSet
			results.absolute(rowNumber);
			results.updateString("FirstName", account.getFirstName());
			results.updateString("LastName", account.getLastName());
			results.updateString("Gender", account.getGender());
			results.updateString("DateOfBirth", account.getDateOfBirth());
			results.updateString("MaritalStatus", account.getMaritalStatus());
			results.updateString("AddressLine1", account.getAddressLine1());
			results.updateString("AddressLine2", account.getAddressLine2());
			results.updateString("AddressLine3", account.getAddressLine3());
			results.updateString("PhoneNumber", account.getPhoneNumber());
			results.updateString("AccountType", account.getAccountType());
			results.updateRow(); // Update the database table

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
	}

	/**
	 * Deletes an account from the specified row in the database table. Then, it
	 * decrements each remaining row's customer ID by one.
	 * 
	 * @param rowNumber
	 *            the row where the account to be deleted is located in the
	 *            table
	 */
	public void deleteAccount(int rowNumber) {
		try {			
			doConnect(); // Connect to the database	
			
			// Move to the specified row and delete it from the ResultSet
			results.absolute(rowNumber);
			results.deleteRow(); 
			
			// Close these objects to make changes to the database
			myConnection.close();
			myStatement.close();

			doConnect(); // Re-connect to the database	
			
			// Move to the last row and store the number of rows
			results.last();
			int numberOfRows = results.getRow();

			// Then move back to the previously specified row
			results.absolute(rowNumber); 

			/* Loop through the rows starting from the specified row till the
			last one and decrement each row's ID by one to get the new order. */
			for (int i = rowNumber; i <= numberOfRows; i++) {
				results.absolute(i);
				results.updateInt("CustomerID", i);
				results.updateRow();
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);

		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
	}

	/**
	 * Finds and stores customer account data in a two-dimensional array as rows
	 * and columns, then returns it. If there is no data in the database yet, an
	 * empty array is returned.
	 * 
	 * @return the array storing the account data if the account was found
	 */
	public String[][] getAccountData() {	
		try	{			
			doConnect(); // Connect to the database	
			
			// If there are rows in the database table, get the data			
			if(results.last()) {

				// Store the number of table rows
				int numberOfRows = results.getInt("CustomerID");

				String[][] customerData = new String[numberOfRows][12];					

				/* Loop over all rows, get the data, and return the array
				storing it. Move before the first row, so, the loop can start
				with the first row. */
				results.beforeFirst(); 
				for (int i = 0; i < numberOfRows; i++) {
					
					results.next();
					customerData[i][0] = results.getString("FirstName");
					customerData[i][1] = results.getString("LastName");
					customerData[i][2] = results.getString("AccountType");
					customerData[i][3] = results.getString("AccountNumber");
					customerData[i][4] = "£" + results.getString("AccountBalance");
					customerData[i][5] = results.getString("Gender");
					customerData[i][6] = results.getString("DateOfBirth");
					customerData[i][7] = results.getString("MaritalStatus");
					customerData[i][8] = results.getString("AddressLine1");
					customerData[i][9] = results.getString("AddressLine2");
					customerData[i][10] = results.getString("AddressLine3");
					customerData[i][11] = results.getString("PhoneNumber");
				}
				return customerData;
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
		/* If there are no rows with data, return an empty array with space 
		for column names */
		return new String[0][12];			
	}	

	/**
	 * This method takes an account number and searches the database to find the
	 * name of the account owner and the account balance.
	 * 
	 * @param searchedAccountNumber
	 *            the searched account number
	 * @return a string array storing the name of the account owner and the
	 *         account balance if the account was found
	 */
	public String[] getAccountOwner(String searchedAccountNumber) {
		String[] returnedData = new String[2];
		try {
			doConnect(); // Connect to the database

			// Store the number of rows
			results.last();
			int numberOfRows = results.getInt("CustomerID");

			/* Search each row of records and find the account by checking the
			account numbers. */
			for (int i = 1; i <= numberOfRows; i++) {
				results.absolute(i);

				/* If the account is found, store the account holder's name and
				balance in the array to be returned. */
				if (results.getString("AccountNumber").equals(searchedAccountNumber)) {
					returnedData[0] = results.getString("FirstName") + " " + results.getString("LastName");
					returnedData[1] = results.getString("AccountBalance");
					break;
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
		return returnedData;
	}

	/**
	 * Searches the database for an account with the given account number. If
	 * the account is found, it returns an array list storing the account data
	 * and the row number where the account was found. If the account is not
	 * found, an empty array list is returned.
	 * 
	 * @param searchedAccountNumber
	 *            the number of the searched account
	 * @return an array list storing the account data and the row number where
	 *         the account was found
	 */
	public ArrayList<String> searchAccount(String searchedAccountNumber) {
		ArrayList<String> accountData = new ArrayList<>();
		try	{	
			doConnect(); // Connect to the database				
			
			// Move to the last row and get the number of rows
			results.last();            
			int numberOfRows = results.getRow();		

			/* Search each row of records and find the account by checking the
			account numbers */
			for (int i = 1; i <= numberOfRows; i++) {
				results.absolute(i);
				
				if (results.getString("AccountNumber").equals(searchedAccountNumber)) {

					/* Return an array list storing all necessary data to be
					displayed in either the SearchUpdateAccount, Deposit, or
					Withdraw window. Not all data are displayed in each
					window. */

					/* This method could return an object, but that could not
					store the current row number. We need this number for
					calculations in the classes where this method is called,
					so we use an array list to return multiple items. */
					accountData.add(results.getString("FirstName"));
					accountData.add(results.getString("LastName"));
					accountData.add(results.getString("Gender"));
					accountData.add(results.getString("DateOfBirth"));
					accountData.add(results.getString("MaritalStatus"));
					accountData.add(results.getString("AddressLine1"));
					accountData.add(results.getString("AddressLine2"));
					accountData.add(results.getString("AddressLine3"));
					accountData.add(results.getString("PhoneNumber"));
					accountData.add(results.getString("AccountType"));
					accountData.add(results.getString("AccountBalance"));
					accountData.add(results.getString("AccountNumber"));
					accountData.add(String.valueOf(i)); 						

					return accountData;
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
		// If the account was not found, an empty array list is returned
		return accountData; 
	}

	/**
	 * Deposits a specific amount of money to a given account.
	 * 
	 * @param rowNumber
	 *            the row in the database table storing the account to which the
	 *            amount will be deposited
	 * @param currentBalance
	 *            the current balance of the account
	 * @param amount
	 *            the amount of money to be deposited
	 */
	public void deposit(int rowNumber, double currentBalance, double amount) {
		try	{
			doConnect(); // Connect to the database	

			// Move to the row where the account was found 
			results.absolute(rowNumber);			

			// Calculate the new balance and update the account data
			results.updateDouble("AccountBalance", currentBalance + amount);
			results.updateRow();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
	}

	/**
	 * Withdraws a specific amount of money from a given account.
	 * 
	 * @param rowNumber
	 *            the row in the database table storing the account from which
	 *            the amount will be withdrawn
	 * @param currentBalance
	 *            the current balance of the account
	 * @param amount
	 *            the amount of money to be withdrawn
	 */
	public void withdraw(int rowNumber, double currentBalance, double amount) {
		try	{
			doConnect(); // Connect to the database	

			// Move to the row where the account was found 
			results.absolute(rowNumber); 		

			// Calculate the new balance and update the account data
			results.updateDouble("AccountBalance", currentBalance - amount);
			results.updateRow();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
	}

	/**
	 * This method begins the money transfer between two accounts by debiting
	 * the transferred amount from the remitter's account.
	 * 
	 * @param amount
	 *            the amount to be debited from the remitter's account
	 * @param remitterAccountNumber
	 *            the remitter's account number
	 */
	public void debitTransferredAmount(double amount, String remitterAccountNumber) {
		try	{	
			doConnect(); // Connect to the database	

			// The charge of each transaction is £1
			int incurredCharge = 1;

			// Move to the last row and get the number of rows
			results.last();
			int numberOfRows = results.getInt("CustomerID");

			/* Find the remitter's account number in the database, and debit the
			amount as well as the incurred charge from the remitter's balance. */
			for (int i = 1; i <= numberOfRows; i++) {
				results.absolute(i); 
				
				if (results.getString("AccountNumber").equals(remitterAccountNumber)) {
					double remitterAccountBalance = results.getDouble("AccountBalance");
					remitterAccountBalance = remitterAccountBalance - amount - incurredCharge;

					// Update the remitter's account balance
					results.updateDouble("AccountBalance", remitterAccountBalance);
					results.updateRow();
					break;
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
	}
	
	/**
	 * This method finishes the money transfer between two accounts by crediting
	 * the transferred amount to the payee's account.
	 * 
	 * @param amount
	 *            the amount to be credited to the payee's account
	 * @param payeeAccountNumber
	 *            the payee's account number
	 */
	public void creditTransferredAmount(double amount, String payeeAccountNumber) {
		try	{		
			doConnect(); // Connect to the database	
			
			// Move to the last row and get the number of rows
			results.last();
			int numberOfRows = results.getInt("CustomerID");

			/* Find the payee's account number in the database, 
			and credit the transferred amount to the payee's balance. */
			for (int i = 1; i <= numberOfRows; i++) {
				results.absolute(i); 

				if (results.getString("AccountNumber").equals(payeeAccountNumber)) {
					double payeeAccountBalance = results.getDouble("AccountBalance");
					payeeAccountBalance = payeeAccountBalance + amount;

					// Update the payee's account balance
					results.updateDouble("AccountBalance", payeeAccountBalance);
					results.updateRow();
					break;
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Exception: ", e);
		} finally {
			if (myConnection != null) {
				try {
					myConnection.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
			if (myStatement != null) {
				try {
					myStatement.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Exception: ", e);
				}
			}
		}
	}
}
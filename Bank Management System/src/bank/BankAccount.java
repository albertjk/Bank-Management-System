package bank;

/**
 * Entity class which acts as a persistent object. This class is a POJO, used
 * for convenience as a reference entity object while interacting with the
 * controller bean. The constructor can be used to create a new BankAccount
 * object. Setters and getters can be used to access object attributes.
 * 
 * @author Albert Jozsa-Kiraly
 */
public class BankAccount {

	/* Bank Account attributes */
	private String firstName = null;
	private String lastName = null;
	private String gender = null;
	private String dateOfBirth = null;
	private String maritalStatus = null;
	private String addressLine1 = null;
	private String addressLine2 = null;
	private String addressLine3 = null;
	
	/* String so 0 can be displayed at the front if needed */
	private String phoneNumber = null;	
	
	private String accountType = null;
	private double balance = 0;
	
	/* String so 0 can be displayed at the front if needed */
	private String accountNumber = null; 
	
	/**
	 * Instantiates a new Bank Account.
	 * 
	 * @param firstName
	 *            the first name of the account owner
	 * @param lastName
	 *            the last name of the account owner
	 * @param gender
	 *            the gender of the account owner
	 * @param dateOfBirth
	 *            the date of birth of the account owner
	 * @param maritalStatus
	 *            the marital status of the account owner
	 * @param addressLine1
	 *            the first address line of the account owner
	 * @param addressLine2
	 *            the second address line of the account owner
	 * @param addressLine3
	 *            the third address line of the account owner
	 * @param phoneNumber
	 *            the phone number of the account owner
	 * @param accountType
	 *            the account type
	 * @param balance
	 *            the account balance
	 * @param accountNumber
	 *            the account number
	 */
	public BankAccount(String firstName, String lastName, String gender, String dateOfBirth, String maritalStatus,
			String addressLine1, String addressLine2, String addressLine3, String phoneNumber, String accountType,
			double balance, String accountNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.maritalStatus = maritalStatus;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.phoneNumber = phoneNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.accountNumber = accountNumber;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return this.gender;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine3() {
		return this.addressLine3;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public String toString() {
		return this.firstName + " " + this.lastName + " " + this.gender + " " + this.dateOfBirth + " "
				+ this.maritalStatus + " " + this.addressLine1 + " " + this.addressLine2 + " " + this.addressLine3 + " "
				+ this.phoneNumber + " " + this.accountType + " " + this.balance + " " + this.accountNumber;
	}
}
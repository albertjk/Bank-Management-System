/**
 * Author:  Albert Jozsa-Kiraly
 * Created: 25-July-2017
 */

CREATE TABLE BankAccount
(CustomerID INTEGER NOT NULL PRIMARY KEY,
FirstName VARCHAR(20) NOT NULL,
LastName VARCHAR(20) NOT NULL,
Gender VARCHAR(20) NOT NULL,
DateOfBirth VARCHAR(20) NOT NULL,
MaritalStatus VARCHAR(20) NOT NULL,
AddressLine1 VARCHAR(30) NOT NULL,
AddressLine2 VARCHAR(30) NOT NULL,
AddressLine3 VARCHAR(30) NOT NULL,
PhoneNumber VARCHAR(20) NOT NULL,
AccountType VARCHAR(20) NOT NULL,
AccountNumber VARCHAR(20) NOT NULL,
AccountBalance DOUBLE
);



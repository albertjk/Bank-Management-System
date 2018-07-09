# Bank Management System

## Project description
This is a personal project I made during the summer holidays of 2016 and 2017.

The project dealt with the creation of a Java application that resembles a real-world bank management system
used in bank branches where an employee can create an account out of 4 types, 
update or delete an account, view all customer accounts, 
make a cash deposit to an account, withdraw cash from an account, 
or transfer money between 2 accounts.

The application displays the GUI as a main interface with menus and menu items
corresponding to each of the above-mentioned bank services. On click to a menu item,
a separate window opens allowing the user to perform a certain bank service.
Bank accounts are stored in a local database in rows and columns.

The program uses JDBC to connect to the database, send SQL queries, and process the results received from the database.
To test the program, a localhost database needs to be running, and the IDE must have a JDBC Driver to connect to this database.
I used an Apache Derby database and a JDBC Driver connected it to Eclipse.

This was a project I was first working on from July to September 2016.
During this period, I created most of the program, including a user
interface for each bank service, the methods dealing with database operations,
and made it possible to do bank operations using the program.

Then, I worked on this project again between July and August 2017.
During this time, I modified the program by making it more object-oriented which has maintenance benefits.
Initially, the GUI and the methods of database operations were mixed in the classes. 
Therefore, I separated the parts of the code where the GUI is created from the parts
where the database operations are performed, and where the BankAccount entity is defined.
I created 2 more classes, namely, BankAccount and BankAccountBean.

The BankAccount class is a POJO used for convenience as an entity object while interacting with the bean class.
The BankAccountBean class is the controller bean class through which every communication with the database is done.
The other classes which I kept from 2016 are MainInterface, CreateAccount, SearchUpdateAccount,
DisplayTable, Deposit, Withdraw, and Transfer. In these classes I only kept the GUI creator
and action handler methods.
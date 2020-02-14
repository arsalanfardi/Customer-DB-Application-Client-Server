package ServerSide;

import com.mysql.jdbc.Driver;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import Model.Customer;

/**
 * Model class used for business logic and primarily to communicated with database.
 * 
 * @author Arsalan Fardi and Mihai Robu
 * @version 1
 * @since February 13, 2020
 */
public class CustomerManager implements JDBCCredentials {

    /**
     * CustomerID that is updated every time a Customer is added
     */
    private static int CustomerID = 1;
    /**
     * Connection to the database
     */
    private Connection jdbc_connection;
    /**
     * Used for SQL commands that don't require user input
     */
    private Statement statement;
    /**
     * Used for SQL commands that require user input
     */
    private PreparedStatement pStat;
    /**
     * Database name, Table name, and file name
     */
    private String databaseName ="customer_db", tableName ="customer_info_table", dataFile = "customers.txt";

    /**
     * Constructor for the CustomerManager which establishes a connection to the database
     */
    public CustomerManager () {
        try {
            Class.forName(JDBC_DRIVER);
            jdbc_connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected to: " + DB_URL + "\n");
            dbSetup();
        }
        catch (SQLException e) { e.printStackTrace(); }
        catch (Exception e) { e.printStackTrace(); }
    }
    
    /**
     * Method creates database and table.
     * Checks if database and table already exist
     */
    public void dbSetup () {
        try {
            ArrayList<String> list= new ArrayList<String>();

            DatabaseMetaData meta = jdbc_connection.getMetaData();
            ResultSet rs = meta.getCatalogs();
            while (rs.next()) {
                String listofDatabases=rs.getString("TABLE_CAT");
                list.add(listofDatabases);
            }
            if(list.contains(databaseName)) {
                System.out.println("Database already exists");
            } else {
                createDB();
            }
        } catch (SQLException e) { e.printStackTrace(); }

        try {
            ArrayList<String> list= new ArrayList<String>();

            DatabaseMetaData meta = jdbc_connection.getMetaData();
            ResultSet rs = meta.getTables(null,null,tableName,null);
            while (rs.next()) {
                String listOfTables=rs.getString("TABLE_NAME");
                list.add(listOfTables);
            }
            if(list.contains(tableName)) {
                System.out.println("Table already exists");
            } else {
                createTable();
                fillTable();
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Creates Customer database
     */
    private void createDB () {
        try {
            jdbc_connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = jdbc_connection.createStatement();
            statement.executeUpdate("CREATE DATABASE " + databaseName);
            System.out.println("Created Database " + databaseName);
        }
        catch (SQLException e) { e.printStackTrace(); }
        catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Creates Customer table containing Customer information (see Customer Class)
     */
    private void createTable () {
        String sql = "CREATE TABLE " + tableName + "("
                + "ID INT(4) NOT NULL, "
                + "FIRSTNAME VARCHAR(20) NOT NULL, "
                + "LASTNAME VARCHAR(20) NOT NULL, "
                + "ADDRESS VARCHAR(50) NOT NULL, "
                + "POSTALCODE CHAR(7) NOT NULL, "
                + "PHONENUMBER CHAR(12) NOT NULL, "
                + "CustomerTYPE CHAR(1) NOT NULL, "
                + "PRIMARY KEY (ID))";

        try {
            statement = jdbc_connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Created Table " + tableName);
        }
        catch (SQLException e) { e.printStackTrace(); }
        catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Removes Customer table
     */
    public void removeTable () {
        String sql = "DROP TABLE " + tableName;
        try{
            statement = jdbc_connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Removed Table " + tableName);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Fills table with information from data file
     */
    private void fillTable () {
        try {
            Scanner sc = new Scanner(new FileReader(dataFile));
            while (sc.hasNext()) {
                String[] CustomerInfo = sc.nextLine().split(";");
                int id = -1;
                String firstName = CustomerInfo[0];
                String lastName = CustomerInfo[1];
                String address = CustomerInfo[2];
                String postalCode = CustomerInfo[3];
                String phoneNumber = CustomerInfo[4];
                char CustomerType = CustomerInfo[5].charAt(0);
                addCustomer(new Customer(id, firstName, lastName, address, postalCode, phoneNumber, CustomerType));
            }
            sc.close();
        }
        catch (FileNotFoundException e) { System.out.println("File not found!"); }
        catch (Exception e ) { e.printStackTrace(); }
    }

    /**
     * Method for adding Customer to database - used by fillTable() method
     * @param Customer Customer object to be added to table
     */
    public void addCustomer (Customer Customer) {
        updateStaticID();
        String sql = "INSERT INTO " + tableName +
                " VALUES (?,?,?,?,?,?,?)";
        try{
            PreparedStatement pStat = jdbc_connection.prepareStatement(sql);
            pStat.setInt(1, getStaticCustomerID());
            pStat.setString(2, Customer.getFirstName());
            pStat.setString(3, Customer.getLastName());
            pStat.setString(4, Customer.getAddress());
            pStat.setString(5, Customer.getPostalCode());
            pStat.setString(6, Customer.getPhoneNumber());
            pStat.setString(7, Character.toString(Customer.getCustomerType()));
            pStat.executeUpdate();
            pStat.close();
            CustomerID++;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method for updating selected Customer in database
     * @param Customer Customer object currently selected
     */
    public void updateCustomer (Customer Customer) {
        int CustomerID = Customer.getId();

        String fName = Customer.getFirstName();
        String lName = Customer.getLastName();
        String address = Customer.getAddress();
        String pCode = Customer.getPostalCode();
        String phone = Customer.getPhoneNumber();
        String cType = String.valueOf(Customer.getCustomerType());

        String sql = "UPDATE " + tableName + " SET FIRSTNAME=?, LASTNAME=?, ADDRESS=?, " +
                "POSTALCODE=?, PHONENUMBER=?, CustomerTYPE=? WHERE ID=?";
        try {
            pStat = jdbc_connection.prepareStatement(sql);
            pStat.setString(1, fName);
            pStat.setString(2, lName);
            pStat.setString(3, address);
            pStat.setString(4, pCode);
            pStat.setString(5, phone);
            pStat.setString(6, cType);
            pStat.setInt(7, CustomerID);

            pStat.executeUpdate();
            pStat.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Method for removing Customer from database
     * @param CustomerID id of Customer object to be removed
     */
    public void removeCustomer (int CustomerID) {
        String sql = "DELETE FROM " + tableName + " WHERE ID=?";
        try {
            pStat =jdbc_connection.prepareStatement(sql);
            pStat.setInt(1, CustomerID);
            pStat.executeUpdate();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     * Accesses database and returns all Customers.
     * @return all Customers in database as array of Customer Objects
     */
    public ArrayList<Customer>searchCustomer () {
        ArrayList<Customer> searchedCustomers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + tableName;
            statement = jdbc_connection.createStatement();
            ResultSet Customer = statement.executeQuery(sql);
            while (Customer.next()) {
                int CustomerID = Customer.getInt("ID");
                String fname = Customer.getString("FIRSTNAME");
                String lname = Customer.getString("LASTNAME");
                String address = Customer.getString("ADDRESS");
                String postal = Customer.getString("POSTALCODE");
                String phone = Customer.getString("PHONENUMBER");
                char ctype = Customer.getString("CustomerTYPE").charAt(0);
                Customer tempCustomer = new Customer(CustomerID, fname, lname, address, postal, phone, ctype);
                searchedCustomers.add(tempCustomer);
            }
            Customer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return searchedCustomers.toArray(new Customer[searchedCustomers.size()]);
        return searchedCustomers;
    }

    /**
     * Accesses database and returns all Customers with given ID
     * @param id Customer ID
     * @return array of Customer objects matching search criteria
     */
    public ArrayList<Customer> searchCustomer (int id) {
        ArrayList<Customer> searchedCustomers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + tableName + " WHERE ID=?";
            pStat = jdbc_connection.prepareStatement(sql);
            pStat.setInt(1, id);
            ResultSet Customer = pStat.executeQuery();
            while (Customer.next()) {
                int CustomerID = Customer.getInt("ID");
                String fname = Customer.getString("FIRSTNAME");
                String lname = Customer.getString("LASTNAME");
                String address = Customer.getString("ADDRESS");
                String postal = Customer.getString("POSTALCODE");
                String phone = Customer.getString("PHONENUMBER");
                char ctype = Customer.getString("CustomerTYPE").charAt(0);
                Customer tempCustomer = new Customer(CustomerID, fname, lname, address, postal, phone, ctype);
                searchedCustomers.add(tempCustomer);
            }
            Customer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return searchedCustomers.toArray(new Customer[searchedCustomers.size()]);
        return searchedCustomers;
    }

    /**
     * Accesses database and returns all Customers with given last name
     * @param lastName Customer's last name
     * @return array of Customer objects matching search criteria
     */
    public ArrayList<Customer> searchCustomer (String lastName) {
        ArrayList<Customer> searchedCustomers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + tableName + " WHERE LASTNAME=?";
            pStat = jdbc_connection.prepareStatement(sql);
            pStat.setString(1, lastName);
            ResultSet Customer = pStat.executeQuery();
            while (Customer.next()) {
                int CustomerID = Customer.getInt("ID");
                String fname = Customer.getString("FIRSTNAME");
                String lname = Customer.getString("LASTNAME");
                String address = Customer.getString("ADDRESS");
                String postal = Customer.getString("POSTALCODE");
                String phone = Customer.getString("PHONENUMBER");
                char ctype = Customer.getString("CustomerTYPE").charAt(0);
                Customer tempCustomer = new Customer(CustomerID, fname, lname, address, postal, phone, ctype);
                searchedCustomers.add(tempCustomer);
            }
            Customer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return searchedCustomers.toArray(new Customer[searchedCustomers.size()]);
        return searchedCustomers;
    }

    /**
     * Accesses database and returns all Customers with given Customer type
     * @param CustomerType Customer's type (residential or commercial)
     * @return array of Customer objects matching search criteria
     */
    public ArrayList<Customer> searchCustomer (char CustomerType) {
        ArrayList<Customer> searchedCustomers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + tableName + " WHERE CustomerTYPE=?";
            pStat = jdbc_connection.prepareStatement(sql);
            pStat.setString(1, String.valueOf(CustomerType));
            ResultSet Customer = pStat.executeQuery();
            while (Customer.next()) {
                int CustomerID = Customer.getInt("ID");
                String fname = Customer.getString("FIRSTNAME");
                String lname = Customer.getString("LASTNAME");
                String address = Customer.getString("ADDRESS");
                String postal = Customer.getString("POSTALCODE");
                String phone = Customer.getString("PHONENUMBER");
                char ctype = Customer.getString("CustomerTYPE").charAt(0);
                Customer tempCustomer = new Customer(CustomerID, fname, lname, address, postal, phone, ctype);
                searchedCustomers.add(tempCustomer);
            }
            Customer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return searchedCustomers.toArray(new Customer[searchedCustomers.size()]);
        return searchedCustomers;
    }

    /**
     * Gets the CustomerID static variable
     * @return integer representing the counter of customer ID's
     */
    public int getStaticCustomerID () {
        return this.CustomerID;
    }

    /**
     * Updates the counter to reflect the number of customers in the database + 1
     */
    public void updateStaticID () {
        ArrayList<Customer> searchedCustomers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + tableName;
            statement = jdbc_connection.createStatement();
            ResultSet Customer = statement.executeQuery(sql);
            while (Customer.next()) {
                int CustomerID = Customer.getInt("ID");
                String fname = Customer.getString("FIRSTNAME");
                String lname = Customer.getString("LASTNAME");
                String address = Customer.getString("ADDRESS");
                String postal = Customer.getString("POSTALCODE");
                String phone = Customer.getString("PHONENUMBER");
                char ctype = Customer.getString("CustomerTYPE").charAt(0);
                Customer tempCustomer = new Customer(CustomerID, fname, lname, address, postal, phone, ctype);
                searchedCustomers.add(tempCustomer);
            }
            Customer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return searchedCustomers.toArray(new Customer[searchedCustomers.size()]);
        CustomerID = searchedCustomers.size() + 1;
    }
}

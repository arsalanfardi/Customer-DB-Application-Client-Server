package Model;

import java.io.Serializable;

/**
 * Represents a Customer with personal and contact information
 * 
 * Implements Serializable to allow for sending between Client/Server.
 * 
 * @author Arsalan Fardi and Mihai Robu
 * @version 1
 * @since February 13, 2020
 */
public class Customer implements Serializable {
    /** serialVersion ID to ensure proper serialization */
    private static final long serialVersionUID = 1L;
    /**
     * Customer ID used as primary key [int]
     */
    private int id;
    /**
     * Customer's first name [String]
     */
    private String firstName;
    /**
     * Customer's last name [String]
     */
    private String lastName;
    /**
     * Customer's address [String]
     */
    private String address;
    /**
     * Customer's postal code [String]
     */
    private String postalCode;
    /**
     * Customer's phone number [String]
     */
    private String phoneNumber;
    /**
     * Customer type (Residential "R" or Commercial "C") [char]
     */
    private char clientType;

    /**
     * Constructor for Customer
     * @param id Customer's unique id [int]
     * @param firstName Customer's first name [String]
     * @param lastName Customer's last name [String]
     * @param address Customer's address [String]
     * @param postalCode Customer's postal code [String]
     * @param phoneNumber Customer's phone number [String]
     * @param clientType Customer type [char]
     */
    public Customer (int id, String firstName, String lastName,
                     String address, String postalCode,
                     String phoneNumber, char clientType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getPostalCode() { return postalCode; }
    public String getPhoneNumber() { return phoneNumber; }
    public char getCustomerType() { return clientType; }

    /**
     * Customer's toString method
     * @return String with client information formatted for humans
     */
    @Override
    public String toString() {
        return this.id + ": " + this.firstName + " " + this.lastName + " | " + this.clientType + "\n";
//                + this.address + " " + this.postalCode +"\n"
//                + this.phoneNumber;
    }
}

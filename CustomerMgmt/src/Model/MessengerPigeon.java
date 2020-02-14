package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Serializable class containing a customers list and action instruction which is sent between Client and Server.
 * 
 * @author Arsalan Fardi and Mihai Robu
 * @version 1
 * @since February 13, 2020
 */
public class MessengerPigeon implements Serializable {
    /** serialVersion ID to ensure proper serialization */
    private static final long serialVersionUID = 1L;
    /** List of customers which is populated as necessary */
    ArrayList<Customer> customers;
    /** Instruction corresponding the switch board in the Server Controller */
    String instruction = null;
    /** Search parameter for search instructions */
    String searchParameter = null;
    /** Search type for search instructions */
    String searchType = null;

    /**
     * The constructor to instantiate MessengerPigeon with a list of customers and an instruction as a minimum 
     * requirement.
     * 
     * @param customers list of applicable customers
     * @param instruction the instruction as specified by the Client
     */
    public MessengerPigeon(ArrayList<Customer> customers, String instruction){
        this.customers = customers;
        this.instruction = instruction;
    }

    
    /** 
     * Returns the search parameter
     * 
     * @return String representing the search parameter
     */
    public String getSearchParameter(){
        return searchParameter;
    }

    
    /** 
     * Sets the search fields.
     * 
     * Used on Client side before sending a search instruction.
     * @param searchType
     * @param searchParameter
     */
    public void setSearchFields(String searchType, String searchParameter){
        this.searchType = searchType;
        this.searchParameter = searchParameter;
    }

    
    /** 
     * Returns the instruction as selected by the Client.
     * 
     * Used on the Server Controller.
     * @return instruction
     */
    public String getInstruction(){
        return instruction;
    }

	
    /** 
     * Returns the search type.
     * 
     * Used on the Server Controller.
     * @return String
     */
    public String getSearchType() {
		return searchType;
	}

	
    /** 
     * Sets the list of customers
     * @param customers
     */
    public void setCustomer(ArrayList<Customer> customers) {
        this.customers = customers;
	}

	
    /** 
     * Returns the list of customers
     * @return ArrayList<Customer>
     */
    public ArrayList<Customer> getCustomer() {
		return customers;
	}

}
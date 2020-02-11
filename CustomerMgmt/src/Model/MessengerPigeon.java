package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class MessengerPigeon implements Serializable {
    private static final long serialVersionUID = 1L;
    ArrayList<Customer> customers;
    String instruction = null;
    String searchParameter = null;
    String searchType = null;

    public MessengerPigeon(ArrayList<Customer> customers, String instruction){
        this.customers = customers;
        this.instruction = instruction;
    }

    public String getSearchParameter(){
        return searchParameter;
    }

    public void setSearchFields(String searchType, String searchParameter){
        this.searchType = searchType;
        this.searchParameter = searchParameter;
    }

    public String getInstruction(){
        return instruction;
    }

	public String getSearchType() {
		return searchType;
	}

	public void setCustomer(ArrayList<Customer> customers) {
        this.customers = customers;
	}

	public ArrayList<Customer> getCustomer() {
		return customers;
	}

}
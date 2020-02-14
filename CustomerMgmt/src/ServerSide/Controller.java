package ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import Model.MessengerPigeon;
import Model.Customer;

/**
 * Controller class used to link to the Database and communicate to Client
 * @author Arsalan Fardi and Mihai Robu
 * @version 1
 * @since February 13, 2020
 */
public class Controller implements Runnable {

    /** Input stream from the instance of Client */
    private ObjectInputStream objectIn;
    /** Output stream for the instance of Client */
    private ObjectOutputStream objectOut;
    /** CustomerManager which connects to database */
    private CustomerManager cManager;
    /** Boolean condition the while loop in the run method */
    private boolean live = true;

    public Controller(ObjectInputStream objectIn, ObjectOutputStream objectOut){
        this.objectOut = objectOut;
        this.objectIn = objectIn;
        this.cManager = new CustomerManager();
    }

    /**
     * Method to constantly listen for a response from the Client.
     */
    @Override
    public void run() {
        MessengerPigeon clientResponse = null;
        while (live) {
            System.out.println("Server Controller listening...");
            try {
                clientResponse = (MessengerPigeon) objectIn.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            switchBoard(clientResponse);
        }
        try {
            objectIn.close();
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch statement for listening to instruction from the client response.
     * 
     * Redirects to appropriate method.
     * 
     * @param clientResponse the MessengerPigeon from the client
     */
    private void switchBoard(MessengerPigeon clientResponse) {
        MessengerPigeon pidgey = new MessengerPigeon(null, null);
        try {
            switch(clientResponse.getInstruction()){
                case "1":
                    pidgey.setCustomer(callSearch(clientResponse));
                    objectOut.writeObject(pidgey);
                    break;
                case "2":
                    callSave(clientResponse);
                    break;
                case "3":
                    callAdd(clientResponse);
                    break;
                case "4":
                    callDelete(clientResponse);
                    break;
                case "5":
                    live = false;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding a new customer to the database.
     * 
     * @param clientResponse
     */
    private void callAdd(MessengerPigeon clientResponse) {
        cManager.addCustomer(clientResponse.getCustomer().get(0));
    }

    /**
     * Updating a customer in the database.
     * 
     * @param clientResponse
     */
    private void callSave(MessengerPigeon clientResponse) {
        cManager.updateCustomer(clientResponse.getCustomer().get(0));
    }

    /**
     * Searching the database with type and parameter as specified by the Client.
     * 
     * @param clientResponse
     * @return an ArrayList with search results
     */
    private ArrayList<Customer> callSearch(MessengerPigeon clientResponse) {
        switch(clientResponse.getSearchType()){
            case "ID":
                return cManager.searchCustomer(Integer.valueOf(clientResponse.getSearchParameter()));
            case "lName":
                return cManager.searchCustomer(clientResponse.getSearchParameter());
            case "cType":
                return cManager.searchCustomer(clientResponse.getSearchParameter().charAt(0));
            case "all":
                return cManager.searchCustomer();
            default:
                return null;
        }
    }

    /**
     * Deleting a customer from the database.
     * @param clientResponse
     */
    private void callDelete (MessengerPigeon clientResponse) {
        cManager.removeCustomer(clientResponse.getCustomer().get(0).getId());
    }

    
}

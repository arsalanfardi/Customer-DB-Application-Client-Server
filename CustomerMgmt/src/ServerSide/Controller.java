package ServerSide;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Model.MessengerPigeon;
import Model.Customer;

/**
 * Controller class used to link the View (GUI class) to the Model
 * (CustomerManager Class).
 */
public class Controller implements Runnable {

    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private CustomerManager cManager;
    private boolean live = true;

    public Controller(BufferedReader socketIn, PrintWriter socketOut, 
    ObjectInputStream objectIn, ObjectOutputStream objectOut){
        this.objectOut = objectOut;
        this.objectIn = objectIn;
        this.socketIn = socketIn;
        this.socketOut = socketOut;
        this.cManager = new CustomerManager();
    }

    @Override
    public void run() {
        // cManager.dbSetup();
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
    }

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

    private void callAdd(MessengerPigeon clientResponse) {
        cManager.addCustomer(clientResponse.getCustomer().get(0));
    }

    private void callSave(MessengerPigeon clientResponse) {
        cManager.updateCustomer(clientResponse.getCustomer().get(0));
    }

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

    private void callDelete (MessengerPigeon clientResponse) {
        cManager.removeCustomer(clientResponse.getCustomer().get(0).getId());
    }

    
}

package ServerSide;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class used to link the View (GUI class) to the Model
 * (CustomerManager Class).
 */
public class Controller implements Runnable {

    BufferedReader socketIn;
    PrintWriter socketOut;
    CustomerManager cManager;

    public Controller(BufferedReader socketIn, PrintWriter socketOut) {
        this.socketIn = socketIn;
        this.socketOut = socketOut;
        this.cManager = new CustomerManager();
        this.run();
    }

    @Override
    public void run() {
        // cManager.dbSetup();
        String clientResponse = "";
        while (true) {
            System.out.println("Server Controller listening...");
            try {
                clientResponse = socketIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socketOut.println(clientResponse);
        }
    }

    
}

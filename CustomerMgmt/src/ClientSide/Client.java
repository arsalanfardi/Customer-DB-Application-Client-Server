package ClientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import Model.MessengerPigeon;

/**
 * Client class for connecting and communicating with Server.
 * 
 * @author Arsalan Fardi and Mihai Robu
 * @version 1
 * @since February 13, 2020
 * 
 */
public class Client {
    /** Socket for connecting to Server */
    private Socket aSocket;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    /**
     * Constructor for Client that connects using server name and port number
     * @param serverName Name of the server to connect to
     * @param portNumber Port number to connect to
     */
    public Client(String serverName, int portNumber) {
        try {
            aSocket = new Socket(serverName, portNumber);
            objectOut = new ObjectOutputStream(aSocket.getOutputStream());
            objectIn = new ObjectInputStream(aSocket.getInputStream());
            System.out.println("Client connected");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to send MessengerPigeon object to server
     * @param pidgey MessengerPigeon object
     */
    public void objectOut(MessengerPigeon pidgey) {
        try {
            objectOut.writeObject(pidgey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to read MessengerPigeon object from server
     * @return MessengerPigeon object
     */
    public MessengerPigeon objectIn() {
        MessengerPigeon pidgey = null;
        try {
            pidgey = (MessengerPigeon) objectIn.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pidgey;
    }
}

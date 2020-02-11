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

public class Client {
    private Socket aSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    public Client(String serverName, int portNumber) {
        try {
            aSocket = new Socket(serverName, portNumber);
            objectOut = new ObjectOutputStream(aSocket.getOutputStream());
            objectIn = new ObjectInputStream(aSocket.getInputStream());
            socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOut = new PrintWriter(aSocket.getOutputStream(), true);
            System.out.println("Client connected");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void objectOut(MessengerPigeon pidgey) {
        try {
            objectOut.writeObject(pidgey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    public void out(String s){
        socketOut.println(s);
    }

    public void in() {
        try {
            System.out.println(socketIn.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

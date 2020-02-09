package ClientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket aSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public Client (String serverName, int portNumber) {
        try {
            aSocket = new Socket(serverName, portNumber);
            socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOut = new PrintWriter(aSocket.getOutputStream(), true);
            System.out.println("Client connected");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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

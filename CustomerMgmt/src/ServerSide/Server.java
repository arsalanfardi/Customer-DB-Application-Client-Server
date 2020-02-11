package ServerSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private Socket socket;
    private ServerSocket serverSocket;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private ExecutorService pool;

    public Server() {
        try {
            serverSocket = new ServerSocket(9898);
            pool = Executors.newCachedThreadPool();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runServer(){
        try {
            while(true){
                //Getting the first player's connection
                socket = serverSocket.accept();
                System.out.println("Client connected");
                socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                socketOut = new PrintWriter(socket.getOutputStream(), true);
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());
                //What to execute?
                pool.execute(new Controller(socketIn, socketOut, objectIn, objectOut));
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Server shutting down");
            socketIn.close();
            socketOut.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }
}
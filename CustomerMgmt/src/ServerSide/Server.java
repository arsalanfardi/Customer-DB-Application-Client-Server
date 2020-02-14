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

/**
 * Controller class that funcitons as server and creates connection between a Client and new CustomerManager object.
 * 
 * @author Arsalan Fardi and Mihai Robu
 * @version 1
 * @since February 13, 2020
 */
public class Server {
    private Socket socket;
    private ServerSocket serverSocket;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private ExecutorService pool;

    /**
     * Constructor for server creates new ServerSocket and new ThreadPool
     */
    public Server() {
        try {
            serverSocket = new ServerSocket(9898);
            pool = Executors.newCachedThreadPool();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that begins running the server so that it can accept new Client Connections.
     * Executes a new Controller (implements Runnable) and passes in a new ObjectOutputStream and ObjectInputStream 
     */
    public void runServer(){
        try {
            while(true){
                //Getting the first player's connection
                socket = serverSocket.accept();
                System.out.println("Client connected");
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());
                //What to execute?
                pool.execute(new Controller(objectIn, objectOut));
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
            objectIn.close();
            objectOut.close();
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ThreadedServer {

    public static void main(String[] args) {

        final int serverPort = Integer.parseInt(args[0]);

        ConnectionManager mgr = new ConnectionManager();

        try (
                ServerSocket serverSocket = new ServerSocket(serverPort);
        ) {
            while(true) {
                ClientConnection newConnection = new ClientConnection(serverSocket.accept(), mgr);
                newConnection.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

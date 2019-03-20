import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ClientConnection implements AutoCloseable {

    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;

    public ClientConnection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream());
        in = new Scanner(clientSocket.getInputStream());
    }


    @Override
    public void close() throws Exception {
        out.close();
        in.close();
        clientSocket.close();
    }

    public void run() throws Exception {
        System.out.println("Starting client connection");
        while (in.hasNextLine()) {
            String input = in.nextLine();
            System.out.println("Got input from client:" + input);
            try {
                Task t = new Task(input);
                out.println(t.getId() + " " + t.getResult());

                System.out.println("Sent answer to client: " + t.getResult());

                out.flush();
            } catch (Exception e) {
                // survive invalid input
                e.printStackTrace();
            }
        }
        System.out.println("Client disconnected");
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

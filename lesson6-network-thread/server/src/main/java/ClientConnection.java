import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientConnection implements AutoCloseable {

    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private Thread incomingThread;
    private Thread outgoingThread;
    private ConnectionManager mgr;
    private final LinkedList<String> outgoingMessages = new LinkedList<>();
    boolean active = true;


    public ClientConnection(Socket clientSocket, ConnectionManager mgr) throws IOException {
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream());
        in = new Scanner(clientSocket.getInputStream());
        this.mgr = mgr;
    }

    void addMessage(String msg) {

    }

    @Override
    public void close() throws Exception {
        out.close();
        in.close();
        clientSocket.close();
    }

    public void run() {

        incomingThread = new Thread(() -> {
            System.out.println("Starting client connection");
            mgr.add(this);
            while (in.hasNextLine()) {
                String input = in.nextLine();
                System.out.println("Got input from client:" + input);
                try {
                    Task t = new Task(input);

                    mgr.forEachConnection((ClientConnection c) -> {
                        synchronized (c.outgoingMessages) {
                            c.outgoingMessages.add(t.getId() + " " + t.getResult());
                            c.outgoingMessages.notifyAll();
                        }
                    });

                    System.out.println("Queued answer to all clients: " + t.getResult());

                    out.flush();
                } catch (Exception e) {
                    // survive invalid input
                    e.printStackTrace();
                }
            }
            System.out.println("Client disconnected");

            // Make sure that the outgoing thread exits nicely

            synchronized (outgoingMessages) {
                active = false;
                outgoingMessages.clear();
                outgoingMessages.notifyAll();
            }

            try {
                outgoingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                mgr.remove(this);
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Client threads done.");

        });
        incomingThread.start();

        outgoingThread = new Thread(() -> {
            while(active) {
                synchronized (outgoingMessages) {
                    try {
                        outgoingMessages.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        while (!outgoingMessages.isEmpty() && clientSocket.isConnected()) {
                            out.println(outgoingMessages.getFirst());
                            out.flush();
                            System.out.println("Sent answer to client: " + clientSocket + " - " + outgoingMessages.getFirst());
                            outgoingMessages.removeFirst();
                        }
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        outgoingThread.start();
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

class TorpedoClient {

    private String hostname;
    private int serverPort;

    public TorpedoClient(String hostname, int serverPort) {
        this.serverPort = serverPort;
        this.hostname = hostname;
    }

    void run() {
        try (
                Socket echoSocket = new Socket(hostname, serverPort);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            boolean running = true;
            while (running) {
                String cmd = stdIn.readLine();
                if (cmd.equals("exit")) {
                    running = false;
                }
                out.println(cmd);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: <hostname> <port>");
            return;
        }
        new TorpedoClient(args[0], Integer.parseInt(args[1])).run();
    }

}
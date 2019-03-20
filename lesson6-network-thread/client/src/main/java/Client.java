import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    static int id = 1;

    public static void main(String[] args) {
        String hostname =args[0];
        int serverPort = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(hostname, serverPort);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream());
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {

            while(true) {
                String msg = id + " " + stdIn.readLine();
                out.println(msg);
                out.flush();
                System.out.println("Message sent: " + msg);
                System.out.println("Message received; " + in.readLine());
                id++;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

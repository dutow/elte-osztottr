import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static int id = 1;

    private static boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        String hostname =args[0];
        int serverPort = Integer.parseInt(args[1]);

        try (
                Socket socket = new Socket(hostname, serverPort);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream());
                Scanner in =
                        new Scanner(
                                new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            Thread tToServer = new Thread(() -> {
                try {
                    while (running) {
                        String read =stdIn.readLine();
                        if(read.equals("exit")) {
                            running = false;
                            break;
                        }
                        String msg = id + " " + read;
                        out.println(msg);
                        out.flush();
                        System.out.println("Message sent: " + msg);
                        id++;
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            });

            Thread tFromServer = new Thread(() -> {
                while (running && in.hasNextLine()) {
                    System.out.println("Message received; " + in.nextLine());                }
            });

            tFromServer.start();
            tToServer.start();

            tFromServer.join();
            tToServer.join();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

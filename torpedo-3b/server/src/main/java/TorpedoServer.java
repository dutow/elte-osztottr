import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

class TorpedoServer {

    public static void main(String[] args) {
        final int serverPort = 1337;

        final Random rand = new Random(System.currentTimeMillis());
        final boolean player1Starts = rand.nextBoolean();

        try (ServerSocket serverSocket = new ServerSocket(serverPort);
             Game game = new Game(1, serverSocket.accept(), serverSocket.accept(), player1Starts)
        ) {
            while(game.isRunning()) {
                game.readHit();
                game.nextPlayer();
            }
            game.sendResult();
            game.sendExitAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
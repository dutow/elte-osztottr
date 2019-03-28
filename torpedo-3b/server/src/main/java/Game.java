import java.io.IOException;
import java.net.Socket;

public class Game implements AutoCloseable {

    int id;
    Player[] players;
    int currentPlayer;

    public Game(int id, Socket player1, Socket player2, boolean player1Starts) throws IOException {
        this.id = id;

        log("Starting");

        players = new Player[]{new Player(player1), new Player(player2)};
        currentPlayer = player1Starts ? 0 : 1;

        log("Ships received");
        log("Player 1 is " + players[0].getName());
        log("Player 2 is " + players[1].getName());

        sendPlay(currentPlayerIdx());
        sendWait(otherPlayerIdx());

        log("Hello sent");
    }

    private void log(String msg) {
        System.out.println("Game #" + id + ": " + msg);
    }

    private void sendToPlayer(int id, String message) {
        log(" sending to player " + id + " >> " + message);
        players[id].println(message);
    }

    private void sendWait(int player) {
        sendToPlayer(player, "wait " + id + " " + remainingShips());
    }

    private void sendPlay(int player) {
        sendToPlayer(player, "play " + id + " " + remainingShips());
    }

    private String remainingShips() {
        return players[0].getBoard().remaining() + " " + players[1].getBoard().remaining();
    }

    int currentPlayerIdx() {
        return currentPlayer;
    }

    int otherPlayerIdx() {
        return 1 - currentPlayerIdx();
    }

    @Override
    public void close() throws IOException {
        log("Closing game");
        for (Player p : players) {
            p.close();
        }
    }


    public boolean isRunning() {
        return players[0].getBoard().remaining() > 0 &&
                players[1].getBoard().remaining() > 0;
    }

    private String readFromPlayer(int idx) {
        log("Waiting for message from player " + idx);
        String line = players[idx].readLineIfAny();
        log(" received from player " + idx + " << " + line);

        if(line == null) {
            throw new RuntimeException("Player " + idx + " disconnected!");
        }


        return line;
    }

    public boolean readHit() {
        String line = readFromPlayer(currentPlayerIdx());
        String[] cmd = line.split(" ");
        int x = -1;
        int y = -1;
        if (cmd[0].equals("shoot")) {
            x = Integer.parseInt(cmd[1]);
            y = Integer.parseInt(cmd[2]);
            players[otherPlayerIdx()].getBoard().hit(x, y);
        }
        sendHit(currentPlayerIdx(), otherPlayerIdx(), x, y);
        sendHit(otherPlayerIdx(), otherPlayerIdx(), x, y);
        return true;
    }

    private void sendHit(int player, int hitPlayer, int x, int y) {
        String hitMessage = "hit " + id +
                " " + hitPlayer +
                " " + x +
                " " + y +
                " " + remainingShips();
        sendToPlayer(player, hitMessage);
    }

    public void nextPlayer() {
        currentPlayer = 1 - currentPlayer;
    }

    public void sendResult() {
        sendToAll(getResultMessage());
    }

    public String getResultMessage() {
        int winner = players[0].getBoard().remaining() == 0 ? 0 : 1;
        int loser = 1 - winner;
        return "result " + id +
                " winner \"" + players[winner].getName() +
                "\" loser \"" + players[loser].getName() + '"';
    }

    public void sendExitAll() {
        sendToAll( "exit");
    }

    private void sendToAll(String msg) {
        for(int i =0;i<players.length;++i) {
            sendToPlayer(i, msg);
        }
    }
}
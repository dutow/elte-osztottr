import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player implements AutoCloseable {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private String name;
    private Board board;

    public Player(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new Scanner(socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());
        this.name = in.nextLine();
        this.board = new Board(in);
    }

    @Override
    public void close() throws IOException {
        socket.close();
        in.close();
        out.close();
    }

    public void println(String msg) {
        out.println(msg);
        out.flush();
    }

    public String readLineIfAny() {
        if(!in.hasNextLine()) {
            return null;
        }
        return in.nextLine();
    }

    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }
}

import java.util.LinkedList;

public class ConnectionManager {

    interface ClientFunctor {
        void apply(ClientConnection c);
    }

    private LinkedList<ClientConnection> connections = new LinkedList<>();

    public synchronized void add(ClientConnection conn) {
        connections.add(conn);
    }

    public synchronized void remove(ClientConnection conn) {
       connections.remove(conn);
    }

    public synchronized void forEachConnection(ClientFunctor f) {
        for(ClientConnection c: connections) {
            f.apply(c);
        }
    }

}

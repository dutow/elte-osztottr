import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class TorpedoClient {

    private static final int MAP_SIZE = 10;

    private final String shipsFile;
    private final String hostname;
    private final String name;
    private static final int serverPort = 1337;

    private char[][] ourShips;
    private char[][] player1;
    private char[][] player2;

    private boolean currentPlayerActive;
    private int remainingPlayer1 ;
    private int remainingPlayer2;

    public TorpedoClient(String hostname, String name, String shipsFile) {
        this.hostname = hostname;
        this.name = name;
        this.shipsFile = shipsFile;
    }

    void run() {
        try (
                Socket clientSocket = new Socket(hostname, serverPort);
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream());
                Scanner in =
                        new Scanner(
                                new InputStreamReader(clientSocket.getInputStream()));
                Scanner stdIn =
                        new Scanner(
                                new InputStreamReader(System.in))
        ) {
            // Hello: nev + hajok
            out.println(name);
            initShips(out);
            out.flush();

            boolean running = true;
            while (running && in.hasNextLine()) {
                String fromServer =in.nextLine();
                System.out.println(" >> " + fromServer);
                String[] cmd = fromServer.split(" ");

                switch(cmd[0]) {
                    case "play": {
                        // Uj jatek, mi vagyunk az elso jatekos
                        resetGame(true);
                        remainingPlayer1 = Integer.parseInt(cmd[2]);
                        remainingPlayer2 = Integer.parseInt(cmd[3]);
                        displayMaps();
                        out.println(shootSomething(stdIn));
                        out.flush();
                        break;
                    }
                    case "wait": {
                        // Uj jatek, mi vagyunk a masodik jatekos
                        resetGame(false);
                        remainingPlayer1 = Integer.parseInt(cmd[2]);
                        remainingPlayer2 = Integer.parseInt(cmd[3]);
                        displayMaps();
                        break;
                    }
                    case "results": {
                        // Egy jatek vege
                        // Megjelintettuk, mas dolgunk nincs
                        break;
                    }
                    case "hit": {
                        // Valaki lott egyet.
                        // hit <ID> <0|1> <X> <Y> <N> <M>
                        updateGame(Integer.parseInt(cmd[2]),
                                Integer.parseInt(cmd[3]),
                                Integer.parseInt(cmd[4]),
                                Integer.parseInt(cmd[5]),
                                Integer.parseInt(cmd[6]));
                        displayMaps();
                        if (ourTurn()) {
                            out.println(shootSomething(stdIn));
                            out.flush();
                        }
                        break;
                    }
                    case "exit": {
                        // A szerver azt mondja, lepjunk ki
                        running = false;
                        break;
                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateGame(int playerId, int x, int y, int n, int m) {
        if(playerId == 0) {
            updateMapWithHit(player1, x, y);
        }
        if (playerId == 1) {
            updateMapWithHit(player2, x, y);
        }
        remainingPlayer1 = n;
        remainingPlayer2 = m;
        currentPlayerActive = !currentPlayerActive;
    }

    private void updateMapWithHit(char[][] map, int x, int y) {
        if(x < 0 || y < 0 || x >= MAP_SIZE || y >= MAP_SIZE) {
            return;
        }
        if(map[y][x]=='.' || map[y][x] == '*') {
            map[y][x] = '*';
        } else {
            map[y][x] = '!';
        }
    }


    private boolean ourTurn() {
        return currentPlayerActive;
    }

    private String shootSomething(Scanner in) {
        return "shoot " + in.nextLine();
    }

    private void initShips(PrintWriter out) {
        ourShips = createEmptyMap();
        try(Scanner sc = new Scanner(new File(shipsFile))) {
            while(sc.hasNext()) {
                String type = sc.next();
                int x = sc.nextInt();
                int y = sc.nextInt();

                switch(type) {
                    case "x": {
                        addShip(ourShips, x, y);
                        break;
                    }
                    case "-": {
                        addShip(ourShips, x, y);
                        addShip(ourShips, x-1, y);
                        addShip(ourShips, x+1, y);
                        break;
                    }
                    case "I": {
                        addShip(ourShips, x, y);
                        addShip(ourShips, x, y-1);
                        addShip(ourShips, x, y+1);
                        break;
                    }
                }

                out.println(type + " " + x + " " + y);
            }

            out.println("---");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addShip(char[][] ourShips, int x, int y) {
        if(x >= 0 && y >= 0 && x < MAP_SIZE && y < MAP_SIZE) {
            ourShips[y][x] = 'x';
        }
    }

    private void resetGame(boolean weAreFirst) {
        if(weAreFirst) {
            player1 = ourShips.clone();
            player2 = createEmptyMap();
            currentPlayerActive = true;
        } else {
            player2 = ourShips.clone();
            player1 = createEmptyMap();
            currentPlayerActive = false;
        }
    }

    private char[][] createEmptyMap() {
        char[][] map = new char[MAP_SIZE][MAP_SIZE];
        for(int iy =0;iy<MAP_SIZE;iy++) {
            for(int ix =0;ix<MAP_SIZE;ix++) {
                map[iy][ix] = '.';
            }
        }
        return map;
    }

    private void displayMaps() {
        System.out.println("----------------------------");
        for(int iy =0;iy < MAP_SIZE; ++iy) {
            String line = "|" + String.valueOf(player1[iy]) + "|    |" + String.valueOf(player2[iy]) + "|";
            System.out.println(line);
        }
        System.out.println("----------------------------");
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: <hostname> <name> <ships_file>");
            return;
        }
        new TorpedoClient(args[0], args[1], args[2]).run();
    }

}
import java.io.PrintStream;
import java.util.Scanner;

public class Board implements Cloneable {

    enum Type {
        WATER,
        SHIP_INTACT,
        SHIP_HIT,
        MISS
    }

    private static final int BOARD_SIZE = 10;
    private Type[][] map;

    // Beolvas egy hajolistat, ami a "---" sorig vagy a bemenet vegeig tart.
    public Board(Scanner in) {
        map = new Type[BOARD_SIZE][BOARD_SIZE];
        for (int iy = 0; iy < BOARD_SIZE; ++iy) {
            for (int ix = 0; ix < BOARD_SIZE; ++ix) {
                set(ix, iy, Type.WATER);
            }
        }

        while (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println(line);
            if (line.equals("---")) {
                break;
            }
            Scanner lineScanner = new Scanner(line);
            String shipType = lineScanner.next();
            int x = lineScanner.nextInt();
            int y = lineScanner.nextInt();
            switch (shipType) {
                case "X":
                    placeShip(x, y);
                    break;
                case "I":
                    placeShip(x, y - 1);
                    placeShip(x, y);
                    placeShip(x, y + 1);
                    break;
                case "-":
                    placeShip(x - 1, y);
                    placeShip(x, y);
                    placeShip(x + 1, y);
                    break;
            }
        }
    }

    // Megjeleniti a terkepet
    private void print(PrintStream out) {
        for (int iy = 0; iy < BOARD_SIZE; ++iy) {
            for (int ix = 0; ix < BOARD_SIZE; ++ix) {
                out.print(visualise(get(ix, iy)));
            }
            out.println();
        }
    }

    // Visszaadja a karakteres reprezentaciojat a tablanak
    private char visualise(Type type) {
        switch (type) {
            case WATER:
                return '.';
            case MISS:
                return '*';
            case SHIP_INTACT:
                return 'X';
            case SHIP_HIT:
                return '!';
        }
        return '?';
    }

    // Megmondja, az adott koordinata rajta van e a tablan
    boolean onMap(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    // Mi az adott koordinata tipusa?
    // Csak valid koordinatakat fogad el!
    Type get(int x, int y) {
        return map[y][x];
    }

    // Megvaltoztatja, mi van a koordinatan
    // Csak valid koordinatakat fogad el!
    void set(int x, int y, Type t) {
        map[y][x] = t;
    }

    // Lehelyez egy hajo elemet, ha a koordinatak a tablan vannak
    private void placeShip(int x, int y) {
        if (onMap(x, y)) {
            set(x, y, Type.SHIP_INTACT);
        }
    }

    // Lo az adott cellara. Igazzal ter vissza, ha a koordinat a tablan van.
    public boolean hit(int x, int y) {
        if (onMap(x, y)) {
            switch (get(x, y)) {
                case SHIP_INTACT:
                    set(x, y, Type.SHIP_HIT);
                    break;
                case WATER:
                    set(x, y, Type.MISS);
                    break;
            }
            return true;
        }
        return false;
    }

    // Megmondja, hany ep hajo van meg a tablan.
    public int remaining() {
        int count =0;
        for (int iy = 0; iy < BOARD_SIZE; ++iy) {
            for (int ix = 0; ix < BOARD_SIZE; ++ix) {
                if (get(ix, iy) == Type.SHIP_INTACT) count++;
            }
        }
        return count;
    }
}




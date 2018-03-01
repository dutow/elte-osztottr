import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Numbers {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: <infile> <outfile>");
            return;
        }

        try (
                Scanner in = new Scanner(new File(args[0]));
                PrintWriter out = new PrintWriter(args[1])) {

            while (in.hasNextInt()) {
                out.println(in.nextInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
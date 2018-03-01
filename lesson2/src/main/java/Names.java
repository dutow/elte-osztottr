import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class Names {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: <infile>");
            return;
        }

        ArrayList<Name> list = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {

            String str;
            while ((str = in.readLine()) != null) {
                String[] split = str.split(" ");
                if (split.length == 2) {
                    list.add(new Name(split[0], split[1]));
                }
            }
        } catch (IOException e) {
        }

        Collections.sort(list);

        String previousLastName = "";

        for (Name n : list) {
            if (!previousLastName.equals(n.getLastName())) {
                previousLastName = n.getLastName();
                System.out.println(previousLastName + ":");
            }
            System.out.println(n);
        }
    }

}
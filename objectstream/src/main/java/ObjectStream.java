import java.beans.Transient;
import java.io.*;
import java.util.ArrayList;

public class ObjectStream {

    static class Data1 implements Serializable {
        ArrayList<Integer> stuff = new ArrayList<>();

        transient Double otherStuff;

        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return stuff + " -- " + otherStuff;
        }
    }

    static class Data2 implements Serializable {
        Double stuff;

        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return String.valueOf(stuff);
        }
    }

    static class NotSerializbleData {
        Integer stuff;

        @Override
        public String toString() {
            return String.valueOf(stuff);
        }
    }

    public static void main(String[] args) {

        try (
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(new File("objects.dat")));
        ) {
            {
                Data1 d = new Data1();
                d.otherStuff = 42.0;
                d.stuff.add(1);
                d.stuff.add(2);
                out.writeObject(d);
                d.stuff.add(3);
                d.otherStuff = 40.0;
                out.writeObject(d);
                out.reset();
                d.stuff.add(4);
                out.writeObject(d);
            }
            {
                Data2 d = new Data2();
                out.writeObject(d);
                d.stuff = 13.37;
                out.reset();
                out.writeObject(d);
            }
            try{
                NotSerializbleData d = new NotSerializbleData();
                out.writeObject(d);
            } catch(Exception e) {
                System.out.println("E1 - " + e);
            }
        } catch (IOException e) {
            System.out.println("E2 - " + e);
        }

        System.out.println("------------");

        try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File("objects.dat"))))
        {
            while(true) {
                try {
                    Object o = oi.readObject();
                    System.out.println(o);
                } catch (EOFException e) {
                    System.out.println("E3 - " + e);
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("E4 - " + e);
                } catch (Exception e) {
                    System.out.println("E5 - " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("E6 - " + e);
        }
    }

}

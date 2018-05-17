import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Serial implements Serializable {

  int i;

  Serial(int i) {
    this.i = i;
  }

  public static void main(String[] args) {

    try (
        FileOutputStream fileOut = new FileOutputStream("things.dat");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        ) {
      out.writeObject(new Serial(42));
      out.writeObject(new Serial(24));
    } catch (IOException e) {
      e.printStackTrace();
    }

    try (
      FileInputStream fileIn = new FileInputStream("things.dat");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      ){
      Serial s = (Serial) in.readObject();
      System.out.println(s.i);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

  }

}

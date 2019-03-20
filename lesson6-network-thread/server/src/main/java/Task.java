import java.util.Scanner;

public class Task {

    private int id;
    private int inputNumber;
    private int result;

    public Task(String input) {
        Scanner sc = new Scanner(input);
        id = sc.nextInt();
        inputNumber = sc.nextInt();
        try {
            Thread.sleep(((inputNumber * 137 + 67) % 13) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = (inputNumber * 42 + 13) % 1337;
    }

    public int getId() {
        return id;
    }

    public int getInputNumber() {
        return inputNumber;
    }

    public int getResult() {
        return result;
    }
}

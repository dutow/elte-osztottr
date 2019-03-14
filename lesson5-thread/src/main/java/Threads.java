public class Threads {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> characterPrinter("hello"));
        new Thread(() -> characterPrinter("vilag")).start();
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
        }
        characterPrinter("!");
    }

    private static /* synchronized */ void characterPrinter(String hello) {
        for(char c: hello.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}

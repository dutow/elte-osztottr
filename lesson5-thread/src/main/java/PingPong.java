public class PingPong {

    static class Printer implements Runnable {

        public Printer(Object lock, Object otherLock, String msg) {
            this.lock = lock;
            this.otherLock = otherLock;
            this.msg = msg;
        }

        @Override
        public void run() {
            while(true) {
                System.out.println(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                synchronized (otherLock) {
                    otherLock.notify();
                }
                try {
                    synchronized (lock) {
                        lock.wait(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Object lock;
        Object otherLock;
        String msg;
    }

    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        new Thread(new Printer(lock1, lock2, "ping")).start();
        new Thread(new Printer(lock2, lock1, "pong")).start();
    }

}

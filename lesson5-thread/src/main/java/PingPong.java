public class PingPong {

    static class Jelzo {
        boolean mehet = false;
    }

    static class Printer implements Runnable {

        public Printer(Jelzo lock, Jelzo otherLock, String msg) {
            this.lock = lock;
            this.otherLock = otherLock;
            this.msg = msg;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    while(lock.mehet == false) {
                        synchronized (lock) {
                            lock.wait();
                        }
                    }
                    synchronized (lock) {
                        lock.mehet = false;
                    }
                } catch (InterruptedException e) { }
                System.out.println(msg);
                try { Thread.sleep(1000); } catch (InterruptedException e) { }
                synchronized (otherLock) {
                    otherLock.mehet = true;
                    otherLock.notify();
                }
            }
        }

        Jelzo lock;
        Jelzo otherLock;
        String msg;
    }

    public static void main(String[] args) throws InterruptedException {
        Jelzo lock1 = new Jelzo();
        Jelzo lock2 = new Jelzo();

        new Thread(new Printer(lock1, lock2, "ping")).start();
        new Thread(new Printer(lock2, lock1, "pong")).start();


        //Thread.sleep(100);

        synchronized (lock1) {
            lock1.mehet = true;
            lock1.notify();
        }
    }

}

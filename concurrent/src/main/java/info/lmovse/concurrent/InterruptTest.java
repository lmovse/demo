package info.lmovse.concurrent;

public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new InterruptRunnable());
        t.start();
        Thread.sleep(5000);
        t.interrupt();
        System.out.println(" 我中断了线程 t");

    }

    static class InterruptRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.println(" 我还在执行哦 ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

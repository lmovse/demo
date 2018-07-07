package info.lmovse.concurrent;

public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println(" 我还在执行哦 ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        Thread.sleep(5000);
        t.interrupt();
        System.out.println(" 我中断了线程 t");
    }
}

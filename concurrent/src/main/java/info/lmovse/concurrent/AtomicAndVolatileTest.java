package info.lmovse.concurrent;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicAndVolatileTest {
    private volatile int volatileInt = 0;

    @Test
    @SuppressWarnings("all")
    public void volatileTest() throws InterruptedException {
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1000);
        for (int x = 0; x < threadPool.getCorePoolSize(); x++) {
            threadPool.execute(() -> volatileInt++);
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            Thread.sleep(1000);
        }
        System.out.println(volatileInt); // result: 996
    }

    @Test
    public void atomicTest() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1000);
        for (int x = 0; x < threadPool.getCorePoolSize(); x++) {
            threadPool.execute(atomicInteger::incrementAndGet);
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            Thread.sleep(1000);
        }
        System.out.println(atomicInteger.get()); // result: 1000
    }
}

import org.junit.Test;

/**
 * @author pwd
 * @create 2018-07-26 20:39
 **/
public class TestOther {

    @Test
    public void test() throws InterruptedException {
        final Object myOb = 12;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                synchronized (myOb) {
                    try {
                        System.out.println("starting for " + i++);
                        myOb.wait();
                        System.out.println("notify for " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        thread.sleep(2000);
        thread.interrupt();
        thread.sleep(5000);
        synchronized (myOb) {
            myOb.notifyAll();
        }
    }

}

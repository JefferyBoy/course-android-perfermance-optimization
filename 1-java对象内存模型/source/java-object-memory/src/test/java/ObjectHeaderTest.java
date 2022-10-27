import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class ObjectHeaderTest {

    // 对象头
    @Test
    public void testObjectHeader() {
        // 打印类或对象的内存分配情况
        Person person = new Person();
        System.out.println("HashCode = " + Integer.toHexString(person.hashCode()));
        Runtime.getRuntime().gc();
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
    }

    // 对象链图
    @Test
    public void testObjectGraph() {
        // 打印对象链内存分配情况
        Person jack = new Person();
        Person amy = new Person();
        jack.friend = amy;
        amy.friend = jack;
        System.out.println(GraphLayout.parseInstance(jack).toPrintable());
    }

    // markWord中的hashCode
    @Test
    public void testMarkWordHashCode() {
        Person person = new Person();
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
        System.out.println(Integer.toHexString(person.hashCode()));
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
    }

    // 偏向锁，jvm启动后有4s偏向锁延迟
    @Test
    public void testMarkWordBiasedLock() throws InterruptedException {
        Thread.sleep(5000);
        Person person = new Person();
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
        // 调用hashCode后，不再产生偏向锁，直接进入轻量级锁
//        person.hashCode();
//        System.identityHashCode(person);
        synchronized (person) {
            System.out.println(ClassLayout.parseInstance(person).toPrintable());
            synchronized (person) {
                System.out.println(ClassLayout.parseInstance(person).toPrintable());
            }
        }
    }

    // 轻量级锁
    @Test
    public void testMarkWordThinLock() throws InterruptedException {
        Thread.sleep(5000);
        Person person = new Person();
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
        person.hashCode();
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
        synchronized (person) {
            System.out.println(ClassLayout.parseInstance(person).toPrintable());
        }
    }

    // 重量级锁
    @Test
    public void testMarkWordFatLock() {
        Person person = new Person();
        System.out.println(ClassLayout.parseInstance(person).toPrintable());
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (person) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(ClassLayout.parseInstance(person).toPrintable());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (person) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(ClassLayout.parseInstance(person).toPrintable());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

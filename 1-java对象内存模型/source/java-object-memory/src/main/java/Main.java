import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class Main {

    public static void main(String[] args) {
        // byte   char  int  float   long  double  short
        // 1      2     4    4       4     8       2
        // Byte   Char  Integer Float Long Double  Short
        byte a = 10;
        int b = 20;
        Byte c = 10;
        Byte c2 = 10;
        Integer d = 20;
        System.out.println(ClassLayout.parseInstance(c).toPrintable());
        System.out.println(ClassLayout.parseInstance(c2).toPrintable());
        System.out.println(ClassLayout.parseInstance(d).toPrintable());

        Person p = new Person();
        System.out.println(ClassLayout.parseInstance(p).toPrintable());
    }

    public void test() {
        Object lock = new Object();
        synchronized (lock) {
            synchronized (lock) {

            }
        }
    }
}

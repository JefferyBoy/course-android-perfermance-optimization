
public class BoxAndUnBox {


    // 自动拆装箱，查看java字节码
    public void box() {
        int a = 10;
        Integer b = a;
        int c = b.intValue();
    }
}

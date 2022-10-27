import sun.misc.Unsafe;

import java.nio.ByteBuffer;

public class OutHeapMemory {

    public void testByteBuffer() {
        Unsafe.getUnsafe().allocateMemory(32);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(16);
    }
}

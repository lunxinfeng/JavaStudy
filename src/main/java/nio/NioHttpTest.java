package nio;

import java.io.IOException;
import java.nio.channels.Selector;

public class NioHttpTest {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];

        ports[0] = 10000;
        ports[1] = 10001;
        ports[2] = 10002;
        ports[3] = 10003;
        ports[4] = 10004;

        Selector selector = Selector.open();
    }
}

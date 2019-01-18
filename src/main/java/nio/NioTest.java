package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

public class NioTest {
    public static void main(String[] args) throws IOException {
//        Test1();
//        Test2(); //读文件
//        Test3(); //写文件
        Test4(); //从一个文件读取数据写入另一个文件
    }

    private static void Test1() {
        IntBuffer intBuffer = IntBuffer.allocate(10);

        for (int i = 0; i < intBuffer.capacity(); i++){
            int randomNum = new SecureRandom().nextInt(20);
            intBuffer.put(randomNum);
        }

        intBuffer.flip();
        //等效
//        intBuffer.limit(intBuffer.position());
//        intBuffer.position(0);

        intBuffer.clear();

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }

    private static void Test2() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/main/java/nio/io.md");

        //io --> nio
        FileChannel fileChannel = fileInputStream.getChannel();

        //创建buffer，分配大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        //从Channel中读取数据到Buffer
        fileChannel.read(byteBuffer);

        //Buffer状态反转   往buffer写 --> 从buffer读
        byteBuffer.flip();

        while (byteBuffer.hasRemaining()){
            byte b = byteBuffer.get();
            System.out.println("Char：" + (char)b);
        }

        fileInputStream.close();
    }

    private static void Test3() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/java/nio/test.txt");

        //io --> nio
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建buffer，分配大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);

        byte[] text = "hello world".getBytes();

        byteBuffer.put(text);

        //Buffer状态反转
        byteBuffer.flip();

        //从Channel中读取数据到Buffer
        fileChannel.write(byteBuffer);

        fileOutputStream.close();
    }

    private static void Test4() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/main/java/nio/test.txt");
        FileOutputStream outputStream = new FileOutputStream("src/main/java/nio/test_out.txt");

        FileChannel channelIn = inputStream.getChannel();
        FileChannel channelOut = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(50);

        while (true){
            byteBuffer.clear();

            int read = channelIn.read(byteBuffer);
            if (read == -1)
                break;
            byteBuffer.flip();
            channelOut.write(byteBuffer);
        }

        inputStream.close();
        outputStream.close();
        channelIn.close();
        channelOut.close();
    }
}

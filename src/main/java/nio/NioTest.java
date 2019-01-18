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
//        Test4(); //从一个文件读取数据写入另一个文件
//        Test5(); //类型化得get和put
//        Test6(); //Buffer分片
        Test7(); //只读Buffer
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
        FileInputStream fileInputStream = new FileInputStream("src/main/java/nio/nio.md");

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

    private static void Test5(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);

        byteBuffer.putInt(5);
        byteBuffer.putLong(50L);
        byteBuffer.putDouble(10.5D);
        byteBuffer.putChar('嗨');
        byteBuffer.putShort((short) 3);

        byteBuffer.flip();

        //放进去是什么类型，取得时候就要是什么类型。因为每种类型占据得长度是不一样得
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }

    private static void Test6(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);

        for (int i = 0; i < byteBuffer.capacity(); i++){
            byteBuffer.put((byte) i);
        }

        byteBuffer.position(2);
        byteBuffer.limit(5);

        ByteBuffer byteBufferSlice = byteBuffer.slice();

        System.out.println(byteBufferSlice); //java.nio.HeapByteBuffer[pos=0 lim=3 cap=3]

        for (int i = 0; i < byteBufferSlice.capacity(); i++){
            System.out.println(byteBufferSlice.get()); // 2 3 4
        }
    }

    private static void Test7(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);

        for (int i = 0; i < byteBuffer.capacity(); i++){
            byteBuffer.put((byte) i);
        }

        ByteBuffer byteBufferReadOnly = byteBuffer.asReadOnlyBuffer();

        System.out.println(byteBufferReadOnly); //java.nio.HeapByteBuffer[pos=0 lim=3 cap=3]
    }

    private static void Test8() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/main/java/nio/test.txt");
        FileOutputStream outputStream = new FileOutputStream("src/main/java/nio/test_out.txt");

        FileChannel channelIn = inputStream.getChannel();
        FileChannel channelOut = outputStream.getChannel();

//        ByteBuffer byteBuffer = ByteBuffer.allocate(50); //创建的为HeapByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(50); //创建的为DirectByteBuffer

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

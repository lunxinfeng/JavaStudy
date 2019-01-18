package mina.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        // 接收客户端的数据
        IoBuffer ioBuffer = (IoBuffer) message;
        byte[] byteArray = new byte[ioBuffer.limit()];
        ioBuffer.get(byteArray, 0, ioBuffer.limit());
        System.out.println("======messageReceived:" + new String(byteArray, "UTF-8"));

        // 发送到客户端
        byte[] responseByteArray = "你好啊，Netty！我是Mina。".getBytes("UTF-8");
        IoBuffer responseIoBuffer = IoBuffer.allocate(responseByteArray.length);
        responseIoBuffer.put(responseByteArray);
        responseIoBuffer.flip();
        session.write(responseIoBuffer);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("======sessionCreated：");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("======sessionClosed：");
    }
}

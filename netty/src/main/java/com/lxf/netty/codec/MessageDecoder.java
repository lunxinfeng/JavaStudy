package com.lxf.netty.codec;

import com.lxf.netty.message.MsgPack;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        System.out.println(in.getIntLE(0));
////        System.out.println(in.getIntLE(1));
////        System.out.println(in.getIntLE(2));
////        System.out.println(in.getIntLE(3));
//        System.out.println(in.getIntLE(4));
////        System.out.println(in.getIntLE(5));
////        System.out.println(in.getIntLE(6));
////        System.out.println(in.getIntLE(7));
//        System.out.println(in.getIntLE(8));
////        System.out.println(in.getIntLE(9));
////        System.out.println(in.getIntLE(10));
////        System.out.println(in.getIntLE(11));
//        System.out.println(in.getIntLE(12));
////        System.out.println(in.getIntLE(13));
////        System.out.println(in.getIntLE(14));
////        System.out.println(in.getIntLE(15));
//
////        in.getBytes(16,msg);
////        System.out.println(in.getBytes(16,msg).toString(Charset.forName("utf-8")));


        MsgPack msgPack = new MsgPack();
        msgPack.setMsgLength(in.readIntLE());
        msgPack.setMsgMethod(in.readIntLE());
        msgPack.setMsgGroupId(in.readIntLE());
        msgPack.setMsgToID(in.readIntLE());
        byte[] msg = new byte[in.getIntLE(0)];
        in.readBytes(msg);
        msgPack.setMsgPack(new String(msg));

        int idLength = in.readIntLE();
        byte[] ids = new byte[idLength];
        in.readBytes(ids);
        msgPack.setId(new String(ids));
        msgPack.setType(in.readIntLE());

        out.add(msgPack);
    }

}

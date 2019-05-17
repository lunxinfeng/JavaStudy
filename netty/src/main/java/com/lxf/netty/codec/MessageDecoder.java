package com.lxf.netty.codec;

import com.lxf.netty.log.Log;
import com.lxf.netty.message.MsgPack;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() > 4){
            //数据太大  不正常
            if (in.readableBytes() > 2048) {
                in.skipBytes(in.readableBytes());
            }

            int msgLength = in.getIntLE(0);
            if (in.readableBytes() < 16 + msgLength){
                Log.info("半包信息");
                return;
            }

            msgLength = in.readIntLE();
            int msgMethod = in.readIntLE();
            int msgGroupId = in.readIntLE();
            int toId = in.readIntLE();

            byte[] msg = new byte[msgLength];
            in.readBytes(msg);


            MsgPack msgPack = new MsgPack();
            msgPack.setMsgLength(msgLength);
            msgPack.setMsgMethod(msgMethod);
            msgPack.setMsgGroupId(msgGroupId);
            msgPack.setMsgToID(toId);
            msgPack.setMsgPack(new String(msg));

            out.add(msgPack);
        }
    }

}

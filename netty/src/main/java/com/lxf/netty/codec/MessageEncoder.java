package com.lxf.netty.codec;

import com.lxf.netty.message.MsgPack;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class MessageEncoder extends MessageToByteEncoder<MsgPack> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MsgPack msg, ByteBuf out) throws Exception {
//        out.order();
        out.writeIntLE(msg.getMsgLength());
        out.writeIntLE(msg.getMsgMethod());
        out.writeIntLE(msg.getMsgGroupId());
        out.writeIntLE(msg.getMsgToID());
        out.writeBytes(msg.getMsgPack().getBytes(Charset.forName("utf-8")));

    }
}

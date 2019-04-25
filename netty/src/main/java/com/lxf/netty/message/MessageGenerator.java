package com.lxf.netty.message;

import java.nio.charset.Charset;

public class MessageGenerator {
    public static MsgPack heart(){
        return message(0,0,0,"*","0",0);
    }

    public static MsgPack message(int method,int groupId,int toId,String msg,String id,int type){
        MsgPack msgPack = new MsgPack();
        msgPack.setMsgMethod(method);
        msgPack.setMsgGroupId(groupId);
        msgPack.setMsgToID(toId);
        msgPack.setMsgPack(msg);
        msgPack.setMsgLength(msg.getBytes(Charset.forName("utf-8")).length);
        msgPack.setId(id);
        msgPack.setType(type);
        return msgPack;
    }
}

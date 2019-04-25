package com.lxf.netty.message;

import com.lxf.netty.i.Message;

import java.io.Serializable;

/**
 * @author Herman.Xiong
 * @date 2014年6月11日 11:31:45
 */
public class MsgPack implements Serializable, Message {
    /**
     * 序列化和反序列化的版本号
     */
    private static final long serialVersionUID = 1L;
    // 消息长度
    private int msgLength;
    // 消息方法
    private int msgMethod;
    // 讨论组ID
    private int msgGroupID; // 0表示大厅用户
    // 目标用户ID
    private int msgToID; // 0表示组里所有人
    // 消息包内容
    private String msgPack;

    /**
     * 唯一标识：可以 时间戳+用户id，用来去重
     */
    private String id;
    /**
     * 消息类型：
     * A发送消息给B的流程
     * 1.A请求服务器，发送消息
     * 2.服务器响应A，表示自己收到了，通过id来鉴别是否是重发信息
     * 3.服务器转发给B
     * 4.B发送确认消息给服务器，表示自己收到
     * 5.服务器响应B，表示自己收到
     * 6.服务器通知A，B接收到了信息
     *
     * 如果A一段时间没有收到6，重发
     */
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MsgPack() {
    }

    public int getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }

    public int getMsgMethod() {
        return msgMethod;
    }

    public void setMsgMethod(int msgMethod) {
        this.msgMethod = msgMethod;
    }

    public int getMsgGroupId() {
        return msgGroupID;
    }

    public void setMsgGroupId(int msgGroupID) {
        this.msgGroupID = msgGroupID;
    }

    public int getMsgToID() {
        return msgToID;
    }

    public void setMsgToID(int msgToID) {
        this.msgToID = msgToID;
    }

    public String getMsgPack() {
        return msgPack;
    }

    public void setMsgPack(String msgPack) {
        this.msgPack = msgPack;
    }

    @Override
    public String toString() {
        return "MsgPack{" +
                "msgLength=" + msgLength +
                ", msgMethod=" + msgMethod +
                ", msgGroupID=" + msgGroupID +
                ", msgToID=" + msgToID +
                ", msgPack='" + msgPack + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                '}';
    }
}

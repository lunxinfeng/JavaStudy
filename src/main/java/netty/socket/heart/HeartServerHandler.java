package netty.socket.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);

        if (evt instanceof IdleStateEvent){
            String msg = null;
            switch (((IdleStateEvent) evt).state()){
                case READER_IDLE:
                    msg = "读空闲";
                    break;
                case WRITER_IDLE:
                    msg = "写空闲";
                    break;
                case ALL_IDLE:
                    msg = "读写空闲";
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + "：" + msg);

//            ctx.channel().close();
        }
    }
}

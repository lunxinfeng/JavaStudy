import com.google.gson.Gson
import com.lxf.netty.Client
import com.lxf.netty.MinaManager
import com.lxf.netty.callback.ConnectListener
import com.lxf.netty.message.MsgPack
import com.lxf.rxretrofit.RetrofitHelper
import io.netty.channel.Channel
import java.nio.charset.Charset
import java.util.*

fun main(args: Array<String>) {
//    connectWH()
    connectLocal()
}

private fun connectLocal(){
    Client.Builder("127.0.0.1",8901)
            .connectListener( object : ConnectListener {
                override fun onConnectSuccess(client: Client) {
//                    val info = Mina.register(user.userid, user.account, Date().toString(), user.level, "注册成功", user.roomtype, user.dypassword)
//                    val msg = MsgPack()
//                    msg.msgMethod = Mina.register
//                    msg.msgGroupId = 0
//                    msg.msgToID = 0
//                    msg.msgPack = info
//                    msg.msgLength = info.toByteArray(Charset.forName("utf-8")).size
//                    println("准备发送：$msg")
//                    MinaManager.minaSend(1, 0, 0, info, 8901)
//                        client.channel.writeAndFlush(msg)
//                                .addListener { future ->
//                                    if (future.isSuccess()) {
//                                        println("发消息isSuccess")
//                                    }
//
//                                    if (future.isDone()) {
//                                        println("发消息isDone")
//                                    }
//
//                                    if (future.isCancelled()) {
//                                        println("发消息isCancelled")
//                                    }
//
//                                    if (future.isCancellable()) {
//                                        println("发消息isCancellable")
//                                    }
//
//                                    if (future.cause()!=null) {
//                                        future.cause().printStackTrace()
//                                    }
//                                }
                }

                override fun onConnectFail() {
                    println("onConnectFail")
                }
            })
            .build().connect()
}

private fun connectWH() {
    RetrofitHelper.getInstance()
            .baseUrl("http://47.99.41.182:8899/whgoedu/")
            .create(Api::class.java)
            .get(RequestModel().apply {
                code = "010101"
                info = "{\"root\":[{\"loginname\":\"13397610436\",\"password\":\"123456\"}]}"
            })
            .subscribe {
                println(it.data)
                val user = Gson().fromJson(it.data, LoginUser::class.java)
                MinaManager.connectServer(8901, object : ConnectListener {
                    override fun onConnectSuccess(client: Client) {
                        val info = Mina.register(user.userid, user.account, Date().toString(), user.level, "注册成功", user.roomtype, user.dypassword)
                        val msg = MsgPack()
                        msg.msgMethod = Mina.register
                        msg.msgGroupId = 0
                        msg.msgToID = 0
                        msg.msgPack = info
                        msg.msgLength = info.toByteArray(Charset.forName("utf-8")).size
                        println("准备发送：$msg")
//                        MinaManager.minaSend(1, 0, 0, info, 8901)
//                        client.channel.writeAndFlush(msg)
//                                .addListener { future ->
//                                    if (future.isSuccess()) {
//                                        println("发消息isSuccess")
//                                    }
//
//                                    if (future.isDone()) {
//                                        println("发消息isDone")
//                                    }
//
//                                    if (future.isCancelled()) {
//                                        println("发消息isCancelled")
//                                    }
//
//                                    if (future.isCancellable()) {
//                                        println("发消息isCancellable")
//                                    }
//
//                                    if (future.cause()!=null) {
//                                        future.cause().printStackTrace()
//                                    }
//                                }
                    }

                    override fun onConnectFail() {
                        println("onConnectFail")
                    }
                }, null)
//                Client("127.0.0.1",8901).apply {
//                    setConnectListener()
//                }.connectServer()
            }
}
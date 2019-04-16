# 消息模式
ClientA ----  Server  ----  ClientB

三方通信

# 消息类型
- R：客户端向服务器发送消息
- A：服务器响应消息给客户端
- N：服务器将消息通知给其他客户端

# 一次完整的通信
- ClientA发送 R 到Server
- Server返回 A 给ClientA，此时给ClientA可以确保服务器收到信息
- Server通知 N 给ClientB
- ClientB再发送请求包 R 给Server
- Server响应 A 给ClientB，此时给ClientB可以确保服务器收到自己的确认信息
- Server通知 N 给ClientA，此时ClientA可以确认自己发送给ClientB的信息成功

# 消息超时和重发
如果ClientA规定时间内没有收到ClientB的确认信息，那么ClientA重新发送信息。

# 消息去重
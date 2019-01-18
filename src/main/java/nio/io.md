Java IO And NIO
# IO
> java.io中最为核心的一个概念：流（Stream），面向流的编程。java中一个流要么是输入流要么是输出流，不可能同时既是输入流又是输出流。

- 输入流、输出流
- 字节流、字符流

基于装饰者模式设计的。

# NIO
> java.nio中有3个核心概念：Selector，Channel和Buffer。在java.nio中，我们是面向块（block）或者面向缓冲区（buffer）编程的。

## Buffer
### 基本概念
>A container for data of a specific primitive type.

>一个特殊的原生类型的数据容器。

Buffer本身就是一块内存，底层实现实际上就是一个数组。数据的读写都是通过Buffer来实现的。与io不同的是既可以读又可以写。

除了数组之外，Buffer还提供了对于数据的结构化访问方式，并且可以追踪到系统的读写过程。

Java中的7中原生数据类型都有各自对应的Buffer类型，如IntBuffer、LongBuffer、ByteBuffer和CharBuffer等，并没有BooleanBuffer。

三个核心状态属性：
- capacity：表示容器内可包含的元素的个数。永远不会是负数，且不可改变。
- limit：不能被读或写的第一个元素的索引。永远不会是负数，且不会超过capacity。
- position：下一个将要被读或写的元素的索引。永远不会是负数，且不会超过limit。

### 数据传输
>数据的读写操作总是相对于当前的position。

该类的每个子类都有两种get和put操作：
- 相对操作：读写一个或多个元素时，position会相应发生改变。如果get操作使position超出limit，会抛出BufferUnderflowException异常；如果
是put操作，则抛出BufferOverflowException异常。发生任何一种情况时，数据不会被传输。
- 绝对操作：直接根据给定的索引放置相应的元素，不改变position。如果最后索引超出limit，抛出IndexOutOfBoundsException异常。
### 标记和重置
mark和reset是搭配来使用的，当你调用reset方法时，可以将position重置到mark的索引。mark不可为负数，也不可超过position。
如果定义了mark，则当position或limit调整为小于mark值时，改mark将被丢弃。如果没有定义mark，则调用reset会抛出InvalidMarkException异常。
### 不变性
>0 <= mark <= position <= limit <= capacity

新创建Buffer时position为0，mark为未定义，limit可能是0，也可能是其他值，取决于Buffer的创建方式。一个新创建的Buffer的每个初始元素都为0.
### 附加的操作
除了position、limit、capacity、marking、resetting方法之外，还有一些对缓存区进行操作的方法：
- clear：将position重值为0，limit重置为capacity，mark重置为未定义。一般使Buffer为通道读取或相对put操作的新序列做好准备。
```java
    public Buffer clear() {
        position = 0;
        limit = capacity;
        mark = -1;
        return this;
    }
```
- flip：将limit设置为position，然后将position重置为0，mark重置为未定义。一般使Buffer为通道写入或相对get操作的新序列做好准备。
```java
    public Buffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }
```
- rewind：保持limit不变，position重置为0，mark重置为未定义。一般使Buffer准备好重新读取它已经包含的数据。
```java
    public Buffer rewind() {
        position = 0;
        mark = -1;
        return this;
    }
```
- slice：创建Buffer的子序列：它保持limit和position不变。
- duplicate：复制创建一个Buffer的浅副本：它保持limit和position不变。
### 只读Buffer
每个Buffer都是可读的，但并不是每个Buffer都是可写的。Buffer是否只读可以通过isReadOnly方法来判断。

只读Buffer不允许改变其内容，否则抛出ReadOnlyBufferException异常，但可以改变mark、position、limit。
### 线程安全
多线程并发情况下，Buffer并不是线程安全的。如果存在多线程访问，需要自己添加适当的同步操作。

## Channel
Channel指的是可以向其写入数据或者是从中读取数据的对象，它类似于java.io中的Stream，但不同的是，所有数据的读写都要通过Buffer进行，永远
不会出现直接向Channel写入数据或从Channel读取数据的情况。另外Channel是双向的，一个流只可能是InputStream或者OutputStream，而Channel
打开后可以进行读取、写入或读写。

由于Channel是双向的，因此它能更好地反映除底层操作系统的真是情况：在Linux系统中，底层操作系统的通道就是双向的。

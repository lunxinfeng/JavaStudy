创建方法：
- wrap(byte[] array)：根据已有的字节数组创建一个Buffer，包含这个byte数组。但注意此方式不要直接修改byte数组，而应该通过Buffer进行修改。
- allocate(int capacity)：分配一个指定长度内存空间。返回HeapByteBuffer（堆字节缓冲）。
- allocateDirect(int capacity)：分配一个指定长度内存空间。返回DirectByteBuffer（直接字节缓冲）。



那么heap buffer和direct buffer有什么区别呢？

首先解释一下两者的区别：

heap buffer这种缓冲区是分配在堆上面的，直接由Java虚拟机负责垃圾回收，可以直接想象成一个字节数组的包装类。

direct buffer则是通过JNI在Java的虚拟机外的内存中分配了一块缓冲区(所以即使在运行时通过-Xmx指定了Java虚拟机的最大堆内存，还是可以实例化超出该大小的Direct ByteBuffer),该块并不直接由Java虚拟机负责垃圾回收收集，但是在direct buffer包装类被回收时，会通过Java Reference机制来释放该内存块。(但Direct Buffer的JAVA对象是归GC管理的，只要GC回收了它的JAVA对象，操作系统才会释放Direct Buffer所申请的空间)

两者各有优劣势:direct buffer对比 heap buffer:

劣势：创建和释放Direct Buffer的代价比Heap Buffer得要高；

优势：当我们把一个Direct Buffer写入Channel的时候，就好比是“内核缓冲区”的内容直接写入了Channel，这样显然快了，减少了数据拷贝（因为我们平时的read/write都是需要在I/O设备与应用程序空间之间的“内核缓冲区”中转一下的）。而当我们把一个Heap Buffer写入Channel的时候，实际上底层实现会先构建一个临时的Direct Buffer，然后把Heap Buffer的内容复制到这个临时的Direct Buffer上，再把这个Direct Buffer写出去。当然，如果我们多次调用write方法，把一个Heap Buffer写入Channel，底层实现可以重复使用临时的Direct Buffer，这样不至于因为频繁地创建和销毁Direct Buffer影响性能。

结论：Direct Buffer创建和销毁的代价很高，所以要用在尽可能重用的地方。 比如周期长传输文件大采用direct buffer，不然一般情况下就直接用heap buffer 就好。



简单的说，我们需要牢记三点： 
（1）	平时的read/write，都会在I/O设备与应用程序空间之间经历一个“内核缓冲区”。 
（2）	Direct Buffer就好比是“内核缓冲区”上的缓存，不直接受GC管理；而Heap Buffer就仅仅是byte[]字节数组的包装形式。因此把一个Direct Buffer写入一个Channel的速度要比把一个Heap Buffer写入一个Channel的速度要快。 
（3）	Direct Buffer创建和销毁的代价很高，所以要用在尽可能重用的地方。 



他山之石 
REFER: 
http://stackoverflow.com/questions/5670862/bytebuffer-allocate-vs-bytebuffer-allocatedirect 
Operating systems perform I/O operations on memory areas. These memory areas, as far as the operating system is concerned, are contiguous sequences of bytes. It's no surprise then that only byte buffers are eligible to participate in I/O operations. Also recall that the operating system will directly access the address space of the process, in this case the JVM process, to transfer the data. This means that memory areas that are targets of I/O perations must be contiguous sequences of bytes. In the JVM, an array of bytes may not be stored contiguously in memory, or the Garbage Collector could move it at any time. Arrays are objects in Java, and the way data is stored inside that object could vary from one JVM implementation to another. 
For this reason, the notion of a direct buffer was introduced. 
Direct buffers are intended for interaction with channels and native I/O routines. They make a best effort to store the byte elements in a memory area that a channel can use for direct, or raw, access by using native code to tell the operating system to drain or fill the memory area directly. 
Direct byte buffers are usually the best choice for I/O operations. By design, they support the most efficient I/O mechanism available to the JVM. Nondirect byte buffers can be passed to channels, but doing so may incur a performance penalty. It's usually not possible for a nondirect buffer to be the target of a native I/O operation. If you pass a nondirect ByteBuffer object to a channel for write, the channel may implicitly do the following on each call: 
1.	Create a temporary direct ByteBuffer object. 
2.	Copy the content of the nondirect buffer to the temporary buffer. 
3.	Perform the low-level I/O operation using the temporary buffer. 
4.	The temporary buffer object goes out of scope and is eventually garbage collected. 
This can potentially result in buffer copying and object churn on every I/O, which are exactly the sorts of things we'd like to avoid. However, depending on the implementation, things may not be this bad. The runtime will likely cache and reuse direct buffers or perform other clever tricks to boost throughput. 
If you're simply creating a buffer for one-time use, the difference is not significant. 
如果我们构造一个ByteBuffer仅仅使用一次，不复用它，那么Direct Buffer和Heap Buffer没有明显的区别。两个地方我们可能通过Direct Buffer来提高性能： 
1、	大文件，尽管我们Direct Buffer只用一次，但是如果内容很大，Heap Buffer的复制代价会很高，此时用Direct Buffer能提高性能。这就是为什么，当我们下载一个大文件时，服务端除了用SendFile机制，也可以用“内存映射”，把大文件映射到内存，也就是MappedByteBuffer，是一种Direct Buffer，然后把这个MappedByteBuffer直接写入SocketChannel，这样减少了数据复制，从而提高了性能。 

2、	重复使用的数据，比如HTTP的错误信息，例如404呀，这些信息是每次请求，响应数据都一样的，那么我们可以把这些固定的信息预先存放在Direct Buffer中（当然部分修改Direct Buffer中的信息也可以，重要的是Direct Buffer要能被重复使用），这样把Direct Buffer直接写入SocketChannel就比写入Heap Buffer要快了。 
On the other hand, if you will be using the buffer repeatedly in a high-performance scenario, you're better off allocating direct buffers and reusing them. 
Direct buffers are optimal for I/O, but they may be more expensive to create than nondirect byte buffers. 
The memory used by direct buffers is allocated by calling through to native, operating system-specific code, bypassing the standard JVM heap. Setting up and tearing down direct buffers could be significantly more expensive than heap-resident buffers, depending on the host operating system and JVM implementation. The memory-storage areas of direct buffers are not subject to garbage collection because they are outside the standard JVM heap. 
The performance tradeoffs of using direct versus nondirect buffers can vary widely by JVM, operating system, and code design. By allocating memory outside the heap, you may subject your application to additional forces of which the JVM is unaware. When bringing additional moving parts into play, make sure that you're achieving the desired effect. I recommend the old software maxim: first make it work, then make it fast. Don't worry too much about optimization up front; concentrate first on correctness. The JVM implementation may be able to perform buffer caching or other optimizations that will give you the performance you need without a lot of unnecessary effort on your part. 
Direct_Buffer_vs._Heap_Buffer.rar (16.5 KB)
下载次数: 87
2019软考实战秘籍
历年真题详解，高效备战2019年软考
分享到：    
CumulativeProtocolDecoder 流与事件模型 | 备忘 mysql add column
2011-07-26 18:27浏览 11413评论(3)分类:编程语言查看更多
评论
3 楼 duzc2 2013-12-03  
hardPass 写道
MMap是Direct Buffer 的一种，那么请问楼主，Heap Buffer的表现形式是什么？

Heap Buffer是指所有内存里的对象？包括数组、或者其他东西？

这里聊的是 ByteBuffer ， 不是虚拟机内存模型。
2 楼 hardPass 2013-06-19  
MMap是Direct Buffer 的一种，那么请问楼主，Heap Buffer的表现形式是什么？

Heap Buffer是指所有内存里的对象？包括数组、或者其他东西？
1 楼 softkf 2011-12-26  
 
发表评论
  您还没有登录,请您登录后再发表评论

相关资源推荐
《Java源码解析》NIO中的heap Buffer和direct Buffer区别
heap buffer 和 direct buffer区别在Java的NIO中，我们一般采用ByteBuffer缓冲区来传输数据，一般情况下我们创建Buffer对象是通过ByteBuffer的两个静态方法：ByteBuffer.allocate(int capacity); ByteBuffer.wrap(byte[] array);查看JDK的NIO的源代码关于这两个部分：/**allocate(

Java网络编程与NIO详解8：浅析mmap和Direct Buffer
深入理解DirectBuffer
介绍    最近在工作中使用到了DirectBuffer来进行临时数据的存放，由于使用的是堆外内存，省去了数据到内核的拷贝，因此效率比用ByteBuffer要高不少。之前看过许多介绍DirectBuffer的文章，在这里从源码的角度上来看一下DirectBuffer的原理。用户态和内核态    Intel的 X86架构下，为了实现外部应用程序与操作系统运行时的隔离，分为了Ring0-Ring3四种...

Java NIO学习笔记三（堆外内存之 DirectByteBuffer 详解）
堆外内存堆外内存是相对于堆内内存的一个概念。堆内内存是由JVM所管控的Java进程内存，我们平时在Java中创建的对象都处于堆内内存中，并且它们遵循JVM的内存管理机制，JVM会采用垃圾回收机制统一管理它们的内存。那么堆外内存就是存在于JVM管控之外的一块内存区域，因此它是不受JVM的管控。

Java Nio 的Buffer和优缺点
在数据传输的时候，我们会用到缓冲区。Java NIO中的Buffer用于和NIO通道进行交互。

线上java.lang.OutOfMemoryError问题定位三板斧
首先这是受一篇文章的启发：线上服务内存OOM问题定位三板斧OOM(OutOfMemoryError) 问题归根结底三点原因： 本身资源不够 申请的太多内存 资源耗尽 解决思路，换成Java服务分析，三个原因也可以解读为： 有可能是内存分配确实过小，而正常业务使用了大量内存 某一个对象被频繁申请，却没有释放，内存不断泄漏，导致内存耗尽 某一个资源被频繁申请，系统资源耗尽，例如：不断创建线程，不断发起网

NIO学习笔记——缓冲区（Buffer）详解
缓冲区是包在一个对象内的基本数据元素数组，Buffer类相比一个简单的数组的优点是它将关于数据的数据内容和信息包含在一个单一的对象中。Buffer的属性容量（capacity）：缓冲区能够容纳的数据元素的最大数量。这一容量在缓冲区创建时被设定，并且永远不能被改变 上界（limit）：缓冲区的第一个不能被读或写的元素。或者说，缓冲区中现存元素的计数 位置（position）：下一个要被读或写的元素

java学习-【转】NIO DirectByteBuffer 内存泄露的测试
程老师原文地址：http://flychao88.iteye.com/blog/2188489原文如下： 写NIO程序经常使用ByteBuffer来读取或者写入数据，那么使用ByteBuffer.allocate(capability)还是使用ByteBuffer.allocteDirect(capability)来分配缓存了？第一种方式是分配JVM堆内存，属于GC管辖范围，由于需要拷贝所以速度相

Java NIO通俗编程之缓冲区内部细节状态变量position,limit,capacity(二)
一、介绍 我们介绍了NIO中的两个核心对象：缓冲区和通道，在谈到缓冲区时，我们说缓冲区对象本质上是一个数组，但它其实是一个特殊的数组，缓冲区对象内置了一些机制，能够跟踪和记录缓冲区的状态变化情况，如果我们使用get()方法从缓冲区获取数据或者使用put()方法把数据写入缓冲区，都会引起缓冲区状态的变化。本节将介绍 NIO 中两个重要的缓冲区组件：状态变量和访问方法 (accessor)。状态

《Java NIO》：Channel and Buffer （通道和缓冲区）
《Java NIO》：Channel and Buffer （通道和缓冲区）从今天开始，自己将会了解下Java NIO的相关知识，以及会看下相关的类库源码。和往常自己学习新知识之前，自己都会阅读网上的一些博文和资料，以使自己对这一块的知识有一个大致的了解，然后再按照自己感兴趣的点来研究一点点知识。关于Java NIO这一块的知识，也是如此。关于Java NIO涉及到三个重要的概念：1、Chan

Java NIO —— Buffer（缓冲区）
Buffer是一个抽象类，位于java.nio包中，主要用作缓冲区。注意：Buffer是非线程安全类。capacity一旦初始化后就不会改变，其值一直为常量。在使用中我们一般使用Buffer的抽象子类ByteBuffer.allocate()方法，实际上是生成ByteArrayBuffer类。（1）Buffer中定义的变量含义/** * <code>UNSET_MARK</code> means

Java NIO中Buffer常用方法的用法及理解
一、首先，什么是Buffer? Buffer,中文意思就是缓冲区，一个用于特定基本类型的容器，由java.nio提供该类，及以下常用子类： ByteBuffer CharBuffer ShortBufer IntBuffer LongBuffer FloatBuffer DoubleBuffer，并没有BooleanBuffer.      二、其次，这么多的buffer，它的作用是什么呢？

《Java源码解析》NIO中Buffer缓冲区的实现
Buffer 缓冲区Java的NIO中Buffer至关重要：buffer是读写的中介，主要和NIO的通道交互。数据是通过通道读入缓冲区和从缓冲区写入通道的。其实缓冲区buffer的本质就是一块可以读写的内存块。这块内存块被包装成NIO的Buffer对象，并提供了一组方法方便读写。3.1 Buffer的基本用法：使用Buffer读写数据一般是下面步骤： 1. 写入数据到Buffer 2. 调用fl

Java NIO笔记(三)：NIO Buffer(缓冲区)之进阶
本节讲解NIO缓冲区(Buffer)比较dichen

Java NIO深入理解Buffer(缓冲区)
一 Buffer概述Java NIO中的Buffer用于和NIO通道进行交互。数据是从通道读入缓冲区，从缓冲区写入到通道中的。缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。二 Buffer重要知识点分析1.Buffer基本用法使用Buffer读写数据一般遵循以下四个步骤：(1) 写入数据到Buffer...

Java nio 学习笔记（一） Buffer（缓冲区）与Channel（通道）的相关知识
一．基本概念 IO 是主存和外部设备 ( 硬盘、终端和网络等 ) 拷贝数据的过程。 IO 是操作系统的底层功能实现，底层通过 I/O 指令进行完成。 所有语言运行时系统提供执行 I/O 较高级别的工具。在java编程中，标准低版本IO使用流的方式完成I/O操作，所有的I/O

NIO浅谈之Buffer基本原理
NIO从整体上分类来看可以看做由这几个部分组成：Buffer，Channel，Selector组成。本篇文章从浅谈一下Buffer。 1：Buffer的类型。可以这么来说，不同的数据类型有不同的buffer,例如ByteBuffer，IntBuffer，LongBuffer，ShortBuffer。。分别对应的数据类型为byte,int，long，short。 2：想获取一个Buffer对象的...

Java-NIO（三）：直接缓冲区与非直接缓冲区
直接缓冲区与非直接缓冲区的概念： 1）非直接缓冲区：通过 static ByteBuffer allocate(int capacity) 创建的缓冲区，在JVM中内存中创建，在每次调用基础操作系统的一个本机IO之前或者之后，虚拟机都会将缓冲区的内容复制到中间缓冲区（或者从中间缓冲区复制内容），缓冲区的内容驻留在JVM内，因此销毁容易，但是占用JVM内存开销，处理过程中有复制操作。

java NIO(十) 缓冲区——compact方法介绍
注意java6帮助文档对compact方法的介绍 compact public abstract ByteBuffer compact()压缩此缓冲区（可选操作）。 将缓冲区的当前位置和界限之间的字节（如果有）复制到缓冲区的开始处。即将索引 p = position() 处的字节复制到索引 0 处，将索引 p + 1 处的字节复制到索引 1 处，依此类推，直到将索引 limit() - 1 处

DirectByteBuffer内存回收笔记
今天在看netty源码时候又再次遇到了DirectByteBuffer，关于DirectByteBuffer的内存回收机制，在netty框架中被封装的面目全非，但其回收机制也是万变不离其宗，下面这几篇简单易懂的文章就介绍了DirectByteBuffer的概念极其内存回收方式，在这里和大家分享一下： 文章列表 jvm堆外内存–DirectByteBuffer java...


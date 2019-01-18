创建方法：
- wrap(byte[] array)：根据已有的字节数组创建一个Buffer，包含这个byte数组。但注意此方式不要直接修改byte数组，而应该通过Buffer进行修改。
- allocate(int capacity)：分配一个指定长度内存空间。返回HeapByteBuffer（堆字节缓冲）。
- allocateDirect(int capacity)：分配一个指定长度内存空间。返回DirectByteBuffer（直接字节缓冲）。
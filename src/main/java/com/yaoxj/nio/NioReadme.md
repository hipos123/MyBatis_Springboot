###Buffer
0. Bufer顾名思义，它是一个缓冲区，实际上是一个容器，一个连续数组
1. buffer allocate 分配多少空间
2. buffer hasRemaining 还有剩下的元素
3. buffer flip 读写反转
4. buffer asReadOnlyBuffer 转为只读buffer，不可以写进去
5. MappedByteBuffe 可以让文件直接在内存中修改（不是堆内存），减少了文件的拷贝
###channel



javac SocketNIO.java
strace -ff -o out java SocketNIO

man 2 socket   
通过这个可以看到
Since Linux 2.6.27, the type argument serves a second purpose: in addition to specifying a socket type, it may include the bitwise OR of any of the following values, to modify the behavior of
 socket():
SOCK_NONBLOCK   Set the O_NONBLOCK file status flag on the new open file description.  Using this flag saves extra calls to fcntl(2) to achieve the same result.
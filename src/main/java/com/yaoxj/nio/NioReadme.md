###Buffer
1. buffer allocate 分配多少空间
2. buffer hasRemaining 还有剩下的元素
3. buffer flip 读写反转
4. buffer asReadOnlyBuffer 转为只读buffer，不可以写进去
5. MappedByteBuffe 可以让文件直接在内存中修改（不是堆内存），减少了文件的拷贝
###channel
# 第二周作业
## 一. 第二周第一节作业
### 1、使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。

#### 串行GC
-XX:+UseSerialGC
```
//Minor GC, DefNew后面的信息表示年轻代的回收情况：139776-17471 = 122305，括号里面157248K表示年轻代当前容量，后面的139776K->46367K表示整个堆的情况，139776-46367 = 93409，说明有部分对象从年轻代晋升到了老年代。
2021-01-19T21:58:27.085-0800: [GC (Allocation Failure) 2021-01-19T21:58:27.085-0800: [DefNew: 139776K->17471K(157248K), 0.0492130 secs] 139776K->46367K(506816K), 0.0492815 secs] [Times: user=0.02 sys=0.02, real=0.05 secs]
...
//Full GC
2021-01-19T21:58:27.685-0800: [GC (Allocation Failure) 2021-01-19T21:58:27.685-0800: [DefNew: 156497K->156497K(157248K), 0.0000208 secs]2021-01-19T21:58:27.685-0800: [Tenured: 306968K->271051K(349568K), 0.0611719 secs] 463465K->271051K(506816K), [Metaspace: 2680K->2680K(1056768K)], 0.0612459 secs] [Times: user=0.06 sys=0.00, real=0.06 secs]
...
//Full GC，有明显的Full GC标志
[Full GC (Allocation Failure) 2021-01-19T21:58:28.424-0800: [Tenured: 349256K->340482K(349568K), 0.0740845 secs] 506487K->340482K(506816K), [Metaspace: 2680K->2680K(1056768K)], 0.0741379 secs] [Times: user=0.07 sys=0.00, real=0.07 secs]
```
在运行过程中，内存太小（216m）会OOM，不断的增加内存可以减少GC的次数，但是因为内存增大，每次回收的对象更多，每次GC的时间会增加。

#### 并行GC
-XX:+UseParallelGC
```
//日志解读和上面类似，PSYoungGen：年轻代内存回收情况，下面是Minor GC 
2021-01-19T22:16:12.867-0800: [GC (Allocation Failure) [PSYoungGen: 131584K->21498K(153088K)] 131584K->43477K(502784K), 0.0214132 secs] [Times: user=0.02 sys=0.05, real=0.02 secs]
...
//Full GC,ParOldGen:老年代内存回收情况
2021-01-19T22:16:13.590-0800: [Full GC (Ergonomics) [PSYoungGen: 36521K->0K(116736K)] [ParOldGen: 296484K->242316K(349696K)] 333006K->242316K(466432K), [Metaspace: 2680K->2680K(1056768K)], 0.0590761 secs] [Times: user=0.13 sys=0.01, real=0.05 secs]
```
和Serial GC类似，内存设置得太小会频繁触发GC，内存过大虽然减少了GC次数，但是会增加每次GC的时间。
#### CMS GC
-XX:+UseConcMarkSweepGC
```
//Minor GC ,使用的ParNew gc
2021-01-19T22:22:02.904-0800: [GC (Allocation Failure) 2021-01-19T22:22:02.904-0800: [ParNew: 306688K->34048K(306688K), 0.0723891 secs] 596785K->394739K(1014528K), 0.0724455 secs] [Times: user=0.08 sys=0.03, real=0.07 secs]

//CMS GC,阶段1：初始化标记
2021-01-19T22:22:02.976-0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 360691K(707840K)] 400522K(1014528K), 0.0006448 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
//阶段2：并发标记
2021-01-19T22:22:02.977-0800: [CMS-concurrent-mark-start]
2021-01-19T22:22:03.000-0800: [CMS-concurrent-mark: 0.023/0.023 secs] [Times: user=0.02 sys=0.01, real=0.03 secs]
//阶段3：并发预清理
2021-01-19T22:22:03.000-0800: [CMS-concurrent-preclean-start]
2021-01-19T22:22:03.002-0800: [CMS-concurrent-preclean: 0.002/0.002 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
//阶段4：可终止的并发预清理
2021-01-19T22:22:03.002-0800: [CMS-concurrent-abortable-preclean-start]
//在阶段4执行过程中，可以执行Minor GC
2021-01-19T22:22:03.056-0800: [GC (Allocation Failure) 2021-01-19T22:22:03.056-0800: [ParNew2021-01-19T22:22:03.109-0800: [CMS-concurrent-abortable-preclean: 0.002/0.106 secs] [Times: user=0.10 sys=0.03, real=0.10 secs]
: 306688K->34048K(306688K), 0.0982061 secs] 667379K->468744K(1014528K), 0.0982687 secs] [Times: user=0.09 sys=0.05, real=0.10 secs]
//阶段5：最终标记
2021-01-19T22:22:03.155-0800: [GC (CMS Final Remark) [YG occupancy: 34562 K (306688 K)]2021-01-19T22:22:03.155-0800: [Rescan (parallel) , 0.0013886 secs]2021-01-19T22:22:03.157-0800: [weak refs processing, 0.0000337 secs]2021-01-19T22:22:03.157-0800: [class unloading, 0.0003832 secs]2021-01-19T22:22:03.157-0800: [scrub symbol table, 0.0009051 secs]2021-01-19T22:22:03.158-0800: [scrub string table, 0.0001588 secs][1 CMS-remark: 434696K(707840K)] 469258K(1014528K), 0.0032586 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
//阶段6：并发清除
2021-01-19T22:22:03.158-0800: [CMS-concurrent-sweep-start]
2021-01-19T22:22:03.163-0800: [CMS-concurrent-sweep: 0.004/0.004 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
//阶段7：并发重置
2021-01-19T22:22:03.163-0800: [CMS-concurrent-reset-start]
2021-01-19T22:22:03.164-0800: [CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
2021-01-19T22:22:03.218-0800: [GC (Allocation Failure) 2021-01-19T22:22:03.218-0800: [ParNew: 306076K->34048K(306688K), 0.0295218 secs] 618868K->426584K(1014528K), 0.0295676 secs] [Times: user=0.08 sys=0.00, real=0.03 secs]
...
//CMS并发失败，退化成串行行GC
2021-01-19T22:28:52.959-0800: [Full GC (Allocation Failure) 2021-01-19T22:28:52.959-0800: [CMS2021-01-19T22:28:52.960-0800: [CMS-concurrent-preclean: 0.001/0.002 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
 (concurrent mode failure): 174624K->174693K(174784K), 0.0766201 secs] 253164K->242495K(253440K), [Metaspace: 2680K->2680K(1056768K)], 0.0777207 secs] [Times: user=0.05 sys=0.00, real=0.08 secs]
2021-01-19T22:28:53.049-0800: [Full GC (Allocation Failure) 2021-01-19T22:28:53.049-0800: [CMS: 174765K->174492K(174784K), 0.0824816 secs] 253373K->244195K(253440K), [Metaspace: 2680K->2680K(1056768K)], 0.0825480 secs] [Times: user=0.06 sys=0.01, real=0.09 secs]

```
同样的内存设置得太小会频繁触发GC，内存过大虽然减少了GC次数，但是会增加每次GC的时间，内存太小和容易触发“并发失败”，导致退化为串行GC，内存太大当存在大量内存碎片的时候，也会增加延迟。
#### G1 GC
-XX:+UseG1GC
```
//纯年轻代的暂停转移
2021-01-21T21:55:25.269-0800: 0.224: [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0094342 secs]
   [Parallel Time: 9.0 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 223.8, Avg: 223.8, Max: 223.9, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.0, Sum: 0.6]
      [Update RS (ms): Min: 0.2, Avg: 0.3, Max: 0.3, Diff: 0.1, Sum: 1.0]
         [Processed Buffers: Min: 3, Avg: 3.5, Max: 4, Diff: 1, Sum: 14]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 8.4, Avg: 8.5, Max: 8.5, Diff: 0.1, Sum: 33.8]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 8.9, Avg: 8.9, Max: 9.0, Diff: 0.0, Sum: 35.8]
      [GC Worker End (ms): Min: 232.8, Avg: 232.8, Max: 232.8, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.3 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 58.0M(97.0M)->0.0B(72.0M) Survivors: 8192.0K->14.0M Heap: 158.5M(256.0M)->108.7M(256.0M)]
 [Times: user=0.01 sys=0.02, real=0.01 secs] 

//并发标记
2021-01-21T21:55:25.340-0800: 0.295: [GC concurrent-root-region-scan-start]
2021-01-21T21:55:25.340-0800: 0.295: [GC concurrent-root-region-scan-end, 0.0001049 secs]
2021-01-21T21:55:25.340-0800: 0.295: [GC concurrent-mark-start]
2021-01-21T21:55:25.342-0800: 0.296: [GC concurrent-mark-end, 0.0011040 secs]
2021-01-21T21:55:25.342-0800: 0.296: [GC remark 2021-01-21T21:55:25.342-0800: 0.296: [Finalize Marking, 0.0000797 secs] 2021-01-21T21:55:25.342-0800: 0.296: [GC ref-proc, 0.0000271 secs] 2021-01-21T21:55:25.342-0800: 0.296: [Unloading, 0.0005375 secs], 0.0012829 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-01-21T21:55:25.343-0800: 0.297: [GC cleanup 167M->167M(256M), 0.0003072 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 

//混合暂停转移
2021-01-21T21:55:25.332-0800: 0.286: [GC pause (G1 Evacuation Pause) (mixed), 0.0023908 secs]
   [Parallel Time: 2.1 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 286.1, Avg: 286.1, Max: 286.1, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.2, Diff: 0.0, Sum: 0.5]
      [Update RS (ms): Min: 0.2, Avg: 0.3, Max: 0.3, Diff: 0.0, Sum: 1.0]
         [Processed Buffers: Min: 2, Avg: 3.2, Max: 4, Diff: 2, Sum: 13]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 1.5, Avg: 1.5, Max: 1.5, Diff: 0.0, Sum: 6.1]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.1]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 1.9, Avg: 2.0, Max: 2.0, Diff: 0.0, Sum: 7.8]
      [GC Worker End (ms): Min: 288.0, Avg: 288.0, Max: 288.1, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.2 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 7168.0K(7168.0K)->0.0B(10.0M) Survivors: 5120.0K->2048.0K Heap: 191.8M(256.0M)->164.5M(256.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
```


### 2、使用压测工具(wrk或sb)，演练gateway-server-0.0.1-SNAPSHOT.jar示例。 
经过压测，猜测是因为本身仅仅对 `http://localhost:8088/api/hello` 进行压测，对象并没有长期被引用，导致基本上新生代GC便可以腾出可用空间，所以适当加大内存可以增加Requests/sec的值，
再加上基于个人电脑测试，所以各个GC上并没有太大的差别。
### 3、(选做)如果自己本地有可以运行的项目，可以按照2的方式进行演练。
TODO
### 4、(必做)根据上述自己对于1和2的演示，写一段对于不同GC和堆内存的总结，提交到 github。
[GC分析](https://shimo.im/sheets/Qv8WXt3wrqdXX9KH/MODOC/)
总的来说，GC的堆内存设置并不是越大越好，要结合业务进行考虑，堆内存设置得太小，会导致频繁的GC，最后甚至OOM，但是设置得太大又会增加每次GC的时间。
特别的对于G1 GC来说需要的堆较大，堆的大小并不是很影响其GC时间。
![GC总结](./src/main/resources/GC总结.png)

## 二. 第二周第二节课作业
### 1、(可选)运行课上的例子，以及 Netty 的例子，分析相关现象。
压测参数 `wrk -c 40 -d30s <url>`
#### HttpServer01
![HttpServer01](./src/main/resources/HttpServer01.png)
单线程的socket程序，每个请求都是串行的，每个请求都会阻塞，一次只能处理一个请求，明显无法充分利用资源。
#### HttpServer02
![HttpServer01](./src/main/resources/HttpServer01.png)
每个请求一个线程，频繁的创建，销毁线程，造成巨大的开销。并且会出现同一时刻大量线程处于阻塞状态，以及大量线程频繁进行上下文切换，拉低应用性能。
#### HttpServer03
![HttpServer01](./src/main/resources/HttpServer01.png)
使用线程池，虽然省去了频繁的创建，销毁线程的开销，但在IO模型上本质还是阻塞的。
#### NettyHttpServer
![HttpServer01](./src/main/resources/netty.png)
上面都是基于BIO进行的实现，是阻塞的，并且基于流进行读取。netty基于NIO，采用事件驱动的方式，IO多路复用，非阻塞。


### 2、(必做)写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801，代 码提交到Github。
使用OkHttp进行实现
[OkHttpUtils.java](./src/main/java/io/github/brightloong/week02/io/utils/OkHttpUtils.java)
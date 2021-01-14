## 训练营第01周作业
### 第一次课程作业
#### 1. 自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和for，然后自己分析一下对应的字节码。
   [Hello.java 链接](./src/Hello.java)<br>
   字节码如下(截取了部分关键代码，添加了一些注释)：
   ```
   {
   public Hello();                        //默认生成的构造器
   descriptor: ()V
   flags: (0x0001) ACC_PUBLIC
   Code:
   stack=1, locals=1, args_size=1
   0: aload_0                             // this入栈
   1: invokespecial #1                    // Method java/lang/Object."<init>":()V
   4: return
   LocalVariableTable:                    // 本地变量表，start: 哪行起作用 length: 变量作用的范围
   Start  Length  Slot  Name   Signature
   0       5     0  this   LHello;        // 非静态方法，默认第一个参数是this,槽位是0
   
   public static void main(java.lang.String[]); //main方法
   descriptor: ([Ljava/lang/String;)V
   flags: (0x0009) ACC_PUBLIC, ACC_STATIC
   Code:
   stack=4, locals=12, args_size=1
   0: bipush        10                 // 10压入栈顶
   2: istore_1                         // 存放在本地变量表槽位1,a = 10
   3: iconst_5                         // 5压入栈顶
   4: istore_2                         // 存放在本地变量表槽位2,b = 5
   5: ldc2_w        #2                 // double 2.0d,从常量池加载2
   8: dstore_3                         // 存放在本地变量表槽位3,c = 2  
   9: iload_1                          // 取本地变量表槽位1位置变量入栈，也就是10
   10: iload_2                         // 取本地变量表槽位2位置变量入栈，也就是5
   11: iadd                            // 取出操作数栈两个变量也就是10和5做加法
   12: istore        5                 // 计算结果放在本地变量槽位5的位置
   14: iload_1                         // 和上面同理做加法操作
   15: iload_2
   16: isub
   17: istore        6
   19: iload_1                         // 和上面同理做乘法操作
   20: iload_2
   21: imul
   22: istore        7
   24: iload_1
   25: i2d                             // int转double
   26: dload_3
   27: ddiv          
   28: dstore        8
   30: iconst_5                        // 将5入栈
   31: istore        10                // 栈顶元素也就是5出栈，存放在本地变量表槽位10，count = 5
   33: iconst_0                        // i = 0 
   34: istore        11    
   36: iload         11                // i入栈
   38: iconst_5                        // 5入栈
   39: if_icmpge     61                // 如果 i>=5, 跳转到61，也就是return
   42: iinc          10, 1             // 比较特殊的操作，直接在本地表里表中进行加法，每次加1，count++操作
   45: iload         10                // count入栈
   47: bipush        8                 // 8入栈
   49: if_icmpne     55                // 如果 count != 8，跳转到55
   52: goto          61                // 和上面结合起来，count == 8,到61，return
   55: iinc          11, 1             // i++的操作
   58: goto          36                // 跳转到36，继续循环
   61: return
   LocalVariableTable:
   Start  Length  Slot  Name   Signature
   36      25    11     i   I
   0      62     0  args   [Ljava/lang/String;
   3      59     1     a   I
   5      57     2     b   I
   9      53     3     c   D
   14      48     5   sum   I
   19      43     6   sub   I
   24      38     7 multiply   I
   30      32     8   div   D
   33      29    10 count   I
   ```
#### 2. 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法， 此文件内容是一个 Hello.class 文件所有字节(x=255-x)处理后的文件。
[文件链接](./src/MyClassLoader.java)
#### 3. 画一张图，展示 Xmx、Xms、Xmn、Metaspache、DirectMemory、Xss 这些内存参数的关系。
![内存参数关系](./resources/内存参数关系.png)
#### 4. 检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。
   <br>线上业务系统无法使用命令查看，通过监控系统获取了部分和用命令查询得到结果类似的内容
   <br>首先是JVM启动参数
   ```
   -Dlog4j.configuration=log4j.properties;
   -Dspring.profiles.active=prod;
   -XX:MetaspaceSize=256M;          //元数据区
   -XX:MaxMetaspaceSize=256M;       //元数据最大值
   -Xmx4g;                          //最大堆内存
   -Xms4g;                          //初始堆内存
   -Xss256k;                        //最大栈
   -XX:-OmitStackTraceInFastThrow;
   -XX:+UseG1GC;                    //G1
   -XX:ParallelGCThreads=8;         //并行GC线程数
   -XX:+AlwaysPreTouch;
   -XX:-UseBiasedLocking;
   -XX:MaxGCPauseMillis=80;         //最大暂停时间
   -XX:+HeapDumpOnOutOfMemoryError;  
   -Xloggc:/var/log/xxx/gc.log;  
   -XX:+PrintGCApplicationConcurrentTime;
   -XX:+PrintGCApplicationStoppedTime;
   -XX:+PrintGCDetails;             //GC日志详情
   -XX:+PrintGCDateStamps;
   -XX:+UseGCLogFileRotation;
   -XX:NumberOfGCLogFiles=10;
   -XX:GCLogFileSize=100m;
   -Xverify:none;
   ```
   
   查看监控系统得到以下一些信息(1天的数据来分析得出的)：
   
   - YoungGC平均每10分钟15次左右，平均时间15ms左右（10分钟总时间/10分钟总次数）
   - 老年代使用内存在1.5G左右
   - Eden内存使用上，看起来不会超过2G，基本都在1.5G以上，自适应调整的结果（UseAdaptiveSizePolicy)
   
   **我的分析：**
   
   首先，G1垃圾收集器使用建议就是有一个比较大的内存，4G我认为是和G1垃圾收集器不匹配的。其次，G1垃圾收集器有两个参数可以控制新生代大小，
   下限：-XX:G1NewSizePercent，默认值5%，上限：-XX:G1MaxNewSizePercent，默认值60%，从上面数据可以看出来，开启了自适应调整，新生代使用
   也经常接近上限60%，而老年代长期在1.5G以上。**我认为可以适当增加堆内存，可以减少Young GC的频率**。
   
### 第二次课程作业
####1. 本机使用 G1 GC 启动一个程序，仿照课上案例分析一下 JVM 情况
   启动参数
   ```
   java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseSerialGC -jar gateway-server- 0.0.1-SNAPSHOT.jar
   ```
   使用`jps -mlv`查看pid和启动参数
   ```
   28240 gateway-server-0.0.1-SNAPSHOT.jar -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseG1GC -XX:MaxGCPauseMillis=50
   ```
使用 `wrk  -d60s -t10 http://localhost:8088/api/hello` 进行压测，并使用 `jstat -gcutil pid 1000 100` 每秒抽样一次，一共100，查看堆的使用情况，截取部分结果如下：
```
  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT
  0.00 100.00  62.97   0.00  95.42  92.90      3    0.041     0    0.000    0.041
  0.00 100.00  62.97   0.00  95.42  92.90      3    0.041     0    0.000    0.041
  0.00 100.00  62.97   0.00  95.42  92.90      3    0.041     0    0.000    0.041
  0.00 100.00   6.09   0.00  95.02  92.35      4    0.079     0    0.000    0.079
  0.00 100.00  19.04   0.00  95.02  92.35      4    0.079     0    0.000    0.079
  0.00 100.00  33.50   0.00  95.02  92.35      4    0.079     0    0.000    0.079
  0.00 100.00  57.87   0.00  95.02  92.35      4    0.079     0    0.000    0.079
  0.00 100.00  94.92   0.00  95.02  92.35      5    0.079     0    0.000    0.079
  0.00 100.00  44.58   0.00  94.59  92.07      5    0.106     0    0.000    0.106
  0.00 100.00  91.04   0.00  94.59  92.07      5    0.106     0    0.000    0.106
  0.00 100.00  32.79   0.00  94.97  92.13      6    0.140     0    0.000    0.140
  0.00 100.00  65.08   0.00  94.97  92.13      6    0.140     0    0.000    0.140
  0.00 100.00  95.08   0.00  94.97  92.13      7    0.140     0    0.000    0.140
  0.00 100.00  37.06   0.00  94.40  92.13      7    0.179     0    0.000    0.179
  0.00 100.00  75.56   0.00  94.40  92.13      7    0.179     0    0.000    0.179
  0.00 100.00  19.49   0.00  94.42  92.13      8    0.238     0    0.000    0.238
  0.00 100.00  60.06   0.00  94.42  92.13      8    0.238     0    0.000    0.238
  0.00 100.00   4.63   0.00  94.42  92.13      9    0.284     0    0.000    0.284
  0.00 100.00  46.25   0.00  94.42  92.13      9    0.284     0    0.000    0.284
  0.00 100.00  83.89   0.00  94.42  92.13      9    0.284     0    0.000    0.284
  0.00 100.00  32.91   0.00  94.44  92.13     10    0.318     0    0.000    0.318
  0.00 100.00  79.71   0.00  94.44  92.13     10    0.318     0    0.000    0.318
  0.00 100.00  31.04   0.00  94.44  92.14     11    0.351     0    0.000    0.351
  0.00 100.00  77.92   0.00  94.44  92.14     11    0.351     0    0.000    0.351
  0.00 100.00  29.55   0.00  94.44  92.14     12    0.395     0    0.000    0.395
  0.00 100.00  75.24   0.00  94.44  92.14     12    0.395     0    0.000    0.395
  0.00 100.00  25.92   0.00  94.44  92.14     13    0.423     0    0.000    0.423
  0.00 100.00  74.24   0.00  94.44  92.14     13    0.423     0    0.000    0.423
  0.00 100.00  28.91   0.00  94.44  92.14     14    0.454     0    0.000    0.454
  0.00 100.00  78.91   0.00  94.44  92.14     14    0.454     0    0.000    0.454
  0.00 100.00  28.12   0.00  94.44  92.14     15    0.492     0    0.000    0.492
  0.00 100.00  78.27   0.00  94.44  92.14     15    0.492     0    0.000    0.492
  0.00 100.00  31.53   0.52  94.44  92.14     16    0.529     0    0.000    0.529
  0.00 100.00  78.34   0.52  94.44  92.14     16    0.529     0    0.000    0.529
  0.00 100.00  19.59   0.82  94.48  92.14     17    0.581     0    0.000    0.581
```
- S0和S1是两个survivor区使用百分比，可以看到S0始终是空的，符合G1的特征
- E表示Eden的使用百分比
- O表示老年代的使用百分比，可以看到在young gc发生15次后，老年代的使用百分比才从0开始增长（默认的提升存活次数就是15，可通过MaxTenuringThreshold配置）
- M表示MetaSpace的使用百分比
- CSS表示CompressedClassSpace的使用百分比
- YGC表示young gc的次数，在压测过程中，不断的再发生young gc
- YGCT表示young gc花费的时间
- FGC表示full gc的次数
- FGCT表示full gc花费的时间
- GCT表示gc的时间
使用`jmap -heap pid`查看内存信息
```
Garbage-First (G1) GC with 4 thread(s)

Heap Configuration:
   MinHeapFreeRatio         = 40
   MaxHeapFreeRatio         = 70
   MaxHeapSize              = 1073741824 (1024.0MB)
   NewSize                  = 1363144 (1.2999954223632812MB)
   MaxNewSize               = 643825664 (614.0MB)
   OldSize                  = 5452592 (5.1999969482421875MB)
   NewRatio                 = 2
   SurvivorRatio            = 6
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 1048576 (1.0MB)

Heap Usage:
G1 Heap:
   regions  = 1024
   capacity = 1073741824 (1024.0MB)
   used     = 191213224 (182.35514068603516MB)
   free     = 882528600 (841.6448593139648MB)
   17.80811920762062% used
G1 Young Generation:
Eden Space:
   regions  = 158
   capacity = 675282944 (644.0MB)
   used     = 165675008 (158.0MB)
   free     = 509607936 (486.0MB)
   24.53416149068323% used
Survivor Space:
   regions  = 1
   capacity = 1048576 (1.0MB)
   used     = 1048576 (1.0MB)
   free     = 0 (0.0MB)
   100.0% used
G1 Old Generation:
   regions  = 25
   capacity = 397410304 (379.0MB)
   used     = 24489640 (23.355140686035156MB)
   free     = 372920664 (355.64485931396484MB)
   6.162306249613498% used
```
- 整体来看，G1的内存有Eden Space和Survivor Space，但是并不像其他分代垃圾收集器把Survivor Space分为From和To两部分
- MinHeapFreeRatio、MaxHeapFreeRatio:最小和最大空闲堆空间比，如果当前空闲堆比小于MinHeapFreeRatio，要进行扩容，大于缩容。
- MaxHeapSize:最大堆内存，在启动参数Xmx的时候进行了设置为1G，如果不设置**默认为本机内存的1/4**
- NewSize:新生代大小
- MaxNewSize: 新生代最大内存空间，-XX:G1MaxNewSizePercent默认为60，所以`MaxNewSize = 0.6*MaxHeapSize`，**但实际测试下来E+S的最大值，比MaxNewSize可以多出5%**
- OldSize:老年代大小
- NewRatio:新生代与老年的堆空间比值，等于2表示新生代:老年代=1:2，新生代占老年代的1/3，G1默认不由该参数决定。
- SurvivorRatio：Eden区的堆空间和两个Survivor区比值，默认是8，表示Eden:S0:S1=8:1:1。**实际测试以及查找jvm相关源码发现，
  在不主动配置SurvivorRatio的时候，虽然默认值是8，实际是按照6:1:1来计算的，在主动配置了SurvivorRatio后才会按照8:1:1计算**。并且UseAdaptiveSizePolicy会使该参数不生效，G1下改参数也不会生效。
- MetaspaceSize:元空间大小
- CompressedClassSpaceSize：Java8在UseCompressedOops之外，额外增加了一个新选项叫做UseCompressedClassPointer，这个选项打开后，class信息中的指针也用32bit的Compressed版本，而这些指针指向的空间被称作“Compressed Class Space”，默认大小是1G。
- MaxMetaspaceSize:最大元空间大小，默认是不设置上限的,所以是0xFFFFFFFFFFFFFFFF/1024/1024 = 17592186044415M
- G1HeapRegionSize:G1是基于Region来做对象管理的，该参数用来指定每个Region空间的大小,取值范围从1M到32M，且是2的指数。一共2048个region,如果不设置默认是：堆大小/2048，1M是最小值。
- Heap Usage：内存的使用情况，G1中regions表示当前使用的region的个数
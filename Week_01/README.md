## 训练营第01周作业
1. 自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和
   for，然后自己分析一下对应的字节码。
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
2. 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法， 此文件内容是一个 Hello.class 文件所有字节(x=255-x)处理后的文件。
   
    [文件链接](./src/MyClassLoader.java)
3. 画一张图，展示 Xmx、Xms、Xmn、Metaspache、DirectMemory、Xss 这些内存参数的关系。
   ![内存参数关系](./resources/内存参数关系.png)
4. 检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。
   TODO
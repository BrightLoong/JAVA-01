/**
 * @author BrightLoong
 * @date 2021/1/11 22:06
 * @description 查看字节码用类
 */
public class Hello {
    public static void main(String[] args) {
        int a = 10;
        int b = 5;
        double c = 2;
        int sum = a + b;
        int sub = a - b;
        int multiply = a * b;
        double div = a / c;
        int count = 5;
        for (int i = 0; i < 5; i++) {
            count++;
            if (count == 8) {
                break;
            }
        }
    }
}

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author BrightLoong
 * @date 2021/1/7 20:17
 * @description
 */
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> clz = new MyClassLoader().findClass("Hello");
            clz.getMethod("hello").invoke(clz.newInstance());
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = null;
        try (InputStream inputStream = getClass().getResourceAsStream(name + ".xlass")) {
            bytes = inputStream2byte(inputStream);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(bytes)) {
            throw new ClassNotFoundException("can not find class:" + name);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    /**
     * inputStream转byte数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] inputStream2byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
}

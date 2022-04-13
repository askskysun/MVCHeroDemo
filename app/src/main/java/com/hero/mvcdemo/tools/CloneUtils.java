package com.hero.mvcdemo.tools;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 对象clone工具类
 */
public class CloneUtils {
    private CloneUtils() {
        throw new AssertionError();
    }

    /**
     * 深度克隆
     *
     * @param srcObj 需要克隆的对象
     * @param <T>
     * @return
     */
    public static <T extends Serializable> T clone(T srcObj) {
        try {
            //创建一块内存来存放对象内容
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bout);
            //将对象转换成二进制内容存入到开辟的内存中(序列化)
            oos.writeObject(srcObj);

            //读取内存块中的二进制内容
            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            //将二进制内容转换回对象 反序列化
            ObjectInputStream ois = new ObjectInputStream(bin);
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
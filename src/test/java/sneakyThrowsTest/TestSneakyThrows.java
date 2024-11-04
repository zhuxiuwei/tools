package sneakyThrowsTest;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 231008
 * 官方文档： https://projectlombok.org/features/SneakyThrows
 */
public class TestSneakyThrows {
    public static void main(String[] args) {
        FileInputStream fileInputStream = ServiceProvider.readFile("/Users/zhuxiuwei/Downloads/NON_EXIST");
    }
    /*
     * 输出：Exception in thread "main" java.io.FileNotFoundException: /Users/zhuxiuwei/Downloads/需要屏蔽的图2书.txt (No such file or directory)
        at java.io.FileInputStream.open0(Native Method)
        at java.io.FileInputStream.open(FileInputStream.java:195)
        at java.io.FileInputStream.<init>(FileInputStream.java:138)
        at sneakyThrowsTest.ServiceProvider.readFile(TestSneakyThrows.java:18)
        at sneakyThrowsTest.TestSneakyThrows.main(TestSneakyThrows.java:11)
     */
}

class ServiceProvider{
    //加上@SneakyThrows注解，则readFile方法不需要加上throws声明来继续向上抛，或者try-catch自己吞掉。
    @SneakyThrows({FileNotFoundException.class})
    public static FileInputStream readFile(String path){
        FileInputStream fis = new FileInputStream(new File(path));
        return fis;
    }
}
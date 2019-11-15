package cn.liubinbin.ai.index;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public class ProgressBarTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Loading...");
        for (int i = 0; i < 1000; i++) {
            System.out.printf("%c进度: %d...", 13, i);
            Thread.sleep(1);
        }
    }
}

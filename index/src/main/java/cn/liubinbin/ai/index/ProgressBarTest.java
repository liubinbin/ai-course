package cn.liubinbin.ai.index;

import java.io.IOException;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public class ProgressBarTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        for (int i = 0; i< 100; i++) {
            System.err.printf("hello world " + i);
            System.err.flush();
            Thread.sleep(1000);
        }

    }
}

package cn.liubinbin.ai.diynn.usage.statistics;

import cn.liubinbin.ai.diynn.fcnn.FCNeuralNetwork;

import java.util.Arrays;

/**
 * Created by bin on 2019/12/23.
 *
 * @Description: TODO
 */
public class GermanCreditData {

    private static final Integer trainSize = 800;
    private static final Integer testSize = 200;
    private static final Integer totalSize = 1000;

    public static void main(String[] args) throws Exception {

        String dataPath = "C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data.onehot";

        FCNeuralNetwork diyNeuralNetwork = new FCNeuralNetwork(69, 2, 1, 30);

        double[][] attrs = GermanCreditDataLoader.getAttr(dataPath, totalSize);
        double[][] labels = GermanCreditDataLoader.getLabels(dataPath, totalSize);

        System.out.println(labels.length);

        int round = 1;
        while (round < Integer.MAX_VALUE) {
            System.out.println("\nstart round: " + (round++));
            System.out.println("start to train");
            long time1 = System.currentTimeMillis();
            for (int i = 0; i < trainSize; i++) {
                System.out.printf("%c已训练: %d, 训练总量: %d", 13, i, trainSize);
                diyNeuralNetwork.train(attrs[i], labels[i]);
            }
            System.out.println("\ntraining uses time: " + (System.currentTimeMillis() - time1));

            System.out.println("start to predict");
            long time2 = System.currentTimeMillis();
            int rightCount = 0;
            for (int j = trainSize; j < testSize + trainSize; j++) {
                System.out.printf("%c已预测: %d, 预测准确: %d, 预测总量: %d", 13, j - trainSize, rightCount, testSize);
                System.out.println(Arrays.toString(diyNeuralNetwork.predict(attrs[j])));
                int predictNum = diyNeuralNetwork.predicToInt(attrs[j]);
                int trueNum = diyNeuralNetwork.pickBiggesItemIdx(labels[j]);
//                System.out.println(" predictNum " + predictNum + " trueNum " + trueNum);
                if (predictNum == trueNum) {
                    rightCount++;
                }
            }
            System.out.println("\n test uses time: " + (System.currentTimeMillis() - time2));
            System.out.println("MnistNN right rate: " + rightCount + " / " + testSize);
            Thread.sleep(1000);
//            diyNeuralNetwork.printOutputLayerWeight();
//            System.out.println("--weight line---");
//            diyNeuralNetwork.printHiddenLayerWeight();
        }

    }
}

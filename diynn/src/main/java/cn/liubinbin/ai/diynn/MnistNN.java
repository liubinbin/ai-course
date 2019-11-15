package cn.liubinbin.ai.diynn;

import java.util.Arrays;

/**
 * Created by bin on 2019/11/15.
 * data source url: http://yann.lecun.com/exdb/mnist/
 *
 * @Description: TODO
 */
public class MnistNN {

    private static int trainCount = 60000;
    private static int testCount = 10000;
    private static String ministRootPath = "/Users/liubinbin/Desktop/MNIST/";

    public static void main(String[] args) throws Exception {
        String trainLabelFile = ministRootPath + "train-labels-idx1-ubyte";
        String trainImageFile = ministRootPath + "train-images-idx3-ubyte";
        String testLabelFile = ministRootPath + "t10k-labels-idx1-ubyte";
        String testImageFile = ministRootPath + "t10k-images-idx3-ubyte";

        DIYNeuralNetwork diyNeuralNetwork = new DIYNeuralNetwork(28 * 28, 10);

        System.out.println("start to get train labels");
        double[][] trainLabels = MnistDataLoader.getLabels(trainLabelFile, trainCount);

        System.out.println("start to get train images");
        double[][] trainImages = MnistDataLoader.getImages(trainImageFile, trainCount);

        System.out.println("start to get test labels");
        double[][] testLabels = MnistDataLoader.getLabels(testLabelFile, testCount);

        System.out.println("start to get test images");
        double[][] testImages = MnistDataLoader.getImages(testImageFile, testCount);

        int round = 1;
        while (round < 100) {
            System.out.println("start round: " + (round++));
            System.out.println("start to train");
            for (int i = 0; i < trainLabels.length; i++) {
                System.out.printf("%c已训练: %d, 训练总量: %d", 13, i, trainLabels.length);
                diyNeuralNetwork.train(trainImages[i], trainLabels[i]);
            }

            System.out.println("\nstart to predict");
            int rightCount = 0;
            for (int j = 0; j < testLabels.length; j++) {
                System.out.printf("%c已预测: %d, 预测总量: %d", 13, j, testLabels.length);
                int predictNum = diyNeuralNetwork.predicToInt(testImages[0]);
                int trueNum = diyNeuralNetwork.pickBiggesItemIdx(testLabels[j]);
                if (predictNum == trueNum) {
                    rightCount++;
                }
            }
            System.out.println("round: " + round + " image[0]: " + Arrays.toString(diyNeuralNetwork.predict(testImages[0])));
            System.out.println("\nMnistNN right rate: " + rightCount + " / " + testLabels.length);
        }

    }

}

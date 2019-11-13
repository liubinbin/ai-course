package cn.liubinbin.ai.diynn;

import java.util.Arrays;

/**
 * Created by bin on 2019/11/12.
 *
 * @Description: TODO
 */
public class DIYNeuralNetwork {

    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;
    private int hiddenLayerNodeSize = 3;

    public DIYNeuralNetwork(int inputLayNodeSize, int outputLayerNodeSize) {
        this.inputLayer = new Layer(LayerType.INPUT, inputLayNodeSize, 0, null);
        this.hiddenLayer = new Layer(LayerType.HIDDEN, hiddenLayerNodeSize, inputLayer.getNodeSize(), inputLayer);
        this.outputLayer = new Layer(LayerType.OUTPUT, outputLayerNodeSize, hiddenLayer.getNodeSize(), hiddenLayer);
    }

    private double[] predict(double[] source) throws Exception {
        // set value to inputLayer;
        this.inputLayer.setValue(source);

        // cal value in hiddenLayer;
        this.hiddenLayer.calValue();

        // cal value in outputLayer;
        this.outputLayer.calValue();

        return this.outputLayer.getOutput();
    }

    private void train(double[] source, double[] result) throws Exception {
//        System.out.println("one train round");
        predict(source);
        // update weight in outputLayer
        this.outputLayer.updateWeight(result, null);
//        printOutputLayerWeight();
        // update weight in hiddenLayer
        this.hiddenLayer.updateWeight(outputLayer.getError(), outputLayer.getWeights());
//        printHiddenLayerWeight();
    }

    private void printHiddenLayerWeight() {
        this.hiddenLayer.printWeights();
    }

    private void printOutputLayerWeight() {
        this.outputLayer.printWeights();
    }

    public static void main(String[] args) throws Exception {
        DIYNeuralNetwork diyNeuralNetwork = new DIYNeuralNetwork(2, 1);
        int roundCount = 1000000;
        for (int i = 0; i < roundCount; i ++) {
            System.out.println("one train round " + i);
            diyNeuralNetwork.train(new double[]{1, 1}, new double[]{1});
            diyNeuralNetwork.train(new double[]{1, 0}, new double[]{0.5});
            diyNeuralNetwork.train(new double[]{0, 1}, new double[]{0.5});
            diyNeuralNetwork.train(new double[]{0, 0}, new double[]{0});
            double[] result = diyNeuralNetwork.predict(new double[]{1, 1});
            System.out.println("1 1 -> result: " + Arrays.toString(result));

            result = diyNeuralNetwork.predict(new double[]{0, 1});
            System.out.println("0 1 -> result: " + Arrays.toString(result));

            result = diyNeuralNetwork.predict(new double[]{1, 0});
            System.out.println("0 1 -> result: " + Arrays.toString(result));

            result = diyNeuralNetwork.predict(new double[]{0.4, 0.6});
            System.out.println("0 0 -> result: " + Arrays.toString(result));
        }
    }
}

package cn.liubinbin.ai.diynn.fcnn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bin on 2019/11/12.
 * 全连接神经网络
 * @Description: TODO
 */
public class FCNeuralNetwork {

    private Layer inputLayer;
    private List<Layer> hiddenLayers;
    private Layer outputLayer;
    private int hiddenLayerSize = 1;
    private int hiddenLayerNodeSize = 40;

    public FCNeuralNetwork(int inputLayNodeSize, int outputLayerNodeSize, int hiddenLayerSize, int hiddenLayerNodeSize) {
        this.hiddenLayerSize = hiddenLayerSize;
        this.hiddenLayerNodeSize = hiddenLayerNodeSize;
        this.inputLayer = new Layer(LayerType.INPUT, inputLayNodeSize, 0, null);
        this.hiddenLayers = new ArrayList<Layer>(hiddenLayerSize);
        this.hiddenLayers.add(new Layer(LayerType.HIDDEN, hiddenLayerNodeSize, inputLayer.getNodeSize(), inputLayer));
        for (int i = 1; i < hiddenLayerSize; i++) {
            this.hiddenLayers.add(new Layer(LayerType.HIDDEN, hiddenLayerNodeSize, hiddenLayers.get(i - 1).getNodeSize(), hiddenLayers.get(i - 1)));
        }
        this.outputLayer = new Layer(LayerType.OUTPUT, outputLayerNodeSize, this.hiddenLayers.get(hiddenLayerSize - 1).getNodeSize(), this.hiddenLayers.get(hiddenLayerSize - 1));
    }

    public static void main(String[] args) throws Exception {
        FCNeuralNetwork diyNeuralNetwork = new FCNeuralNetwork(2, 1, 1, 20);
        int roundCount = 1000000;
        for (int i = 0; i < roundCount; i++) {
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

    public int pickBiggesItemIdx(double[] result) throws Exception {
        int predictInt = 1;
        double temp = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] > temp) {
                predictInt = i;
                temp = result[i];
            }
        }
        predictInt++;
        return predictInt;
    }

    public int predicToInt(double[] source) throws Exception {
        double[] result = predict(source);
        return pickBiggesItemIdx(result);
    }

    public double[] predict(double[] source) throws Exception {
        // set value to inputLayer;
        this.inputLayer.setValue(source);

        // cal value in hiddenLayer;
        for (int i = 0; i < hiddenLayerSize; i++) {
            this.hiddenLayers.get(i).calValue();
        }

        // cal value in outputLayer;
        this.outputLayer.calValue();

        return this.outputLayer.getOutput();
    }

    public void train(double[] source, double[] result) throws Exception {
        predict(source);
        // update weight in outputLayer
        this.outputLayer.updateWeight(result, null);
        // update weight in hiddenLayer
        this.hiddenLayers.get(hiddenLayerSize - 1).updateWeight(outputLayer.getError(), outputLayer.getWeights());
        for (int i = hiddenLayerSize - 2; i >= 0; i--) {
            this.hiddenLayers.get(i).updateWeight(hiddenLayers.get(i + 1).getError(), hiddenLayers.get(i + 1).getWeights());
        }
    }

    public void printHiddenLayerWeight() {
        for (int i = 0; i < hiddenLayerSize; i++) {
            this.hiddenLayers.get(i).printWeights();
        }
    }

    public void printOutputLayerWeight() {
        this.outputLayer.printWeights();
    }
}

package cn.liubinbin;

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

    public DIYNeuralNetwork(int inputLayNodeSize, int outputLayerNodeSize){
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

    private void train(double[] source, double[] result) {
        // update weight in outputLayer
        this.outputLayer.updateWeight(result, null);
        // update weight in hiddenLayer
        this.hiddenLayer.updateWeight(outputLayer.getError(), outputLayer.getWeights());
    }

    private void printHiddenLayerWeight() {
        this.hiddenLayer.printWeights();
    }

    private void printOutputLayerWeight(){
        this.outputLayer.printWeights();
    }

    public static void main(String[] args) throws Exception {
        DIYNeuralNetwork diyNeuralNetwork = new DIYNeuralNetwork(2,1);
        diyNeuralNetwork.train(new double[]{1, 1}, new double[]{2});
        diyNeuralNetwork.train(new double[]{1, 0}, new double[]{1});
        diyNeuralNetwork.train(new double[]{0, 1}, new double[]{1});
        diyNeuralNetwork.train(new double[]{0, 0}, new double[]{0});

        double[] result = diyNeuralNetwork.predict(new double[]{1, 1});
        System.out.println("result: " + Arrays.toString(result));
    }
}

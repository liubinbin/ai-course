package cn.liubinbin.ai.diynn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bin on 2019/11/13.
 *
 * @Description: TODO
 */
public class Layer {

    private static final double learnRate = 0.5;
    private static final double initWeight = 0;
    private static final Double ONE = 1d;
    private LayerType layerType;
    private List<Node> nodes = new ArrayList<Node>();
    private Layer preLayer;
    /**
     * 用于计算本层里节点，里面的值为上一层的值在本层节点计算时的权重。
     * 第一个长度为本层节点个数，第二长度为上一层的节点个数 + 1
     */
    private double[][] weights;
    private int nodeSize;
    private int preLayerSize;
    private double[] error;

    public Layer(LayerType layerType, int nodeSize, int preLayerSize, Layer preLayer) {
        this.layerType = layerType;
        this.nodeSize = nodeSize;
        for (int idx = 0; idx < nodeSize; idx++) {
            this.nodes.add(new Node());
        }
        if (layerType != LayerType.INPUT) {
            this.preLayerSize = preLayerSize;
            // 会有一个 1 多余
            this.weights = new double[nodeSize][preLayerSize + 1];
            for (int x = 0; x < nodeSize; x++) {
                for (int y = 0; y < preLayerSize + 1; y++) {
                    this.weights[x][y] = initWeight;
                }
            }
            this.preLayer = preLayer;
            this.error = new double[nodeSize];
        }
    }

    public void printWeights() {
        System.out.println("weight is following");
        for (int x = 0; x < nodeSize; x++) {
            for (int y = 0; y < preLayerSize + 1; y++) {
                System.out.print(this.weights[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println("weight is done");
        return;
    }

    public Layer getPreLayer() {
        return preLayer;
    }

    public void setPreLayer(Layer preLayer) {
        this.preLayer = preLayer;
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(int nodeSize) {
        this.nodeSize = nodeSize;
    }

    private double getNodeValue(int idx) {
        return nodes.get(idx).getValue();
    }

    public double[] getOutput() {
        if (this.layerType.equals(LayerType.OUTPUT)) {
            double[] result = new double[nodes.size()];
            int idx;
            int size = nodes.size();
            for (idx = 0; idx < size; idx++) {
                result[idx] = nodes.get(idx).getValue();
            }
            return result;
        } else {
            double[] result = new double[nodes.size() + 1];
            int idx;
            int size = nodes.size();
            for (idx = 0; idx < size; idx++) {
                result[idx] = nodes.get(idx).getValue();
            }
            result[idx] = 1d;
            return result;
        }
    }

    public void calValue() {
        double[] preLayerValue = preLayer.getOutput();
        for (int idx = 0; idx < nodeSize; idx++) {
            this.nodes.get(idx).updateValue(weights[idx], preLayerValue);
        }
    }

    public void setValue(double[] source) throws Exception {
        if (source.length != this.nodes.size()) {
            throw new Exception("size does not match");
        }
        for (int idx = 0; idx < source.length; idx++) {
            this.nodes.get(idx).setValue(source[idx]);
        }
    }


    /***
     *  计算误差
     * @param result 输出层的真实结果（非预测结果），也可能是下一层的误差。
     */
    public void calError(double[] result, double[][] nextLayerWeight) {
        if (this.layerType.equals(LayerType.OUTPUT)) {
            for (int x = 0; x < nodeSize; x++) {
                double value = getNodeValue(x);
                this.error[x] = value * (1 - value) * (result[x] - value);
            }
        } else if (this.layerType.equals(LayerType.HIDDEN)) {
            for (int x = 0; x < nodeSize; x++) {
                double value = getNodeValue(x);
                double temp = 0;
                for (int y = 0; y < result.length; y++) {
                    temp += result[y] * nextLayerWeight[y][x];
                }
                this.error[x] = value * (1 - value) * (temp);
            }
        }
    }

    public double[][] getWeights() {
        return weights;
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }

    public double[] getError() {
        return error;
    }

    public void setError(double[] error) {
        this.error = error;
    }

    public void updateWeight(double[] result, double[][] nextLayerWeight) {
        double[] preLayerNodeValue = this.preLayer.getOutput();
        // 计算误差
        calError(result, nextLayerWeight);
        for (int x = 0; x < nodeSize; x++) {
            for (int y = 0; y < preLayerSize + 1; y++) {
                this.weights[x][y] += learnRate * this.error[x] * preLayerNodeValue[y];
            }
        }
    }
}

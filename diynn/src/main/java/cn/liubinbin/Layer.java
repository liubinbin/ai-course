package cn.liubinbin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bin on 2019/11/13.
 *
 * @Description: TODO
 */
public class Layer {

    private static final double initWeight = 0.5;
    private static final Double ONE = 1d;
    private LayerType layerType;
    private List<Node> nodes = new ArrayList<Node>();
    private Layer preLayer;
    private double[][] weights;
    private int nodeSize;
    private int preLayerSize;

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
        }
    }

    public void printWeights(){
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

    public double[] getOutput() {
        if (this.layerType.equals(LayerType.OUTPUT)){
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
        for ( int idx = 0; idx < source.length; idx++ ) {
            this.nodes.get(idx).setValue(source[idx]);
        }
    }
}

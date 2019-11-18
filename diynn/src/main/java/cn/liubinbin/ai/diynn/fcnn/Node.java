package cn.liubinbin.ai.diynn.fcnn;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * Created by bin on 2019/11/13.
 *
 * @Description: TODO
 */
public class Node {

    private double value;

    public Node() {
        this.value = 0;
    }

    public static void main(String[] args) {
        Node node = new Node();
        double[] weights = new double[]{1.0, 2.0, 3.0};
        double[] value = new double[]{1.0, 2.0, 3.0};
        node.updateValue(weights, value);
        System.out.println(node.getValue());
    }

    public double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E, -1 * x));
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void updateValue(double[] weights, double[] preLayerValue) {
        assert weights.length != preLayerValue.length;
        RealVector value1 = new ArrayRealVector(weights);
        RealVector value2 = new ArrayRealVector(preLayerValue);
        this.value = sigmoid(value1.dotProduct(value2));
    }
}

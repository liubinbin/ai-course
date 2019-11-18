package cn.liubinbin.ai.diynn;

/**
 * Created by bin on 2019/11/18.
 *
 * @Description: TODO
 */
public class TrainItem {

    // 第一个参数
    private double x1;
    // 第二个参数
    private double x2;
    // 正集合
    private double y1;
    // 负集合
    private double y2;

    public TrainItem(double x1, double x2, double y1, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public double[] getX() {
        return new double[]{x1, x2};
    }

    public double[] getY() {
        return new double[]{y1, y2};
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    @Override
    public String toString() {
        return "TrainItem: { x1: " + x1 + " x2: " + x2 + " y1: " + y1 + " y2: " + y2 + "}";
    }
}

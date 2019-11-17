package cn.liubinbin.ai.perceptron;


import java.util.ArrayList;
import java.util.List;

/**
 * 数据
 * <p>
 * 正实例点是[10,8],[6,9],[6,8],[7,6],[7,8],[9,6],[11,3],[10,6],[12,5]
 * 负实例点是[1,2],[2,2],[3,1],[1,1],[3,6],[4,4],[3,2],[2,6],[6,2]
 */
public class Perceptron {

    private final double learnRate = 0.01;
    private double w1, w2, b;
    private List<TrainItem> trainData;

    public Perceptron() {
        this.w1 = 1;
        this.w2 = 1;
        this.b = 0;
        this.initTrainData();
    }

    private void initTrainData(){
        this.trainData = new ArrayList<TrainItem>();
        this.trainData.add(new TrainItem(10,8, 1));
        this.trainData.add(new TrainItem(6,9, 1));
        this.trainData.add(new TrainItem(6,8, 1));
        this.trainData.add(new TrainItem(7,6, 1));
        this.trainData.add(new TrainItem(7,8, 1));
        this.trainData.add(new TrainItem(9,6, 1));
        this.trainData.add(new TrainItem(11,3, 1));
        this.trainData.add(new TrainItem(10,6, 1));
        this.trainData.add(new TrainItem(12,5, 1));
        this.trainData.add(new TrainItem(1,2, -1));
        this.trainData.add(new TrainItem(2,2, -1));
        this.trainData.add(new TrainItem(3,1, -1));
        this.trainData.add(new TrainItem(1,1, -1));
        this.trainData.add(new TrainItem(3,6, -1));
        this.trainData.add(new TrainItem(4,4, -1));
        this.trainData.add(new TrainItem(3,2, -1));
        this.trainData.add(new TrainItem(2,6, -1));
        this.trainData.add(new TrainItem(6,2, -1));
    }

    public static void main(String[] args) {
        Perceptron perceptron = new Perceptron();
        perceptron.printWelCome();
        perceptron.train();
        perceptron.printPerceptron();
    }

    public void updatePara(int x1, int x2, int y) {
        w1 += learnRate * y * x1;
        w2 += learnRate * y * x2;
        b += learnRate * y;
    }

    public int sign(double x) {
        if (x < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public void printPerceptron() {
        System.out.println("w1: " + w1 + " w2: " + w2 + " b: " + b);
    }

    public boolean judgeIfStop() {
        return false;
    }

    public double function(int x1, int x2){
        return x1 * w1 + x2 * w2 + b;
    }

    private void printTrainResult() {
        System.out.println("--- result --- ");
        for (TrainItem trainItem : trainData) {
            System.out.println("x1: " + trainItem.getX1() + " x2: " + trainItem.getX2() + " y: " + trainItem.getY() + " fun: " + function(trainItem.getX1(), trainItem.getX2()));
        }
    }

    public void train() {
        boolean stopFlag = false;
        while (!stopFlag) {
            stopFlag = true;
            for (TrainItem trainItem : trainData) {
                if (trainItem.getY() * function(trainItem.getX1(), trainItem.getX2()) <= 0) {
                    updatePara(trainItem.getX1(), trainItem.getX2(), trainItem.getY());
                    stopFlag = false;
                }
            }

            printPerceptron();
            printTrainResult();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printWelCome() {
        System.out.println("Hello Perceptron");
    }
}

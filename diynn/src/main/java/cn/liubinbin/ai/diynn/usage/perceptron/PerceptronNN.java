package cn.liubinbin.ai.diynn.usage.perceptron;

import cn.liubinbin.ai.diynn.fcnn.FCNeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bin on 2019/11/15.
 * data source url: http://yann.lecun.com/exdb/mnist/
 * { hiddenLayerSize = 1 and hiddenLayerNodeSize = 4 } is good
 *
 * @Description: TODO
 */
public class PerceptronNN {

    private static int trainCount = 60000;
    private static int testCount = 10000;

//    正实例点是[10,8],[6,9],[6,8],[7,6],[7,8],[9,6],[11,3],[10,6],[12,5]
//
//    负实例点是[1,2],[2,2],[3,1],[1,1],[3,6],[4,4],[3,2],[2,6],[6,2]

    public static void main(String[] args) throws Exception {

        FCNeuralNetwork diyNeuralNetwork = new FCNeuralNetwork(2, 2, 1, 4);

        List<TrainItem> trainItems = new ArrayList<TrainItem>();
        trainItems.add(new TrainItem(10, 8, 0.99, 0.01));
        trainItems.add(new TrainItem(6, 9, 0.99, 0.01));
        trainItems.add(new TrainItem(6, 8, 0.99, 0.01));
        trainItems.add(new TrainItem(7, 6, 0.99, 0.01));
        trainItems.add(new TrainItem(7, 8, 0.99, 0.01));
        trainItems.add(new TrainItem(9, 6, 0.99, 0.01));
        trainItems.add(new TrainItem(11, 3, 0.99, 0.01));
        trainItems.add(new TrainItem(10, 6, 0.99, 0.01));
        trainItems.add(new TrainItem(12, 5, 0.99, 0.01));

        trainItems.add(new TrainItem(1, 2, 0.01, 0.99));
        trainItems.add(new TrainItem(2, 2, 0.01, 0.99));
        trainItems.add(new TrainItem(3, 1, 0.01, 0.99));
        trainItems.add(new TrainItem(1, 1, 0.01, 0.99));
        trainItems.add(new TrainItem(3, 6, 0.01, 0.99));
        trainItems.add(new TrainItem(4, 4, 0.01, 0.99));
        trainItems.add(new TrainItem(3, 2, 0.01, 0.99));
        trainItems.add(new TrainItem(2, 6, 0.01, 0.99));
        trainItems.add(new TrainItem(6, 2, 0.01, 0.99));

        int roundi = 1;
        while (roundi < 100000) {
            System.out.println("one roundi " + roundi);
            // predict
            System.out.println("predict");
            int rightCount = 0;
            for (TrainItem trainItem : trainItems) {
                double[] predictRe = diyNeuralNetwork.predict(trainItem.getX());
                int trueNum = diyNeuralNetwork.pickBiggesItemIdx(trainItem.getY());
                int predictNum = diyNeuralNetwork.pickBiggesItemIdx(predictRe);
                if (predictNum == trueNum) {
                    rightCount++;
                }
                System.out.println(Arrays.toString(predictRe) + " predictNum : " + predictNum + " trueNum: " + trueNum);
            }
            System.out.println("------ rightCount / totalCount :  " + rightCount + " / " + trainItems.size());

            //train
            for (TrainItem trainItem : trainItems) {
                diyNeuralNetwork.train(trainItem.getX(), trainItem.getY());
            }

            Thread.sleep(5);
            roundi++;
        }

    }

}

package cn.liubinbin.ai.diynn.usage.statistics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by bin on 2019/11/15.
 *
 * @Description: TODO
 */
public class GermanCreditDataLoader {

    private static int attrLen = 69;
    private static int labelLen = 2;
    private static String sep = ",";

    public static double[][] getAttr(String labelPath, int count) throws IOException {
        double[][] attrs = new double[count][attrLen];
        int attri = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(labelPath));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
//            System.out.println(line.split(sep).length);
            String[] lineSplit = line.split(sep);
//            System.out.println("lineSplit.len " + lineSplit.length);
            for (int i = 0; i < attrLen; i++) {
                attrs[attri][i] = Double.parseDouble(lineSplit[i]);
            }
            attri++;
        }
        return attrs;
    }

    public static double[][] getLabels(String labelPath, int count) throws IOException {
        double[][] labels = new double[count][labelLen];
        int labeli = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(labelPath));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] lineSplit = line.split(sep);
//            System.out.println("lineSplit.len " + lineSplit.length);
            for (int i = 0; i < labelLen; i++) {
                labels[labeli][i] = Double.parseDouble(lineSplit[attrLen + i]);
            }
            labeli++;
        }
        return labels;
    }


    public static void main(String[] args) throws IOException {
//        System.out.println("hello world");
        double[][] attrs = getAttr("C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data.onehot", 1000);
        System.out.println(Arrays.toString(attrs[0]));
        System.out.println(Arrays.toString(attrs[1]));

        double[][] labels = getLabels("C:\\Users\\viruser.v-desktop\\Desktop\\AI 作业\\german.data.onehot", 1000);
        System.out.println(Arrays.toString(labels[0]));
        System.out.println(Arrays.toString(labels[1]));
        System.out.println(Arrays.toString(labels[2]));
    }
}

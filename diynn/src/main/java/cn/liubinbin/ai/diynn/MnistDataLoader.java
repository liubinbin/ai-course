package cn.liubinbin.ai.diynn;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by bin on 2019/11/15.
 *
 * @Description: TODO
 */
public class MnistDataLoader {

//    private static int count = 10000;
    private static int weight = 28;
    private static int height = 28;
    private static int reKind = 10;

    public static void saveImage(int[] pixels, String imagePath) throws IOException {
        int width = 28;
        int height = 28;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, pixels[i * width + j]);
            }
        }
        ImageIO.write(image, "png", new File(imagePath));
    }

    public static double[][] getImages(String imagePath, int count) throws IOException {
        double images[][] = new double[count][weight * height];
        // load label
        FileInputStream is = new FileInputStream(imagePath);
        // create new data input stream
        DataInputStream dis = new DataInputStream(is);
        System.out.println("image.image number: " + dis.readInt());
        System.out.println("image.count: " + dis.readInt());
        System.out.println("image.row: " + dis.readInt());
        System.out.println("image.column: " + dis.readInt());
        int pixeli = 0;
        int imagei = 0;
//        int[] pixels = new int[weight * height];
        while (dis.available() > 0) {
            int k = dis.readByte();
//            pixels[pixeli++] = k;
            images[imagei][pixeli++] = k;
            if (pixeli >= weight * height) {
//                saveImage(pixels, "C:\\Users\\viruser.v-desktop\\Desktop\\images\\" + imagei + ".png");
                imagei++;
                pixeli = 0;
//                pixels = new int[weight * height];
            }
        }
        return images;
    }

    public static double[][] getLabels(String labelPath, int count) throws IOException {
        double[][] labels = new double[count][reKind];
        int labeli = 0;
        // load label
        FileInputStream is = new FileInputStream(labelPath);
        // create new data input stream
        DataInputStream dis = new DataInputStream(is);
        System.out.println("label.image number: " + dis.readInt());
        System.out.println("label.count: " + dis.readInt());
        // available stream to be read
        while (dis.available() > 0) {
            int k = dis.readByte();
            for (int i = 0; i < reKind; i++) {
                if (i == k) {
                    labels[labeli][i] = 0.99;
                } else {
                    labels[labeli][i] = 0.01;
                }
            }
            labeli++;
        }
        return labels;
    }


    // label 10008 = 10000 + 8 bytes
    // image 7840016 = 10000 * 28 * 28 + 16
    public static void main(String[] args) throws IOException {
        getLabels("C:\\Users\\viruser.v-desktop\\Desktop\\MNIST\\t10k-labels.idx1-ubyte", 10000);
        getImages("C:\\Users\\viruser.v-desktop\\Desktop\\MNIST\\t10k-images.idx3-ubyte", 10000);
    }
}

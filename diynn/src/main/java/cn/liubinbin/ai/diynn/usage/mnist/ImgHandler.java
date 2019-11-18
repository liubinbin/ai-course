package cn.liubinbin.ai.diynn.usage.mnist;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImgHandler {

    public static void getData(String path) {
        try {
            BufferedImage bimg = ImageIO.read(new File(path));
            int[][] data = new int[bimg.getWidth()][bimg.getHeight()];
            System.out.println(bimg.getWidth() + "|" + bimg.getHeight());
            //方式一：通过getRGB()方式获得像素矩阵
            //此方式为沿Height方向扫描
            for (int i = 0; i < bimg.getWidth(); i++) {
                for (int j = 0; j < bimg.getHeight(); j++) {
                    data[i][j] = bimg.getRGB(i, j);
                    System.out.printf("%x\t", data[i][j]);
                }
                System.out.println();
            }
//            Raster raster = bimg.getData();
//            System.out.println("");
//            int [] temp = new int[raster.getWidth()*raster.getHeight()*raster.getNumBands()];
//            //方式二：通过getPixels()方式获得像素矩阵
//            //此方式为沿Width方向扫描
//            int [] pixels  = raster.getPixels(0,0,raster.getWidth(),raster.getHeight(),temp);
//            for (int i=0;i<pixels.length;) {
//                //输出一列数据比对
//                if((i%raster.getWidth()*raster.getNumBands())==0)
//                    System.out.printf("ff%x%x%x\t",pixels[i],pixels[i+1],pixels[i+2]);
//                i+=3;
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        String imagePath = "C:\\Users\\viruser.v-desktop\\Desktop\\result.png";
        generatePic(imagePath);
        getData(imagePath);
    }

    public static void generatePic(String imagePath) throws IOException {
        int width = 25;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, Integer.valueOf("00ff00", 16));
            }
        }
        ImageIO.write(image, "png", new File(imagePath));
    }
}
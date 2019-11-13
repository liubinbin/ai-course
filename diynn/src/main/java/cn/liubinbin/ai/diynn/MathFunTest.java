package cn.liubinbin.ai.diynn;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * Created by bin on 2019/11/13.
 * http://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html?overview-summary.html
 *
 * @Description: TODO
 */
public class MathFunTest {

    public static void main(String[] args) {
        //定义向量1
        RealVector value1 = new ArrayRealVector(new Double[]{
                2d, 2d, 3d
        });
        //定义向量2
        RealVector value2 = new ArrayRealVector(new Double[]{
                3d, 4d, 5d
        });
        //向量点积
        System.out.println(value1.dotProduct(value2));

        ArrayRealVector arrayRealVector = new ArrayRealVector();
        arrayRealVector.append(1);
        arrayRealVector.append(2);
        arrayRealVector.append(3);
        System.out.println("arrayRealVector " + arrayRealVector);


//        //取向量的模
//        System.out.println(value1.getNorm());
//        //向量相加
//        System.out.println(value1.add(value2));
//        //向量相减
//        System.out.println(value1.subtract(value2));
//        //向量相除
//        System.out.println(value1.ebeDivide(value2));
//        //向量相乘
//        System.out.println(value1.ebeMultiply(value2));
//        //向量之间的距离
//        System.out.println(value1.getDistance(value2));
//        //向量的cos值
//        System.out.println(value1.cosine(value2));
//        //向量的维度
//        System.out.println(value1.getDimension());
//        //向量的三个值相加
//        System.out.println(value1.getL1Norm());

        System.out.println("E " + Math.E);
        Math.pow(Math.E, 2); //即为e的平方
    }
}

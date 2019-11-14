package cn.liubinbin.ai.index;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

import java.util.List;

public class Index {

    private final String rootDataPath = "D:\\src\\ai-course\\index\\data";
    private final int newsCount = 10;

    public Index(){

    }

    public void build() {
        for (int idx = 1; idx <= newsCount; idx++){

        }
    }

    public static void main(String[] args) {
        System.out.println("hello index world, let's try hanlp");

        String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
        HanLP.Config.ShowTermNature = false;
        List<Term> segResult = SpeedTokenizer.segment(text);
        for (Term term : segResult) {
            System.out.println("term:{ " + term.word + " | " + term.getFrequency() + " | " + term.nature + " | " + term.offset + " }") ;
        }
        System.out.println(segResult);
    }
}

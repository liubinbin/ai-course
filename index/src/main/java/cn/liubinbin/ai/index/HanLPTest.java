package cn.liubinbin.ai.index;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

import java.util.List;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public class HanLPTest {

    public static void main(String[] args) {
        String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
        HanLP.Config.ShowTermNature = false;
        List<Term> segResult = SpeedTokenizer.segment(text);
        for (Term term : segResult) {
            System.out.println("term:{ " + term.word + " | " + term.getFrequency() + " | " + term.nature + " | " + term.offset + " }");
        }
        System.out.println(segResult);
    }
}

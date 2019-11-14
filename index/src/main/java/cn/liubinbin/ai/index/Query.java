package cn.liubinbin.ai.index;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public class Query {

    private String wordA;
    private String wordB;
    private BoolFlag boolFlag;

    public Query(String word) {
        this.wordA = word;
    }

    public Query(String wordA, BoolFlag boolFlag, String wordB) {
        this.wordA = wordA;
        this.boolFlag = boolFlag;
        this.wordB = wordB;
    }

    public static void main(String[] args) {
        Query query = new Query("hello");
        System.out.println(query.isSingle() + " | " + query.toString());

        query = new Query("hello", BoolFlag.AND, "world");
        System.out.println(query.isSingle() + " | " + query.toString());
    }

    public boolean isSingle() {
        return wordB == null;
    }

    public String getWordA() {
        return wordA;
    }

    public void setWordA(String wordA) {
        this.wordA = wordA;
    }

    public String getWordB() {
        return wordB;
    }

    public void setWordB(String wordB) {
        this.wordB = wordB;
    }

    public BoolFlag getBoolFlag() {
        return boolFlag;
    }

    public void setBoolFlag(BoolFlag boolFlag) {
        this.boolFlag = boolFlag;
    }

    @Override
    public String toString() {
        if (isSingle()) {
            return "{" + wordA + "}";
        } else {
            return "{" + wordA + " " + boolFlag.getDesc() + " " + wordB + "}";
        }
    }
}

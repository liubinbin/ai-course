package cn.liubinbin.ai.index;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public enum BoolFlag {

    AND(0, "AND"), OR(1, "OR");

    private int flag;
    private String desc;

    BoolFlag(int flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public static BoolFlag parse(String flagStr) {
        flagStr = flagStr.trim();
        if (flagStr.equalsIgnoreCase(AND.getDesc())) {
            return AND;
        } else if (flagStr.equalsIgnoreCase(OR.getDesc())) {
            return OR;
        } else {
            return null;
        }
    }

    public String getDesc() {
        return desc;
    }
}

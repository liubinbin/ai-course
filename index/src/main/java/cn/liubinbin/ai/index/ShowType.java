package cn.liubinbin.ai.index;

/**
 * Created by bin on 2019/11/14.
 *
 * @Description: TODO
 */
public enum ShowType {

    ID(0, "ID"), TITLE(1, "TITLE"), CONTENT(1, "CONTENT");

    private int type;
    private String desc;

    ShowType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

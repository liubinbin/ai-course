package cn.liubinbin.ai.diynn;

/**
 * Created by bin on 2019/11/13.
 *
 * @Description: TODO
 */
public enum LayerType {

    INPUT(0, "输入层"), HIDDEN(1, "隐藏层"), OUTPUT(2, "输出层");

    private int type;
    private String desc;

    LayerType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}

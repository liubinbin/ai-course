package cn.liubinbin;

/**
 * Created by bin on 2019/11/1.
 *
 * @Description: 位子状态，可能是白子，可能是黑子，可能无落子，表示三种状态。
 */
public enum CellStatus {

    NONE(0, "无"), BLACK(1, "黑"), WHITE(2, "白");

    private int status;
    private String desc;

    CellStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

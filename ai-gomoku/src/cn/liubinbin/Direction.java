package cn.liubinbin;

public enum Direction {

    RIGHT(0, 1, "右"),
    LEFT(0, -1, "左"),
    UP(-1, 0, "上"),
    DOWN(1, 0, "下"),
    RIGHT_UP(-1, 1, "右上"),
    RIGHT_DOWN(1, 1, "右下"),
    LEFT_UP(-1, -1, "右上"),
    LEFT_DOWN(1, -1, "右下");

    private int x;
    private int y;
    private String desc;

    Direction(int x, int y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

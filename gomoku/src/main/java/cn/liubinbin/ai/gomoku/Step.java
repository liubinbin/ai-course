package cn.liubinbin.ai.gomoku;

public class Step {

    private int x;
    private int y;
    private CellStatus cellStatus;

    public Step(int x, int y, CellStatus cellStatus) {
        this.x = x;
        this.y = y;
        this.cellStatus = cellStatus;
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

    public CellStatus getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus) {
        this.cellStatus = cellStatus;
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + ", cellStatus: " + cellStatus + "}";
    }
}

package cn.liubinbin;

public class TestValue {

    public static void main(String[] args) throws Exception {
        Gomoku gomoku = new Gomoku();
        gomoku.markCell(3, 4, CellStatus.WHITE);
        gomoku.markCell(3, 5, CellStatus.WHITE);
        gomoku.markCell(3, 6, CellStatus.WHITE);
        gomoku.markCell(3, 7, CellStatus.WHITE);
        gomoku.markCell(3, 8, CellStatus.WHITE);
        gomoku.printBoard();
        int valueByPosStatus = gomoku.calValuePosStatus(3, 4, CellStatus.WHITE);
        System.out.println("valueByPosStatus: " + valueByPosStatus);
        System.out.println("ifCanWin: " + gomoku.ifCanWin(CellStatus.WHITE));

        long time1 = System.currentTimeMillis();
        int value = gomoku.calValue();
        long time2 = System.currentTimeMillis();
        System.out.println("value: " + value + "  use time: " + (time2 - time1));
    }
}

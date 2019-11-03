package cn.liubinbin;

public class TestValue {

    public static void main(String[] args) throws Exception {
        Gomoku gomoku = new Gomoku();
        gomoku.markCell(1, 1, CellStatus.BLACK);
        gomoku.markCell(1, 2, CellStatus.WHITE);
        gomoku.markCell(1, 3, CellStatus.WHITE);
//        gomoku.markCell(1,4, CellStatus.WHITE);
//        gomoku.markCell(1,5, CellStatus.BLACK);
        gomoku.printBoard();
        int value = gomoku.calValuePosStatus(1, 2, CellStatus.WHITE);
        System.out.println("value: " + value);
    }
}

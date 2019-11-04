package cn.liubinbin;

public class TestValue {

    public static void main(String[] args) throws Exception {
        Gomoku gomoku = new Gomoku();
        gomoku.markCell(2, 2, CellStatus.BLACK);

//        gomoku.markCell(3, 4, CellStatus.WHITE);
//        gomoku.markCell(3, 5, CellStatus.WHITE);
//        gomoku.markCell(3, 6, CellStatus.WHITE);
//        gomoku.markCell(3, 8, CellStatus.WHITE);
//        gomoku.markCell(2, 3, CellStatus.BLACK);
//        gomoku.markCell(2, 4, CellStatus.BLACK);
//        gomoku.markCell(3, 7, CellStatus.BLACK);
//        gomoku.markCell(3, 4, CellStatus.BLACK);
        gomoku.printBoard();
//        int x;
//        int y;
//        for (x = 1; x < 15 + 1; x++) {
//            for (y = 1; y < 15 + 1; y++) {
//                if (gomoku.isPosSameStatus(x, y, CellStatus.NONE)) {
//                    gomoku.markCell(x, y, CellStatus.BLACK);
//                    System.out.println("pick x: " + x + " y: " + y + " value: " + gomoku.calValue());
//                    gomoku.stepBack(x, y);
//                }
//            }
//        }

        int valueByPosStatus = gomoku.calValuePosStatus(3, 4, CellStatus.BLACK);
        System.out.println("valueByPosStatus: " + valueByPosStatus);
        System.out.println("ifCanWin: " + gomoku.ifCanWin(CellStatus.WHITE));

        int value = gomoku.calValue();
        System.out.println("value: " + value);
    }
}

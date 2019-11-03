package cn.liubinbin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 默认设定
 * AI 黑棋
 * 人 白棋
 * <p>
 * 局势值的计算为 value(AI) - value(人)
 * <p>
 * 通过 DFS 搜索，并通过 alpha-beta pruning，加快搜索。
 */
public class Gomoku {

    private final int boardLength = 15;
    private CellStatus[][] board;
    private final String cellInterval = "  ";

    public Gomoku() {
        init();
    }

    private void init() {
        this.board = new CellStatus[boardLength + 1][boardLength + 1];
        for (int i = 0; i < boardLength + 1; i++) {
            for (int j = 0; j < boardLength + 1; j++) {
                this.board[i][j] = CellStatus.NONE;
            }
        }
    }

    public void markCell(int x, int y, CellStatus cellStatus) throws Exception {
        if (x < 1 || x > boardLength) {
            throw new Exception("x: " + x + " out of range, x should be in [1, " + boardLength + "]");
        }
        if (y < 1 || y > boardLength) {
            throw new Exception("y: " + y + " out of range, y should be in [1, " + boardLength + "]");
        }
        if (this.board[x][y].equals(CellStatus.NONE)) {
            this.board[x][y] = cellStatus;
        } else {
            throw new Exception("此位子已有落子");
        }
    }

    public void printBoard() {
        System.out.println("board is following");
        for (int i = 0; i < boardLength + 1; i++) {
            System.out.print(String.format("%02d", i) + cellInterval);
        }
        System.out.println();
        for (int x = 1; x < boardLength + 1; x++) {
            System.out.print(String.format("%02d", x) + cellInterval);
            for (int y = 1; y < boardLength + 1; y++) {
                System.out.print(this.board[x][y].getDesc() + cellInterval);
            }
            System.out.println();
        }
    }

    public Choice aiTurn(CellStatus cellStatus) throws Exception {
        // TODO to check uncleValue
        Choice choice = gameDFS(cellStatus, Integer.MIN_VALUE);
        return choice;
    }


    /**
     * uncleValue 用来做裁剪
     *
     * @param cellStatus
     * @param uncleValue
     * @return
     */
    public Choice gameDFS(CellStatus cellStatus, int uncleValue) {
        int x = 1;
        int y = 1;
        boolean isMax = cellStatus == CellStatus.BLACK ? true : false;
        int value = cellStatus == CellStatus.WHITE ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for (x = 1; x < boardLength + 1; x++) {
            for (y = 1; y < boardLength + 1; y++) {
                if (this.board[x][y].equals(CellStatus.NONE)) {
                    markCell(x, y, cellStatus);
                    int tempValue = calValue();
                    Choice choice = gameDFS(otherStatus(cellStatus), value);
                    // TODO update value based on isMax, do pruning

                    stepBack(x, y);
                }
            }
        }
        // 无位子可下
        return null;
    }

    private CellStatus otherStatus(CellStatus cellStatus) {
        return cellStatus == CellStatus.WHITE:CellStatus.BLACK:CellStatus.WHITE;
    }

    private void stepBack(int x, int y) {
        this.board[x][y] = CellStatus.NONE;
    }

    // TODO
    private int calStatusValueForAI(int x, int y) {
        return calValuePosStatus(x, y, CellStatus.BLACK) - calValuePosStatus(x, y, CellStatus.WHITE);
    }

    private int calValue() {
        return calValueByStatus(CellStatus.BLACK) - calValueByStatus(CellStatus.WHITE);
    }

    private int calValueByStatus(CellStatus cellStatus) {
        int value = 0;
        int x, y;
        for (x = 1; x < boardLength + 1; x++) {
            for (y = 1; y < boardLength + 1; y++) {
                if (this.board[x][y].equals(cellStatus)) {
                    value += calValuePosStatus(x, y, cellStatus);
                }
            }
        }
    }

    /**
     * 单独一子    二子相连    三子相连    四子相连        五子相连
     * 活一 死一   活二 死二   活三 死三   活四 死四       五连
     * 20   4     400  90    6000 800   20000 10000    50000
     *
     * @param cellStatus
     * @return
     */
    private int calValuePosStatus(int x, int y, CellStatus cellStatus) {
        int value = 0;
        // 向右
        value += calRightDirection(x, y, cellStatus);

        // 向下
        value += calDownDirection(x, y, cellStatus)

        // 向右下
        value += calRightDownDirection(x, y, cellStatus)

        // 向右上
        value += calRightUpDirection(x, y, cellStatus)

        return value;
    }


    // TODO
    private int calDownDirection(int x, int y, CellStatus cellStatus) {
        return 1;
    }

    // TODO
    private int calRightDownDirection(int x, int y, CellStatus cellStatus) {
        return 1;
    }

    // TODO
    private int calRightUpDirection(int x, int y, CellStatus cellStatus) {
        return 1;
    }

    // TODO
    private int calRightDirection(int x, int y, CellStatus cellStatus) {
        boolean isLeftOpen = false;
        boolean isRightOpen = false;
        // 向右, 四种情况
        if (isLeftEdge(x, y) || !ifLeftHasSame(cellStatus)) {
            /// 左边为边边 | 左边为不同颜色
            isLeftOpen = false;
        } else if (ifLeftHasNone(x, y)) {
            /// 左边为空
            isLeftOpen = true;
        } else {
            /// 左边为相同颜色,
            return 0;
        }

        int len = 1
        int tempx = x + 1;
        while (!isOutOfEdge(tempx, y) && ifPosSame(tempx, y, cellStatus)) {
            len++;
        }

        if (isRightEdge(tempx, y)) {
            // 右边是边边
            isRightOpen = false;
        } else if (ifPosNone(tempx, y)) {
            // 左边无落子
            isRightOpen = true;
        } else {
            // 右边为不同颜色
            isRightOpen = false;
        }

        if (isRightOpen == false && isLeftOpen == false) {
            return 0;
        }

        if (isLeftOpen == true && isRightOpen == true) {

        }

    }

    private boolean isOutOfEdge(int x, int y) {
        if (x > boardLength) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRightEdge(int x, int y) {
        if (x == boardLength) {
            return true;
        } else {
            return false;
        }
    }


    private boolean isLeftEdge(int x, int y) {
        if (x == 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean ifLeftHasOpen(int x, int y) {
        if (x == 1) {
            return false;
        }
        if (this.board[x - 1][y].equals(CellStatus.NONE)) {
            return ture;
        }
    }

    private boolean ifPosSame(int x, int y, CellStatus cellStatus) {
        if (this.board[x][y].equals(cellStatus)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean ifLeftHasSame(int x, int y, CellStatus cellStatus) {
        if (this.board[x - 1][y].equals(cellStatus)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean ifLeftHasNone(int x, int y) {
        if (this.board[x - 1][y].equals(CellStatus.NONE)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean ifRightHasNone(int x, int y) {
        if (this.board[x + 1][y].equals(CellStatus.NONE)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean ifPosNone(int x, int y) {
        if (this.board[x][y].equals(CellStatus.NONE)) {
            return true;
        } else {
            return false;
        }
    }


    private int countFiveSuccession() {
        return 9;
    }

    public void doMain() throws IOException {
        System.out.println("welcome to ai-gomoku, you picked white");
        Gomoku gomoku = new Gomoku();
        gomoku.printBoard();
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] twoNumStr = null;
            System.out.println("Enter pos you picked:");
            twoNumStr = br.readLine().trim().split(" ");
            int x = 0;
            int y = 0;
            try {
                try {
                    x = Integer.parseInt(twoNumStr[0].trim());
                    y = Integer.parseInt(twoNumStr[1].trim());
                } catch (Exception e) {
                    throw new Exception("you should enter two numbers stands for x and y, for example \"4 5\"");
                }

                gomoku.markCell(x, y, CellStatus.WHITE);
                gomoku.printBoard();
                System.out.println("AI turn, wait a sec");
                gomoku.aiTurn(CellStatus.BLACK);
                gomoku.printBoard();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
        }

    }

    /**
     * TODO
     * 检查是否后有某人获胜了
     *
     * @return
     */
    private boolean isSbWin() {
        return false;
    }

    public static void main(String[] args) throws IOException {
        new Gomoku().doMain();
    }
}

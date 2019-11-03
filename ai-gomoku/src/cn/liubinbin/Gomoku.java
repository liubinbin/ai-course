package cn.liubinbin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * homework: 用alpha-beta剪枝实现一个简易的五子棋AI
 * <p>
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
        AlphaBetaPair alphaBetaPair = new AlphaBetaPair();
        Choice choice = gameDFS(cellStatus, alphaBetaPair);
        return choice;
    }


    /**
     * uncleValue 用来做裁剪
     *
     * @param cellStatus
     * @param uncleValue
     * @return
     */
    public Choice gameDFS(CellStatus cellStatus, AlphaBetaPair alphaBetaPair) throws Exception {
        int x = 1;
        int y = 1;
        boolean isMax = cellStatus == CellStatus.BLACK ? true : false;
        int value = cellStatus == CellStatus.WHITE ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for (x = 1; x < boardLength + 1; x++) {
            for (y = 1; y < boardLength + 1; y++) {
                if (this.board[x][y].equals(CellStatus.NONE)) {
                    // make a step
                    markCell(x, y, cellStatus);
                    int tempValue = calValue();
                    Choice choice = gameDFS(otherStatus(cellStatus), alphaBetaPair);
                    // TODO update value based on isMax, do pruning

                    // step back
                    stepBack(x, y);
                }
            }
        }
        // update alphaBetaPair
        // 无位子可下
        return null;
    }

    private CellStatus otherStatus(CellStatus cellStatus) {
        return cellStatus == CellStatus.WHITE ? CellStatus.BLACK : CellStatus.WHITE;
    }

    private void stepBack(int x, int y) {
        this.board[x][y] = CellStatus.NONE;
    }

    // TODO 计算局势
    public int calValue() {
        long time1 = System.currentTimeMillis();
        int valueForBlack = calValueByStatus(CellStatus.BLACK);
        long time2 = System.currentTimeMillis();
        int valueForWhite = calValueByStatus(CellStatus.WHITE);
        long time3 = System.currentTimeMillis();
        System.out.println("one round calValue: black: " + (time2 - time1) + " white: " + (time3 - time2) + " total: " + (time3 - time1));
        return valueForBlack - valueForWhite;
    }

    public int calValueByStatus(CellStatus cellStatus) {
        int value = 0;
        int x, y;
        for (x = 1; x < boardLength + 1; x++) {
            for (y = 1; y < boardLength + 1; y++) {
                if (this.board[x][y].equals(cellStatus)) {
                    value += calValuePosStatus(x, y, cellStatus);
                }
            }
        }
        return value;
    }

    /**
     * 单独一子    二子相连    三子相连    四子相连        五子相连
     * 活一 死一   活二 死二   活三 死三   活四 死四       五连
     * 20   4     400  90    6000 800   20000 10000    50000
     *
     * @param cellStatus
     * @return
     */
    public int calValuePosStatus(int x, int y, CellStatus cellStatus) {
        int value = 0;
        // 向右
        value += calValueByDirection(x, y, cellStatus, Direction.RIGHT, Direction.LEFT);
        System.out.println("value after right: " + value);

        // 向下
        value += calValueByDirection(x, y, cellStatus, Direction.DOWN, Direction.UP);
        System.out.println("value after down: " + value);

        // 向右下
        value += calValueByDirection(x, y, cellStatus, Direction.RIGHT_DOWN, Direction.LEFT_UP);
        System.out.println("value after rightdown: " + value);

        // 向右上
        value += calValueByDirection(x, y, cellStatus, Direction.RIGHT_UP, Direction.LEFT_DOWN);
        System.out.println("value after rightup: " + value);
        return value;
    }

    /**
     * @param x
     * @param y
     * @param cellStatus
     * @return
     */
    private int calValueByDirection(int x, int y, CellStatus cellStatus, Direction direction, Direction negDirection) {
        boolean isNegDirectionOpen = false;
        boolean isDirectionOpen = false;
        // 反方向, 四种情况
        if (isDirectEdge(x, y, negDirection)) {
            /// 反方向为边边
            isNegDirectionOpen = false;
        } else if (ifDirectionHasNone(x, y, negDirection)) {
            /// 反方向为空
            isNegDirectionOpen = true;
        } else if (ifDirectionHasSame(x, y, cellStatus, negDirection)) {
            /// 反方向为相同颜色
            return 0;
        } else {
            /// 反方向为不同颜色,
            isNegDirectionOpen = false;
        }

        int len = 1;
        int tempx = x + direction.getX();
        int tempy = y + direction.getY();
        while (!isOutOfEdge(tempx, tempy) && ifPosSame(tempx, tempy, cellStatus)) {
            len++;
            tempx += direction.getX();
            tempy += direction.getY();
        }

        if (isOutOfEdge(tempx, tempy)) {
            // 正方向是边边
            isDirectionOpen = false;
        } else if (ifPosNone(tempx, tempy)) {
            // 正方向无落子
            isDirectionOpen = true;
        } else {
            // 正方向为不同颜色
            isDirectionOpen = false;
        }

        if (isDirectionOpen == false && isNegDirectionOpen == false) {
            // 两头都没有空节点
            if (len == 5) {
                // 只有当下了能五连菜可以。
                return 50000;
            }
            return 0;
        } else if (isNegDirectionOpen == true && isDirectionOpen == true) {
            // 两头都有空节点，称为活节点
            return getValueForLen(len, true);
        } else {
            // 只有一头都有空节点，称为死节点
            return getValueForLen(len, false);
        }
    }


    /**
     * 单独一子    二子相连    三子相连    四子相连        五子相连
     * 活一 死一   活二 死二   活三 死三   活四 死四       五连
     * 20   4     400  90    6000 800   20000 10000    50000
     *
     * @param len 连子长度
     * @param len 是否是活子，true 代表是活子， false 代表是死子。
     * @return
     */
    private int getValueForLen(int len, boolean isOpen) {
        if (isOpen) {
            if (len < 1) {
                return 0;
            } else if (len == 1) {
                return 20;
            } else if (len == 2) {
                return 400;
            } else if (len == 3) {
                return 6000;
            } else if (len == 4) {
                return 20000;
            } else {
                return 50000;
            }
        } else {
            if (len < 1) {
                return 0;
            } else if (len == 1) {
                return 4;
            } else if (len == 2) {
                return 90;
            } else if (len == 3) {
                return 800;
            } else if (len == 4) {
                return 10000;
            } else {
                return 50000;
            }
        }

    }


    private boolean isOutOfEdge(int x, int y) {
        if (x > boardLength || y > boardLength || x < 1 || y < 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isDirectEdge(int x, int y, Direction direction) {
        return isOutOfEdge(x + direction.getX(), y + direction.getY());
    }

    private boolean ifPosSame(int x, int y, CellStatus cellStatus) {
        if (this.board[x][y].equals(cellStatus)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean ifDirectionHasSame(int x, int y, CellStatus cellStatus, Direction direction) {
        if (this.board[x + direction.getX()][y + direction.getY()].equals(cellStatus)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean ifDirectionHasNone(int x, int y, Direction direction) {
        if (this.board[x + direction.getX()][y + direction.getY()].equals(CellStatus.NONE)) {
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


    public void doMain() throws IOException {
        System.out.println("welcome to ai-gomoku, your colour is white");
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

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
    private final String cellInterval = "  ";
    private final int depth_threshold = 4; // should be even number;
    private CellStatus[][] board;

    public Gomoku() {
        init();
    }

    public static void main(String[] args) throws IOException {
        new Gomoku().doMain();
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
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        AlphaBetaPair alphaBetaPair = new AlphaBetaPair();
        Choice choice = gameDFS(cellStatus, alphaBetaPair, 1);
        return choice;
    }

    /**
     * 返回值如果是 null 的话，说明以无法放棋子。
     *
     * @param cellStatus
     * @param alphaBetaPair， 用于剪枝
     * @return
     */
    public Choice gameDFS(CellStatus cellStatus, AlphaBetaPair alphaBetaPair, int level) throws Exception {
        // System.out.println("level: " + level + " cellStatus: " + cellStatus + " alpha " + alphaBetaPair.getAlpha() + " beta: " + alphaBetaPair.getBeta());
        int x = 1;
        int y = 1;
        boolean isMax = cellStatus == CellStatus.BLACK ? true : false;
        Choice choice = null;
        int value = isMax == true ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (x = 1; x < boardLength + 1; x++) {
            for (y = 1; y < boardLength + 1; y++) {
                if (this.board[x][y].equals(CellStatus.NONE)) {
                    // make a step
                    markCell(x, y, cellStatus);
                    if (level > depth_threshold) {
                        int tempValue = calValue();
                        // System.out.println("depth_threshold isMax " + isMax + " " + tempValue + " | " + alphaBetaPair.getAlpha() + " | " + alphaBetaPair.getBeta());
                        if (isMax) {
                            if (tempValue > alphaBetaPair.getAlpha()) {
                                alphaBetaPair.setAlpha(tempValue);
                            }
                        } else {
                            if (alphaBetaPair.getBeta() > tempValue) {
                                alphaBetaPair.setBeta(tempValue);
                            }
                        }
                        stepBack(x, y);
                        return new Choice(x, y, tempValue);
                    }
                    // cal value if we can
                    if (ifCanWin(cellStatus)) {
                        // we can win
                        int temp = cellStatus == CellStatus.BLACK ? Constants.wulian : 0 - Constants.wulian;
                        stepBack(x, y);
                        return new Choice(x, y, temp);
                    }

                    // pick value from choice
//                    System.out.println("call gameDFS x: " + x + " y: " + y + " level: " + level + " cellStatus: " + cellStatus);
                    Choice tempChoice = gameDFS(otherStatus(cellStatus), alphaBetaPair, level + 1);
                    // if tempChoice is not null, pick the best choice
                    if (tempChoice != null) {
                        // have choice
                        // System.out.println("isMax " + isMax + " " + tempChoice.getValue() + " | " + alphaBetaPair.getAlpha() + " | " + alphaBetaPair.getBeta());
                        if (isMax) {
                            // prune
                            if (tempChoice.getValue() > alphaBetaPair.getBeta()) {
                                stepBack(x, y);
                                // System.out.println("---- ai beta prune");
                                return new Choice(x, y, calValue());
                            }
                            // update choice and value
                            // System.out.println("max update tempChoice.getValue(): " + tempChoice.getValue() + " value: " + value);
                            if (tempChoice.getValue() > value) {
                                value = tempChoice.getValue();
                                choice = tempChoice;
                            }
                        } else {
                            // prune
                            if (tempChoice.getValue() < alphaBetaPair.getAlpha()) {
                                stepBack(x, y);
                                // System.out.println("---- ai alpha prune");
                                return new Choice(x, y, calValue());
                            }
                            // update choice and value
//                            System.out.println("min update tempChoice.getValue(): " + tempChoice.getValue() + " value: " + value);
                            if (tempChoice.getValue() < value) {
                                value = tempChoice.getValue();
                                choice = tempChoice;
                            }
                        }
                    } else {
                        // no chice coz board is full or prune
                        stepBack(x, y);
                        return new Choice(x, y, calValue());
                    }
                    // step back
                    stepBack(x, y);
                }
            }
        }
        // update alphaBetaPair
        if (isMax) {
            if (value > alphaBetaPair.getAlpha()) {
                alphaBetaPair.setAlpha(value);
            }
        } else {
            if (alphaBetaPair.getBeta() > value) {
                alphaBetaPair.setBeta(value);
            }
        }
        return choice;
    }

    private CellStatus otherStatus(CellStatus cellStatus) {
        return cellStatus == CellStatus.WHITE ? CellStatus.BLACK : CellStatus.WHITE;
    }

    private void stepBack(int x, int y) {
        this.board[x][y] = CellStatus.NONE;
    }

    public boolean ifCanWin(CellStatus cellStatus) {
        boolean ifCanWin = false;
        int x, y;
        for (x = 1; x < boardLength + 1; x++) {
            for (y = 1; y < boardLength + 1; y++) {
                if (this.board[x][y].equals(cellStatus)) {
                    ifCanWin = ifCanWinPosStatus(x, y, cellStatus);
                    if (ifCanWin) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 计算局势
    public int calValue() {
//        long time1 = System.currentTimeMillis();
        int valueForBlack = calValueByStatus(CellStatus.BLACK);
//        long time2 = System.currentTimeMillis();
        int valueForWhite = calValueByStatus(CellStatus.WHITE);
//        long time3 = System.currentTimeMillis();
//        System.out.println("one round calValue: black: " + (time2 - time1) + " white: " + (time3 - time2) + " total: " + (time3 - time1));
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

    // 判断是否可以成功
    public boolean ifCanWinPosStatus(int x, int y, CellStatus cellStatus) {
        int value = 0;
        // 向右
        value = calValueByDirection(x, y, cellStatus, Direction.RIGHT, Direction.LEFT);
        if (value == Constants.wulian) {
            return true;
        }

        // 向下
        value = calValueByDirection(x, y, cellStatus, Direction.DOWN, Direction.UP);
        if (value == Constants.wulian) {
            return true;
        }

        // 向右下
        value = calValueByDirection(x, y, cellStatus, Direction.RIGHT_DOWN, Direction.LEFT_UP);
        if (value == Constants.wulian) {
            return true;
        }

        // 向右上
        value = calValueByDirection(x, y, cellStatus, Direction.RIGHT_UP, Direction.LEFT_DOWN);
        if (value == Constants.wulian) {
            return true;
        }
        return false;
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
//        System.out.println("value after right: " + value);

        // 向下
        value += calValueByDirection(x, y, cellStatus, Direction.DOWN, Direction.UP);
//        System.out.println("value after down: " + value);

        // 向右下
        value += calValueByDirection(x, y, cellStatus, Direction.RIGHT_DOWN, Direction.LEFT_UP);
//        System.out.println("value after rightdown: " + value);

        // 向右上
        value += calValueByDirection(x, y, cellStatus, Direction.RIGHT_UP, Direction.LEFT_DOWN);
//        System.out.println("value after rightup: " + value);
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
                return Constants.wulian;
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
                return Constants.zero;
            } else if (len == 1) {
                return Constants.huoyi;
            } else if (len == 2) {
                return Constants.huoer;
            } else if (len == 3) {
                return Constants.huosan;
            } else if (len == 4) {
                return Constants.huosi;
            } else {
                return Constants.wulian;
            }
        } else {
            if (len < 1) {
                return Constants.zero;
            } else if (len == 1) {
                return Constants.siyi;
            } else if (len == 2) {
                return Constants.sier;
            } else if (len == 3) {
                return Constants.sisan;
            } else if (len == 4) {
                return Constants.sisi;
            } else {
                return Constants.wulian;
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
        boolean isFinish = false; // true if sb wins or
        while (!isFinish) {
            // people turn
            while (!isFinish) {
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
                    if (gomoku.ifCanWin(CellStatus.WHITE)) {
                        isFinish = true;
                        System.out.println("***** people win *****");
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }

            // ai turn
            while (!isFinish) {
                System.out.println("AI turn, wait a sec");
                try {
                    long time1 = System.currentTimeMillis();
                    Choice choice = gomoku.aiTurn(CellStatus.BLACK);
                    if (choice == null) {
                        System.out.println("ai choice is null");
                    }
                    // System.out.println("----ai choice.x: " + choice.getX() + " choice.y: " + choice.getY() + " ---- use time " + (System.currentTimeMillis() - time1));
                    gomoku.markCell(choice.getX(), choice.getY(), CellStatus.BLACK);
                    gomoku.printBoard();
                    if (gomoku.ifCanWin(CellStatus.BLACK)) {
                        isFinish = true;
                        System.out.println("***** ai win *****");
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

            }

        }

    }
}

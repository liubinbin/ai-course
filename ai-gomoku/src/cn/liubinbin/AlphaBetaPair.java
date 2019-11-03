package cn.liubinbin;

public class AlphaBetaPair {

    private int alpha; // 极大值
    private int beta;  // 极小值

    public AlphaBetaPair() {
        alpha = Integer.MIN_VALUE;
        beta = Integer.MAX_VALUE;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }
}

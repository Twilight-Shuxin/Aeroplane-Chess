package xyz.chengzi.aeroplanechess.model;

public class ChessPiece {
    public int player, state;//state是叠加的棋子的个数

    public ChessPiece(int player, int state) {
        this.player = player; this.state = state;
    }

    public int getPlayer() {
        return player;
    }
}

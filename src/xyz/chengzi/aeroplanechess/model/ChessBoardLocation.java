package xyz.chengzi.aeroplanechess.model;

import java.util.Objects;

public class ChessBoardLocation {
    private int color;
    private int index;
    private final int boardIndex;
    static private final int[] boardIndexInv = new int[2000];

    public ChessBoardLocation(int color, int index) {
        this.color = color;
        this.index = index;
        if(index < 13) {
            boardIndexInv[(1 + 13 * color + 4 * index) % (4 * 13)] = index;
            boardIndex = (1 + 13 * color + 4 * index) % (4 * 13);
        }
        else {
            int t = 51 + color * 11 + index - 12;
            boardIndexInv[t] = index;
            boardIndex = t;
        }
    }

    public static int getBoardIndexInv(int t) {
        return boardIndexInv[t];
    }

    public int getBoardIndex() {
        return boardIndex;
    }

    public int getColor() {
        return color;
    }

    public void setIndex(int newIndex) {
        index = newIndex;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoardLocation location = (ChessBoardLocation) o;
        return color == location.color && index == location.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, index);
    }
}

package xyz.chengzi.aeroplanechess.model;

import xyz.chengzi.aeroplanechess.Frame2.BattleChess;
import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.ChessBoardListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Listenable<ChessBoardListener> {
    private final List<ChessBoardListener> listenerList = new ArrayList<>();
    public final Square[][] grid;
    private final int dimension, endDimension;
    private int[][][] noteLocation = new int[4][4][2];
    private boolean hasArrived = false;
    private boolean isOnTopOfOthers = false;
    private boolean isOnTopOfOwn = false;
    private int distance;
    ChessBoardComponent CBC;

    public ChessBoard(int dimension, int endDimension, ChessBoardComponent CBC) {
        this.grid = new Square[4][dimension + endDimension + 200];
        this.dimension = dimension;
        this.endDimension = endDimension;
        this.CBC = CBC;
        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension + 5; j ++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }

    public void placeInitialPieces(ChessBoardComponent CBC, int totPlayers) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < dimension + endDimension + 5; j++) {
                ChessBoardLocation now = new ChessBoardLocation(i, j);
                removeChessPieceAt(now);
                grid[i][j].setPiece(null);
            }
        }
        listenerList.forEach(listener -> listener.onChessBoardReload(this));
        if(totPlayers == 1){
            for(int i = 0; i < 4; i ++) {
                for(int j = 0; j < 4; j ++)
                    setChessPieceAt(grid[i][19 + j].getLocation(), new ChessPiece(i, 1));
            }
        }
        else {
            for (int i = 0; i < totPlayers; i++) {
                for (int j = 0; j < 4; j++)
                    setChessPieceAt(grid[i][19 + j].getLocation(), new ChessPiece(i, 1));
            }
        }
    }

    public boolean getHasArrived(){
        return hasArrived;
    }

    public boolean getIsOnTopOfOthers(){
        return isOnTopOfOthers;
    }

    public boolean getIsOnTopOfOwn(){
        return isOnTopOfOwn;
    }

    public int getDistance(){
        return distance;
    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getColor()][location.getIndex()];
    }

    public int getDimension() {
        return dimension;
    }

    public int getEndDimension() {
        return endDimension;
    }

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        listenerList.forEach(listener -> listener.onChessPiecePlace(location, piece));
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        if(piece == null) return null;
        getGridAt(location).setPiece(null);
        listenerList.forEach(listener -> listener.onChessPieceRemove(location));
        return piece;
    }

    //fly是给定起始点和终止点跳跃
    public BattleChess flyChessPiece(ChessBoardComponent CBC, ChessBoardLocation bef, ChessBoardLocation dest, int currentPlayer) {
        BattleChess battleChess = new BattleChess(null, null);
        if(grid[dest.getColor()][dest.getIndex()].getPiece() != null) {
            ChessPiece formerPiece = grid[dest.getColor()][dest.getIndex()].getPiece();
            System.out.println("There is a chess " + dest.getColor() + " " + dest.getIndex() + " " + formerPiece.player + " " + formerPiece.state);
            if(formerPiece.player == currentPlayer) {
                int cnt = formerPiece.state; removeChessPieceAt(dest);
                System.out.println("ok till now" + (dest == null));
                ChessPiece now = removeChessPieceAt(bef);
                CBC.setChessAtGrid(dest, CBC.BOARD_COLORS[currentPlayer], now.state + cnt);
                System.out.println("where's the problem?");
                grid[dest.getColor()][dest.getIndex()].setPiece(new ChessPiece(currentPlayer, now.state + cnt));
                System.out.println("survived");
            }
            else {
                System.out.println("Go for battle");
                battleChess.bef = bef;
                battleChess.dest = dest;
            }
        }
        else setChessPieceAt(dest, removeChessPieceAt(bef));
        return battleChess;
    }

    ChessBoardLocation[] recMove = new ChessBoardLocation[50];
    int cnt = 0;
    //move是给定起始点和移动步数
    public BattleChess moveChessPiece(ChessBoardLocation src, int steps, int currentPlayer, ChessBoardComponent CBC) {
        distance = steps; cnt = 0;
        BattleChess battleChess = new BattleChess(null, null);
        ChessBoardLocation dest = src;
        recMove[++ cnt] = new ChessBoardLocation(dest.getColor(), dest.getIndex());
        int num = 1; 
        for (int i = 0; i < steps; i++) {//记录路径
            dest = nextLocation(dest, currentPlayer, num);
            recMove[++ cnt] = new ChessBoardLocation(dest.getColor(), dest.getIndex());
            if(dest.getIndex() == 18) num = -1;
            if(dest.getIndex() == 12) num = 1;
        }
        if(dest.getColor() == currentPlayer && dest.getIndex() == 4){//捷径飞行
            distance += 12;
            dest.setIndex(7);
            recMove[++ cnt] = new ChessBoardLocation(dest.getColor(), dest.getIndex());
        }
        else if(dest.getColor() == currentPlayer && dest.getIndex() < 12){//同色跳一下
            distance += 4;
            dest.setIndex(dest.getIndex() + 1);
            recMove[++ cnt] = new ChessBoardLocation(dest.getColor(), dest.getIndex());
        }
        if(grid[dest.getColor()][dest.getIndex()].getPiece() != null) {
            ChessPiece formerPiece = grid[dest.getColor()][dest.getIndex()].getPiece();
            if(formerPiece.player != currentPlayer) {
                battleChess.dest = dest;
                battleChess.bef = src;
            }
            else {
                ChessPiece now = removeChessPieceAt(src);
                if(grid[dest.getColor()][dest.getIndex()].getPiece() != null) {
                    int cnt1 = formerPiece.state;
                    removeChessPieceAt(dest);
                    CBC.setChessAtGrid(dest, CBC.BOARD_COLORS[currentPlayer], now.state + cnt1);
                    grid[dest.getColor()][dest.getIndex()].setPiece(new ChessPiece(currentPlayer, now.state + cnt1));
                }
                else setChessPieceAt(dest, now);
            }
        }
        else setChessPieceAt(dest, removeChessPieceAt(src));

        if(dest.getIndex() == 18) {//到终点
            hasArrived = true;
            ChessPiece piece = getGridAt(dest).getPiece();
            int player = piece.getPlayer();
            GameController.winPlane[player] += piece.state;
            for(int t = 1; t <= piece.state; t ++) {
                int id = 0;
                for (int i = 0; i < 4; i++)
                    if (GameController.rec[currentPlayer][i] == 1) {
                        id = i; GameController.rec[currentPlayer][i] = 0;
                        break;
                    }
                ChessBoardLocation start = new ChessBoardLocation(player, id + 19);
                removeChessPieceAt(dest);
                CBC.setChessAtGrid(start, CBC.BOARD_COLORS[player], -1);
                grid[player][start.getIndex()].setPiece(new ChessPiece(player, -1));
            }
        }
        StartingFrame.mainFrame.shadeComponent.state = 0;
        for(int i = 1; i <= cnt; i ++) {
            int player = recMove[i].getColor(), index = recMove[i].getIndex();
            //System.out.println("%%%%recPos: " + player + " " + index);
            CBC.gridComponents[player][index].step = 1;
            CBC.gridComponents[player][index].timer.start();
        }
        return battleChess;
    }


    //move是给定起始点和移动步数
    public ChessBoardLocation checkChessPiecePVE(ChessBoardLocation src, int steps, int currentPlayer) {
        distance = steps;
        ChessBoardLocation dest = src;
        int num = 1;
        for (int i = 0; i < steps; i++) {//记录路径
            dest = nextLocation(dest, currentPlayer, num);
            if(dest.getIndex() == 18) num = -1;
            if(dest.getIndex() == 12) num = 1;
        }

        return dest;
    }

    public static ChessBoardLocation nextLocation(ChessBoardLocation location, int currentPlayer, int num) {
        int col = location.getColor(), index = location.getIndex(), coln = (col + 1) % 4;
        int bIndex = location.getBoardIndex();
        int ind = ChessBoardLocation.getBoardIndexInv((bIndex + num) % 52);
        if(num == -1 && col == currentPlayer && index == 13) {
            if (currentPlayer == 0) {
                ind = ChessBoardLocation.getBoardIndexInv(49);
            } else if (currentPlayer == 1) {
                ind = ChessBoardLocation.getBoardIndexInv(10);
            } else if (currentPlayer == 2) {
                ind = ChessBoardLocation.getBoardIndexInv(23);
            } else if (currentPlayer == 3) {
                ind = ChessBoardLocation.getBoardIndexInv(36);
            }
            coln = col;
        }
        else if (bIndex > 51) {
            if(index == 23) {
                coln = (col - 1) % 4; if(coln < 0) coln += 4;
                ind = 3;
            }
            else {
                coln = col;
                ind = ChessBoardLocation.getBoardIndexInv((bIndex + num));
            }
        }
        if(col == currentPlayer && location.getIndex() == 12) {
            coln = col;
            if (currentPlayer == 0) {
                ind = ChessBoardLocation.getBoardIndexInv(52);
            } else if (currentPlayer == 1) {
                ind = ChessBoardLocation.getBoardIndexInv(63);
            } else if (currentPlayer == 2) {
                ind = ChessBoardLocation.getBoardIndexInv(74);
            } else if (currentPlayer == 3) {
                ind = ChessBoardLocation.getBoardIndexInv(85);
            }
        }
        return new ChessBoardLocation(coln, ind);
    }

    @Override
    public void registerListener(ChessBoardListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(ChessBoardListener listener) {
        listenerList.remove(listener);
    }

    public void newChess(ChessBoardComponent CBC, ChessBoardLocation CBL, int player, int state) {
        int col = CBL.getColor();
        int ind = CBL.getIndex();
        removeChessPieceAt(CBL);
        ChessPiece piece = new ChessPiece(player, state);
        setChessPieceAt(CBL, piece);
    }

}

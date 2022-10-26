package xyz.chengzi.aeroplanechess.controller;

import javazoom.jl.decoder.JavaLayerException;
import xyz.chengzi.aeroplanechess.Frame2.BattleChess;
import xyz.chengzi.aeroplanechess.Frame2.BattleFrame;
import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.Winner.WinnerProperty;
import xyz.chengzi.aeroplanechess.listener.ChessPieceListener;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;
import xyz.chengzi.aeroplanechess.listener.InputListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;
import xyz.chengzi.aeroplanechess.util.RandomUtil;
import xyz.chengzi.aeroplanechess.view.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameController implements ChessPieceListener, InputListener, Listenable<GameStateListener> {
    public final List<GameStateListener> listenerList = new ArrayList<>();
    private final ChessBoardComponent view;
    private final ChessBoard model;

    public Integer rolledNumber1;
    public Integer rolledNumber2;
    public Integer totalNumber;
    public int currentPlayer, totPlayers;
    public WinnerProperty endGame = new WinnerProperty();
    public int endGame1 = 0;//endGame是标记游戏有没有结束
    public static int[] outPlane = new int[4], winPlane = new int[4];
    //outPlane指的是一个玩家出门的棋子数, winPlane指的是玩家成功的棋子数
    public static int[][] rec = new int[4][4];
    //rec[i][j]表示的是第i个玩家的第j个家里的位置有没有飞机，0有，1没有
    public String status = null;
    public int realPlayer = 0;

    public GameController(ChessBoardComponent chessBoardComponent, ChessBoard chessBoard, int totPlayers) {
        this.view = chessBoardComponent;
        this.model = chessBoard;
        this.totPlayers = totPlayers;
        endGame.set(0);

        view.registerListener(this);//此为监听器，不用动，不用改
        model.registerListener(view);

        ChessBoardMouseListener chessBoardMouseListener = new ChessBoardMouseListener();
        this.view.addMouseMotionListener(chessBoardMouseListener);

       /* MouseAdapter adapter = ...
        JFrame frame = ...
        frame.addMouseListener(adapter);
        frame.addMouseMotionListener(adapter);*/
    }

    public ChessBoardComponent getView() {
        return view;
    }

    public ChessBoard getModel() {
        return model;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void initializeGame(int x, int t) {
        totPlayers = x;
        realPlayer = t;
        model.placeInitialPieces(this.view, this.totPlayers);
        rolledNumber1 = null;
        rolledNumber2 = null;
        totalNumber = null;
        currentPlayer = 0;
        endGame1 = 0;
        for(int i = 0; i < 4; i ++) winPlane[i] = 0;
        for(int i = 0; i < 4; i ++) outPlane[i] = 0;
        for(int i = 0; i < 4; i ++)
            for(int j = 0; j < 4; j ++)
                rec[i][j] = 0;
        listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
    }

    public int rollDice1() {
        if (rolledNumber1 == null) {
            return rolledNumber1 = RandomUtil.nextInt(1, 6);
        } else {
            return -1;
        }
    }
    public int rollDice2() {
        if (rolledNumber2 == null) {
            return rolledNumber2 = RandomUtil.nextInt(1, 6);
        } else {
            return -1;
        }
    }


    public int getNumber1() {
        if(rolledNumber1 == null) return -1;
        else return rolledNumber1;
    }
    public int getNumber2() {
        if(rolledNumber2 == null) return -1;
        else return rolledNumber2;
    }

    public int manualRollDice(int x) {
        if(rolledNumber1 == null) {
            rolledNumber1 = x;
            rolledNumber2 = -1;
            totalNumber = x;
            return x;
        }
        else return -1;
    }

    public int nextPlayer() {
        rolledNumber1 = null;
        rolledNumber2 = null;
        totalNumber = null;
        int steps;
        ChessBoardLocation location;
        currentPlayer = (currentPlayer + 1) % totPlayers;
        return  currentPlayer;
    }

    public int plusNumber(int num1, int num2){
        if(num2 == -1) return  totalNumber = num1;
        return totalNumber = num1 + num2;
    }
    public int minusNumber(int num1, int num2){
        if(num2 == -1) return  totalNumber = num1;
        if(num1 == num2) return -1;
        return totalNumber = Math.abs(num1 - num2);
    }
    public int multiplyNumber(int num1, int num2){
        if(num2 == -1) return  totalNumber = num1;
        if(num1 * num2 > 12) return -1;
        else return totalNumber = num1 * num2;
    }
    public int divideNumber(int num1, int num2){
        if(num2 == -1) return  totalNumber = num1;
        if(num1 > num2){
            if(num1 % num2 == 0){
                return totalNumber = num1 / num2;
            }
            else return -1;
        }
        if(num1 <= num2){
            if(num2 % num1 == 0){
                return totalNumber = num2 / num1;
            }
            else return -1;
        }
        else return 1;
    }

    int state = 0;
    Music music = new Music("src/music/初见雪.mp3");

    @Override
    public void onPlayerEnteredChessPiece(ChessComponent component) {
        component.entered = 1;
        component.repaint();
    }

    @Override
    public void onPlayerLeftChessPiece(ChessComponent component) {
        component.entered = 0;
        component.repaint();
    }

    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
        boolean really = model.getChessPieceAt(location) != null;
        System.out.println("clicked " + location.getColor() + "," + location.getIndex() + "," + location.getBoardIndex() + " " + really);
    }

    public int cal(int x) {
        if(rolledNumber1 == null) return -1;
        if (StartingFrame.mainFrame.Op[0].isSelected()) {
            plusNumber(getNumber1(), getNumber2());
        } else if (StartingFrame.mainFrame.Op[1].isSelected()) {
            int t = minusNumber(getNumber1(), getNumber2());
            if(t == -1) {
                if(x != 0) JOptionPane.showMessageDialog(StartingFrame.mainFrame, "You can't move 0 steps!");
                return -1;
            }
        } else if (StartingFrame.mainFrame.Op[2].isSelected()) {
            if(multiplyNumber(getNumber1(), getNumber2()) != -1){
                multiplyNumber(getNumber1(), getNumber2());
            } else {
                if(x != 0) JOptionPane.showMessageDialog(StartingFrame.mainFrame, "Your steps cannot be more than 12!");
                return -1;
            }
        } else {
            if (divideNumber(getNumber1(), getNumber2()) != -1) {
                divideNumber(getNumber1(), getNumber2());
            } else {
                if(x != 0) JOptionPane.showMessageDialog(StartingFrame.mainFrame, "It is not an integer!");
                return -1;
            }
        }
        return 0;
    }

    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component, boolean isPVE) throws FileNotFoundException, JavaLayerException {
        ChessPiece piece = model.getChessPieceAt(location);
        if (rolledNumber1 == null) return;
        if (piece.state == -1) return;
        if (piece.getPlayer() != currentPlayer) return;

        if (location.getIndex() > 18 && location.getIndex() != 23) {
            if (rolledNumber1 != 6 && rolledNumber2 != 6) {
                if (realPlayer != 1 || currentPlayer == 0)
                    JOptionPane.showMessageDialog(StartingFrame.mainFrame, "You must roll a six to send a plane out!");
                endRound();
                return;
            }
            ChessBoardLocation nxt = new ChessBoardLocation(location.getColor(), 23);
            model.flyChessPiece(this.view, location, nxt, currentPlayer);
            outPlane[currentPlayer]++;
            rec[currentPlayer][location.getIndex() - 19] = 1;
            endRound();
            return;
        }
        int x = 0;
        if(!isPVE) x = cal(1);
        if(totalNumber == null || x == -1) return;

        StartingFrame.mainFrame.starComponent.state = totalNumber;
        StartingFrame.mainFrame.starComponent.repaint();

        BattleChess battleChess;
        battleChess = model.moveChessPiece(location, totalNumber, currentPlayer, this.view);
        if(battleChess.bef != null) {
            GoBattle(battleChess);
        }
        if (winPlane[currentPlayer] == 4) {
            endGame.set(currentPlayer + 1);
            endGame1 = 1;
        }
        endRound();
    }

    public void endRound() {
        listenerList.forEach(listener -> listener.onPlayerEndRound(currentPlayer));
        nextPlayer();
        listenerList.forEach(listener -> listener.onPlayerStartRound(currentPlayer));
        if(endGame1 != 1 && currentPlayer == 1 && realPlayer == 1) {
            Working work = new Working(this, StartingFrame.mainFrame.Dice, currentPlayer);
            work.Work2(); // start automatic player round
        }
        return;
    }

    public void GoBattle(BattleChess battleChess) throws FileNotFoundException, JavaLayerException {
        ChessPiece piece1 = model.getChessPieceAt(battleChess.bef);
        ChessPiece piece2 = model.getChessPieceAt(battleChess.dest);
        int rec = 0;
        if(realPlayer == 0 && MusicComponent.musicPlay.state == 1) {
            MusicComponent.musicPlay.musicStop();
            MusicComponent.musicBattle.play();
            rec = 1;
        }
        BattleFrame battleFrame = new BattleFrame(view, model, piece1, piece2, battleChess, "Battle Time!", rec);
        if(realPlayer == 0) battleFrame.setVisible(true);
    }

    @Override
    public void registerListener(GameStateListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(GameStateListener listener) {
        listenerList.remove(listener);
    }
}

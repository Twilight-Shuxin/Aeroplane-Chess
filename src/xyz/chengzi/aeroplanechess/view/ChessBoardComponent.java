package xyz.chengzi.aeroplanechess.view;

import javazoom.jl.decoder.JavaLayerException;
import xyz.chengzi.aeroplanechess.listener.ChessBoardListener;
import xyz.chengzi.aeroplanechess.listener.InputListener;
import xyz.chengzi.aeroplanechess.listener.Listenable;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardComponent extends JComponent implements Listenable<InputListener>, ChessBoardListener {
    public static final Color[] BOARD_COLORS = {Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};
    public static final Color[] PIECE_COLORS = {Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN};

    private final List<InputListener> listenerList = new ArrayList<>();
    public final SquareComponent[][] gridComponents;
    public ChessComponent[][] Map = new ChessComponent[4][30];
    private final int dimension, endDimension;
    private final int gridSize;
    private final int slide = 90, slide2 = 50;
    public int[][] placeX = new int[5][40];
    public int[][] placeY = new int[5][40];

    public ChessBoardComponent(int size, int dimension, int endDimension) {//size是棋盘的size
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLayout(null); // Use absolute layout
        setSize(size + slide * 2, size + slide2 * 2);

        this.gridComponents = new SquareComponent[4][dimension + endDimension + 200];//4个player，dimension是边缘的格子，enddimension是到终点的格子
        this.dimension = dimension;
        this.endDimension = endDimension;
        this.gridSize = size / (dimension + 1);
        System.out.println("??" + gridSize);
        initGridComponents();
    }

    private int gridLocation(int player, int index) {//index是该颜色的第几个格子
        // FIXME: Calculate proper location for each grid
        int boardIndex = (1 + 13 * player + 4 * index) % (4 * dimension);//超过4*13=52以后取余数，又从横向第一条边开始
        int x, y;
      //  System.out.printf("player%d ", boardIndex); //System.out.println();
        if (boardIndex < dimension) {//四条边的上面第一条边
            if (boardIndex < 4) {
                x = boardIndex * gridSize;
                y = 4 * gridSize;
            } else if (boardIndex < 8) {
                x = 3 * gridSize;
                y = (7 - boardIndex) * gridSize;
            } else {
                x = (boardIndex - 4) * gridSize;
                y = 0;
            }
        } else if (boardIndex < 2 * dimension) {//右边第二条边
            if (boardIndex < 17) {
                x = 9 * gridSize;
                y = (boardIndex - dimension) * gridSize;
            } else if (boardIndex < 21) {
                x = (boardIndex - 7) * gridSize;
                y = 3 * gridSize;
            } else {
                x = 13 * gridSize;
                y = (boardIndex - 17) * gridSize;
            }
        } else if (boardIndex < 3 * dimension) {//下面第三条边
            if (boardIndex < 30) {
                x = (39 - boardIndex) * gridSize;
                y = 9 * gridSize;
            } else if (boardIndex < 34) {
                x = 10 * gridSize;
                y = (boardIndex - 20) * gridSize;
            } else {
                x = (43 - boardIndex) * gridSize;
                y = 13 * gridSize;
            }

        } else {//左边第四条边
            if (boardIndex < 43) {
                x = 4 * gridSize;
                y = (52 - boardIndex) * gridSize;
            } else if (boardIndex < 47) {
                x = (46 - boardIndex) * gridSize;
                y = 10 * gridSize;
            } else {
                x = 0;
                y = (56 - boardIndex) * gridSize;
            }
        }
        x += slide; y += slide2;
        return x << 16 | y;//将x数值左移16位，对y取或
    }

    private int endGridLocation(int player, int index) {
        // FIXME: Calculate proper location for each end grid
        int beforeEndGridLocation = gridLocation(player, dimension - 1);
        int x = beforeEndGridLocation >> 16, y = beforeEndGridLocation & 0xffff;
        if (player == 1) {//蓝色
            y += (index + 1) * gridSize;
        } else if (player == 0) {//黄色
            x += (index + 1) * gridSize;
        } else if (player == 3) {//绿色
            y -= (index + 1) * gridSize;
        } else {//红色
            x -= (index + 1) * gridSize;
        }
        return x << 16 | y;
    }
    private int airPortGridLocation(int player, int index) {
        int beforeLocation = gridLocation(player, 0);
        int x = beforeLocation >> 16, y = beforeLocation & 0xffff;
        if(player == 0) {
            y -= gridSize + 80;
            x += 27;
            if(index > 1) y -= gridSize;
            if((index % 2) != 0) x -= gridSize;
        }
        else if(player == 1) {
            x += 135;
            if((index % 2) != 0) x += gridSize;
            y -= 27;
            if(index >= 2) y += gridSize;
        }
        else if(player == 2) {
            y += gridSize + 82;
            x -= 27;
            if((index % 2) != 0) x += gridSize;
            if(index >= 2) y += gridSize;
        }
        else {
            x -= 135;
            y -= 25;
            if(index >= 2) y += gridSize;
            if((index % 2) != 0) x -= gridSize;
        }
        return x << 16 | y;
    }

    private int startGridLocation(int player, int gridLocation) {
        int x = gridLocation >> 16, y = gridLocation & 0xffff;
        if(player == 0) y -= gridSize;
        else if(player == 1) x += gridSize;
        else if(player == 2) y += gridSize;
        else x -= gridSize;
        return x << 16 | y;
    }

    private void initGridComponents() {//画格子
        for (int player = 0; player < 4; player++) {
            for (int index = 0; index < dimension; index++) {
                int gridLocation = gridLocation(player, index);
                gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
                gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
                placeX[player][index] = gridLocation >> 16;
                placeY[player][index] = gridLocation & 0xffff;
                add(gridComponents[player][index]);
            }
            for (int index = dimension; index < dimension + endDimension; index++) {
                int gridLocation = endGridLocation(player, index - dimension);
                gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
                gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
                placeX[player][index] = gridLocation >> 16;
                placeY[player][index] = gridLocation & 0xffff;
                add(gridComponents[player][index]);
            }
        }
        for(int player = 0; player < 4; player ++) {
            for(int index = dimension + endDimension; index < dimension + endDimension + 4; index ++) {
                int gridLocation = airPortGridLocation(player, index - dimension - endDimension);
                gridComponents[player][index] = new SquareComponent(gridSize, BOARD_COLORS[player], player, index);
                gridComponents[player][index].setLocation(gridLocation >> 16, gridLocation & 0xffff);
                placeX[player][index] = gridLocation >> 16;
                placeY[player][index] = gridLocation & 0xffff;
                add(gridComponents[player][index]);
            }
        }
        for(int player = 0; player < 4; player ++) {
            int pl = ((player - 1) % 4); if(pl < 0) pl += 4;
            int gridLocation = gridLocation(pl, 3);
            int tot = dimension + endDimension + 4;
            gridLocation = startGridLocation(player, gridLocation);
            gridComponents[player][tot] = new SquareComponent(gridSize, BOARD_COLORS[player], player, tot);
            gridComponents[player][tot].setLocation(gridLocation >> 16, gridLocation & 0xffff);
            placeX[player][tot] = gridLocation >> 16;
            placeY[player][tot] = gridLocation & 0xffff;
            add(gridComponents[player][tot]);
        }
    }

    public SquareComponent getGridAt(ChessBoardLocation location) {
        return gridComponents[location.getColor()][location.getIndex()];
    }

    public void setChessAtGrid(ChessBoardLocation location, Color color, int state) {
        removeChessAtGrid(location);
        ChessComponent chessComponent = new ChessComponent(color, state);
        getGridAt(location).add(chessComponent);
        Map[location.getColor()][location.getIndex()] = chessComponent;
    }

    public void removeChessAtGrid(ChessBoardLocation location) {
        // Note: re-validation is required after remove / removeAll
        Map[location.getColor()][location.getIndex()] = null;
        getGridAt(location).removeAll();
        getGridAt(location).revalidate();
    }
    int tot = 0;

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
        //System.out.println("[clicked]");
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            //System.out.println("clicked" + " " + e.getX() + " " + e.getY() +  " " + (clickedComponent instanceof SquareComponent));
            if (clickedComponent instanceof SquareComponent) {
                SquareComponent square = (SquareComponent) clickedComponent;
                ChessBoardLocation location = new ChessBoardLocation(square.getPlayer(), square.getIndex());
                for (InputListener listener : listenerList) {
                    if (clickedComponent.getComponentCount() == 0) {
                        listener.onPlayerClickSquare(location, square);
                    } else {
                        try {
                            listener.onPlayerClickChessPiece(location, (ChessComponent) square.getComponent(0), false);
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (JavaLayerException javaLayerException) {
                            javaLayerException.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onChessPiecePlace(ChessBoardLocation location, ChessPiece piece) {
        setChessAtGrid(location, PIECE_COLORS[piece.getPlayer()], piece.state);
        repaint();
    }

    @Override
    public void onChessPieceRemove(ChessBoardLocation location) {
        removeChessAtGrid(location);
        repaint();
    }

    @Override
    public void onChessBoardReload(ChessBoard board) {
        for (int color = 0; color < 4; color++) {
            for (int index = 0; index < board.getDimension(); index++) {
                ChessBoardLocation location = new ChessBoardLocation(color, index);
                ChessPiece piece = board.getChessPieceAt(location);
                if (piece != null) {
                    setChessAtGrid(location, PIECE_COLORS[piece.getPlayer()], piece.state);
                } else {
                    removeChessAtGrid(location);
                }
            }
        }
        repaint();
    }

    @Override
    public void registerListener(InputListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(InputListener listener) {
        listenerList.remove(listener);
    }
}

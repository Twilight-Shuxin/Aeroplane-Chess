package xyz.chengzi.aeroplanechess.listener;

import javazoom.jl.decoder.JavaLayerException;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.view.ChessComponent;
import xyz.chengzi.aeroplanechess.view.SquareComponent;

import java.io.FileNotFoundException;

public interface InputListener extends Listener {
    void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component);
    void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component, boolean isPVE) throws FileNotFoundException, JavaLayerException;
}

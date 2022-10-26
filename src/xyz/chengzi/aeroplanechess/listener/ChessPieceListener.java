package xyz.chengzi.aeroplanechess.listener;

import xyz.chengzi.aeroplanechess.view.ChessComponent;

public interface ChessPieceListener extends Listener {
	void onPlayerEnteredChessPiece(ChessComponent component);
	void onPlayerLeftChessPiece(ChessComponent component);
}

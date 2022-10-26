package xyz.chengzi.aeroplanechess.Frame2;

import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;

public class BattleChess {
	public ChessBoardLocation bef;
	public ChessBoardLocation dest;

	public BattleChess(ChessBoardLocation bef, ChessBoardLocation dest) {
		this.bef = bef;
		this.dest = dest;
	}
}

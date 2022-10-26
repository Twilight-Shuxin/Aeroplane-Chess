package xyz.chengzi.aeroplanechess.GameIO;

import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static xyz.chengzi.aeroplanechess.view.GameFrame.PLAYER_NAMES;

public class GameIO {
	public static void Save(GameController controller, int x) throws IOException {
		File f = new File("saved" + x + ".txt");
		f.createNewFile();
		FileWriter fileWriter = new FileWriter(f.getAbsoluteFile());
		NewBufferedWriter bW = new NewBufferedWriter(fileWriter);
		bW.write(controller.realPlayer);
		bW.write(controller.totPlayers);

		if(controller.realPlayer == 0) bW.write(controller.currentPlayer);
		else bW.write(0);
		ChessBoard CB = controller.getModel();
		for(int i = 0; i < controller.totPlayers; i ++)
			bW.write(controller.outPlane[i]);
		for(int i = 0; i < controller.totPlayers; i ++)
			bW.write(controller.winPlane[i]);
		for(int i = 0; i < controller.totPlayers; i ++)
			for(int j = 0; j < 4; j ++)
				bW.write(controller.rec[i][j]);

		for(int i = 0; i < 4; i ++)
			for(int j = 0; j < 24; j ++) {
				ChessPiece piece = CB.grid[i][j].getPiece();
				if(piece == null) bW.write(-1);
				else {
					System.out.println("****" + i + " " + j + " " + piece.state);
					bW.write(1);
					bW.write(piece.player);
					bW.write(piece.state);
				}
			}
		bW.close();
	}

	public static GameController Load(int x) throws FileNotFoundException {
		File f = new File("saved" + x + ".txt");
		if(f.exists() == false) return null;
		if(Check(x) == false) return null;
		Scanner scanner = new Scanner(new File("saved" + x + ".txt"));
		GameController controller = StartingFrame.mainFrame.controller;
		controller.realPlayer = scanner.nextInt();
		controller.totPlayers = scanner.nextInt();
		controller.currentPlayer = scanner.nextInt();
		controller.endGame1 = 0;
		controller.endGame.set(-1);
		for(int i = 0; i < 4; i ++)
			controller.outPlane[i] = controller.winPlane[i] = 0;
		for(int i = 0; i < controller.totPlayers; i ++)
			for(int j = 0; j < 4; j ++)
				controller.rec[i][j] = 0;

		for(int i = 0; i < controller.totPlayers; i ++)
			controller.outPlane[i] = scanner.nextInt();
		for(int i = 0; i < controller.totPlayers; i ++)
			controller.winPlane[i] = scanner.nextInt();
		for(int i = 0; i < controller.totPlayers; i ++)
			for(int j = 0; j < 4; j ++)
				controller.rec[i][j] = scanner.nextInt();
		for(int i = 0; i < 4; i ++)
			for(int j = 0; j < 24; j ++) {
				ChessBoardLocation CBL = new ChessBoardLocation(i, j);
				controller.getModel().removeChessPieceAt(CBL);
				controller.getModel().grid[i][j].setPiece(null);
			}
		for(int i = 0; i < 4; i ++)
			for(int j = 0; j < 24; j ++) {
				int chessExt = scanner.nextInt();
				ChessBoardLocation CBL = new ChessBoardLocation(i, j);
				if(chessExt == 1) {
					int player = scanner.nextInt();
					int state = scanner.nextInt();
					//System.out.println("^^^^^^" + player + " " + state);
					ChessPiece CP = new ChessPiece(player, state);
					controller.getModel().setChessPieceAt(CBL, CP);
					controller.getModel().grid[i][j].setPiece(CP);
				}
			}
		StartingFrame.mainFrame.statusLabel.setText(String.format("<html>[%s] : <br> Please roll the dice</html>", PLAYER_NAMES[controller.currentPlayer]));
		controller.rolledNumber1 = null;
		controller.rolledNumber2 = null;
		StartingFrame.mainFrame.Dice.state = 0;
		//System.out.println("%%%%%" + controller.outPlane[1]);
		return controller;
	}

	public static boolean Check(int x) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("saved" + x + ".txt"));
		if(scanner.hasNextLine() == false) return false;
		int realPlayers = scanner.nextInt();
		if(scanner.hasNextLine() == false) return false;
		int totPlayers = scanner.nextInt();
		if(totPlayers > 4) return false;
		if(scanner.hasNextLine() == false) return false;
		int currentPlayers = scanner.nextInt();
		if(currentPlayers >= 4) return false;
		int[] outPlane = new int[4], winPlane = new int[4];
		for(int i = 0; i < 4; i ++) {
			outPlane[i] = 0;
			winPlane[i] = 0;
		}
		for(int i = 0; i < totPlayers; i ++) {
			if(scanner.hasNextLine() == false) return false;
			outPlane[i] = scanner.nextInt();
			if(outPlane[i] > 4) return false;
		}
		for(int i = 0; i < totPlayers; i ++) {
			if(scanner.hasNextLine() == false) return false;
			winPlane[i] = scanner.nextInt();
			if(winPlane[i] >= 4) return false;
		}
		for(int i = 0; i < totPlayers; i ++) {
			int sum = 0;
			for (int j = 0; j < 4; j++) {
				if(scanner.hasNextLine() == false) return false;
				int t = scanner.nextInt();
				if (t == 1) sum += 1;
			}
			if(sum != outPlane[i] - winPlane[i]) return false;
		}

		int[] cnt1 = new int[4];
		int[] cnt2 = new int[4];
		int[] tot = new int[4];
		for(int i = 0; i < 4; i ++) {
			cnt1[i] = 0;
			cnt2[i] = 0;
			tot[i] = 0;
		}
		for(int i = 0; i < 4; i ++)
			for(int j = 0; j < 24; j ++) {
				if(scanner.hasNextLine() == false) return false;
				int op = scanner.nextInt();
				if(op == -1) continue;
				if(op != 1) return false;
				if(scanner.hasNextLine() == false) return false;
				int player = scanner.nextInt();
				if(scanner.hasNextLine() == false) return false;
				int state = scanner.nextInt();
				if(player >= totPlayers) return false;
				if(state >= 1) {
					if(j <= 18 || j == 23) cnt1[player] += state;
					tot[player] += state;
				}
				else {
					cnt2[player] += 1;
					tot[player] ++;
				}
			}
		for(int i = 0; i < totPlayers; i ++) if (cnt1[i] != outPlane[i] - winPlane[i]) return false;
		for(int i = 0; i < totPlayers; i ++) if(cnt2[i] != winPlane[i]) return false;
		for(int i = 0; i < totPlayers; i ++) if(tot[i] != 4) return false;
		return true;
	}
}

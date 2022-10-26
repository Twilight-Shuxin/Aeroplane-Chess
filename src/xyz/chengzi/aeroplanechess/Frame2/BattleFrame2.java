package xyz.chengzi.aeroplanechess.Frame2;

import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;
import xyz.chengzi.aeroplanechess.util.RandomUtil;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;
import xyz.chengzi.aeroplanechess.view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BattleFrame2 extends JFrame {
	private static final String[] PLAYER_NAMES = {"Yellow", "Blue", "Green", "Red"};
	Integer number1 = null, number2 = null, endBattle = 0;
	int[] num = new int[2];

	public BattleFrame2 (ChessBoardComponent CBC, ChessBoard CB, ChessPiece piece1, ChessPiece piece2, BattleChess battleChess, String title)
	{
		super(title);
		int[] player = {piece1.player, piece2.player};
		int[] cnt = new int[2];
		num[0] = piece1.state;
		num[1] = piece2.state;

		GameFrame back = StartingFrame.mainFrame;
		BattleFrame2 This = this;
		int width = 800, height = 500;
		setSize(width, height);
		Image icon = Toolkit.getDefaultToolkit().getImage("src/pic/Icon.png");
		setLocationRelativeTo(back); // Center the window
		//back.setEnabled(false);
		setLayout(null);
		setIconImage(icon);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		int side = 250, up = 70;
		JLabel[] statusLabel = new JLabel[3];
		Font labelFont = new Font("Georgia", Font.BOLD, 26);
		for (int i = 0; i < 3; i++) statusLabel[i] = new JLabel();
		statusLabel[0].setText(PLAYER_NAMES[player[0]] + " BATTLE " + PLAYER_NAMES[player[1]] + "!");
		statusLabel[1].setText(PLAYER_NAMES[player[0]] + ": Please roll dice");
		statusLabel[2].setText("Ha");
		for (int i = 0; i < 3; i++) {
			statusLabel[i].setFont(labelFont);
			statusLabel[i].setSize(700, 120);
		}
		statusLabel[0].setLocation(side, up);
		add(statusLabel[0]);

		statusLabel[1].setLocation(side, up + 150);
		add(statusLabel[1]);

		statusLabel[2].setLocation(side, up + 200);
		add(statusLabel[2]);
		statusLabel[2].setVisible(false);

		JButton roll = new JButton("Roll Dice");
		Font rollFont = new Font("Georgia", Font.BOLD, 35);
		roll.setFont(rollFont);
		roll.setLocation(side, up + 110);
		roll.setSize(250, 50);
		roll.setFocusPainted(false);

		System.out.println("===" + number1 + " " + number2);
		roll.addActionListener((e) -> {
			if (endBattle == 0) {
				if (number1 == null) {
					statusLabel[2].setVisible(false);
					number1 = RandomUtil.nextInt(1, 6);
					System.out.println("???");
					statusLabel[1].setText(PLAYER_NAMES[player[0]] + " rolled a  " + number1);
					statusLabel[0].setText(PLAYER_NAMES[player[1]] + ": Please roll dice");
				} else if (number2 == null) {
					while (number2 == null || number1 == number2) number2 = RandomUtil.nextInt(1, 6);
					statusLabel[1].setText(PLAYER_NAMES[player[1]] + " rolled a  " + number2);
					statusLabel[1].setVisible(true);
					int loser = number1 < number2 ? 0 : 1;
					int winner = loser == 0 ? 1 : 0;
					System.out.println("@@@" + winner + " " + loser);
					num[loser]--;
					cnt[loser]++;
					statusLabel[2].setText(PLAYER_NAMES[loser] + " lost a plane.");
					statusLabel[2].setVisible(true);
					if (num[0] == 0 || num[1] == 0) {
						endBattle = 1;
						statusLabel[0].setText(PLAYER_NAMES[winner] + " takes over!");
						for (int pl = 0; pl < 2; pl++) {
							for (int t = 1; t <= cnt[pl]; t++) {
								int id = 0;
								for (int i = 0; i < 4; i++)
									if (GameController.rec[player[pl]][i] == 1) {
										id = i;
										GameController.rec[player[pl]][i] = 0;
										break;
									}
								ChessBoardLocation start = new ChessBoardLocation(player[pl], id + 19);
								CBC.setChessAtGrid(start, CBC.BOARD_COLORS[player[pl]], 1);
								CB.grid[player[pl]][start.getIndex()].setPiece(new ChessPiece(player[pl], 1));
							}
						}
						CB.removeChessPieceAt(battleChess.bef);
						CB.removeChessPieceAt(battleChess.dest);
						System.out.println("^^^^^" + player[winner] + " " + num[player[winner]] + " " + cnt[player[winner]]);
						System.out.println("#####" + player[loser] + " " + num[player[loser]] + " " + cnt[player[loser]]);
						CBC.setChessAtGrid(battleChess.dest, CBC.BOARD_COLORS[player[winner]], num[winner]);
						CB.grid[battleChess.dest.getColor()][battleChess.dest.getIndex()].setPiece(new ChessPiece(player[winner], num[player[winner]]));
					} else {
						statusLabel[0].setText(PLAYER_NAMES[player[0]] + ": Please roll dice");
						number1 = null;
						number2 = null;
					}
				}
			}
		});

		add(roll);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (endBattle == 0) {
					JOptionPane.showMessageDialog(This, "Battle has not finished!");
				} else {
					back.setEnabled(true);
					This.dispose();//关闭
				}
			}
		});

		ImageIcon startBackground = new ImageIcon("src/Pic2/battleBackground.jpg");
		JLabel label0 = new JLabel(startBackground);
		label0.setBounds(0, 0, width, height);
		add(label0);
		dispose();
	}
}

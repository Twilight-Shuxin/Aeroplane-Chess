package xyz.chengzi.aeroplanechess.Frame2;

import javazoom.jl.decoder.JavaLayerException;
import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.Winner.DiceRolling2;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.model.ChessBoardLocation;
import xyz.chengzi.aeroplanechess.model.ChessPiece;
import xyz.chengzi.aeroplanechess.util.RandomUtil;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;
import xyz.chengzi.aeroplanechess.view.GameFrame;
import xyz.chengzi.aeroplanechess.view.MusicComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

public class BattleFrame extends JFrame implements ActionListener {
	private static final String[] PLAYER_NAMES = {"Yellow", "Blue", "Green", "Red"};
	Integer number1 = null, number2 = null, endBattle = 0;
	ChessPiece piece1, piece2;
	int[] player = new int[2];//{piece1.player, piece2.player};
	int[] cnt = new int[2];
	int[] num = new int[2];
	ChessBoardComponent CBC;
	ChessBoard CB;
	BattleChess battleChess;
	JLabel[] statusLabel = new JLabel[2];
	Heart[][] heart = new Heart[2][4];
	int state;
	Timer timer = new Timer(1600, this);
	battlePlayerComponent[] battleIcon = new battlePlayerComponent[2];
	GameFrame back = StartingFrame.mainFrame;

	public BattleFrame (ChessBoardComponent CBC, ChessBoard CB, ChessPiece piece1, ChessPiece piece2, BattleChess battleChess, String title, int rec) {
		super(title);
		this.CBC = CBC; this.CB = CB;
		this.piece1 = piece1; this.piece2 = piece2;
		num[0] = piece1.state; num[1] = piece2.state;
		player[0] = piece1.player; player[1] = piece2.player;
		this.battleChess = battleChess;
		state = 0;
		BattleFrame This = this;
		int width = 1000, height = 600;
		setSize(width, height);
		Image icon = Toolkit.getDefaultToolkit().getImage("src/pic/Icon.png");
		setLocationRelativeTo(back); // Center the window
		back.setEnabled(false);
		setLayout(null);
		setIconImage(icon);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		int side = 145, up = 380;
		Font labelFont = new Font("Georgia", Font.BOLD, 21);
		for (int i = 0; i < 2; i++) statusLabel[i] = new JLabel();
		statusLabel[0].setText("Ready to roll!");
		statusLabel[1].setText("Waiting to strike back...");
		for (int i = 0; i < 2; i++) {
			statusLabel[i].setFont(labelFont);
			statusLabel[i].setSize(700, 120);
		}
		statusLabel[0].setLocation(side, up);
		add(statusLabel[0]);
		statusLabel[1].setLocation(side + 435, up);
		add(statusLabel[1]);

		JLabel[] nameLabel = new JLabel[2];
		int slidex = 155, slidey = 320, nameWidth = 430;

		for (int i = 0; i <= 1; i++) {
			ImageIcon nameTag = new ImageIcon("src/Pic2/nameTag" + (player[i] + 1) + ".png");
			nameLabel[i] = new JLabel(nameTag);
			nameLabel[i].setBounds(slidex + i * nameWidth, slidey, 244, 57);
			add(nameLabel[i]);
		}

		slidex = 118;
		slidey = 92;
		int iconWidth = 437;
		for (int i = 0; i <= 1; i++) {
			battleIcon[i] = new battlePlayerComponent(player[i], i);
			battleIcon[i].setLocation(slidex + iconWidth * i, slidey);
			add(battleIcon[i]);
		}

		for (int i = 0; i <= 1; i++) {
			for (int j = 0; j < num[i]; j++) {
				heart[i][j] = new Heart();
				heart[i][j].setLocation(140 + i * 450 + j * 45, 370);
				add(heart[i][j]);
			}
		}
		DiceRolling2 Dice = new DiceRolling2(90, 80, this);
		Dice.setLocation(448, 247);
		add(Dice);

		JButton roll = new JButton();
		roll.setOpaque(false);
		roll.setContentAreaFilled(false);
		roll.setLocation(430, 346);
		roll.setBorder(null);
		roll.setMargin(new Insets(0, 0, 0, 0));
		roll.setIcon(new ImageIcon("src/Pic2/battleRoll0.png"));
		roll.setRolloverIcon(new ImageIcon("src/Pic2/battleRoll1.png"));
		roll.setSize(120, 40);
		roll.setFocusPainted(false);
		if(StartingFrame.mainFrame.controller.realPlayer == 1) this.setEnabled(false);

		roll.addActionListener((e) -> {
			if (endBattle == 0) {
				if (number1 == null) {
					number1 = RandomUtil.nextInt(1, 6);
					Dice.state = 1;
					Dice.num = number1;
					statusLabel[0].setText("Rolled a (" + number1 + ")");
				} else if (number2 == null) {
					while (number2 == null || number1 == number2) number2 = RandomUtil.nextInt(1, 6);
					statusLabel[1].setText("Rolled a (" + number2 + ")");
					Dice.state = 1;
					Dice.num = number2;
					timer.start();
				}
			}
		});

		add(roll);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (endBattle == 0) {
					JOptionPane.showMessageDialog(This, "Battle has not finished!");
				} else {
					if(rec == 1) {
						try {
							MusicComponent.musicPlay.play();
						} catch (FileNotFoundException fileNotFoundException) {
							fileNotFoundException.printStackTrace();
						} catch (JavaLayerException javaLayerException) {
							javaLayerException.printStackTrace();
						}
						MusicComponent.musicBattle.musicStop();
					}
					back.setEnabled(true);
					This.dispose();//关闭
				}
			}
		});

		ImageIcon startBackground = new ImageIcon("src/Pic2/battleBackground.jpg");
		JLabel label0 = new JLabel(startBackground);
		label0.setBounds(0, 0, width, height);
		add(label0);
		if(StartingFrame.mainFrame.controller.realPlayer == 1) dispose();

		if(StartingFrame.mainFrame.controller.realPlayer == 1) {
			int winner = 0;
			int loser = 1;
			int left = num[winner];
			cnt[winner] = 0;
			cnt[loser] = num[loser]; num[loser] = 0;
			EndBattle(winner, loser);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(number1 == null || number2 == null) {
			//nothing happens...
		}
		else if(state == 0) {
			this.setEnabled(false);
			state = 1;
			int loser = number1 < number2 ? 0 : 1;
			int winner = loser == 0 ? 1 : 0;
			num[loser]--;
			cnt[loser]++;
			heart[loser][cnt[loser] - 1].state = 1;

			if (num[0] == 0 || num[1] == 0) {
				EndBattle(winner, loser);
			} else {
				statusLabel[winner].setText("Wins a round!");
				statusLabel[loser].setText("A plane back home...");
			}
		}
		else if(state == 1) {
			this.setEnabled(true);
			statusLabel[0].setText("Ready to roll!");
			statusLabel[1].setText("Waiting to strike back...");
			state = 0;
			number1 = null;
			number2 = null;
			timer.stop();
		}
	}

	public void EndBattle (int winner, int loser) {
		endBattle = 1;
		state = 2;
		battleIcon[loser].state = 1;
		battleIcon[winner].state = 2;
		repaint();
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
				StartingFrame.mainFrame.controller.outPlane[player[pl]] -= 1;
				CBC.setChessAtGrid(start, CBC.BOARD_COLORS[player[pl]], 1);
				CB.grid[player[pl]][start.getIndex()].setPiece(new ChessPiece(player[pl], 1));
			}
		}
		CB.removeChessPieceAt(battleChess.bef);
		CB.removeChessPieceAt(battleChess.dest);
		CBC.setChessAtGrid(battleChess.dest, CBC.BOARD_COLORS[player[winner]], num[winner]);
		CB.grid[battleChess.dest.getColor()][battleChess.dest.getIndex()].setPiece(new ChessPiece(player[winner], num[winner]));
		statusLabel[winner].setText("Takes over!");
		statusLabel[loser].setText("All back home...");
		this.setEnabled(true);
		number1 = null;
		number2 = null;
		if(StartingFrame.mainFrame.controller.realPlayer == 1) {
			this.dispose();
			back.setEnabled(true);
		}
	}
}

package xyz.chengzi.aeroplanechess;

import javazoom.jl.decoder.JavaLayerException;
import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.model.ChessBoard;
import xyz.chengzi.aeroplanechess.view.ChessBoardComponent;
import xyz.chengzi.aeroplanechess.view.GameFrame;
import xyz.chengzi.aeroplanechess.view.MusicComponent;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class StartingFrame extends JFrame {
	public static GameFrame mainFrame;
	public static ChoosePlayerComponent choosePlayerComponent = new ChoosePlayerComponent();

	StartingFrame(String title) throws FileNotFoundException, JavaLayerException {
		super(title);
		setSize(1500, 900);
		Image icon = Toolkit.getDefaultToolkit().getImage("src/pic/Icon.png");  // 图片的具体位置
		setLocationRelativeTo(null); // Center the window
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);//点×关闭
		setIconImage(icon);

		choosePlayerComponent.setLocation(540, 468);
		add(choosePlayerComponent);

		for(int i = 1; i <= 4; i ++) {
			int finalI = i;
			choosePlayerComponent.Players[i].addActionListener((e) -> {
				choosePlayerComponent.totPlayers = finalI;
				if(finalI == 1) choosePlayerComponent.totPlayers = 4;
				ChessBoardComponent chessBoardComponent = new ChessBoardComponent(760, 13, 6);
				ChessBoard chessBoard = new ChessBoard(13, 6, chessBoardComponent);
				GameController controller = new GameController(chessBoardComponent, chessBoard, choosePlayerComponent.totPlayers);

				mainFrame = new GameFrame(controller);
				mainFrame.add(chessBoardComponent);
				mainFrame.setLocationRelativeTo(this);

				JLabel[] start = new JLabel[5];
				// for(int i = 0; i < 4; i ++) {
				for(int j = 0; j < 4; j ++) {
					ImageIcon startPic = new ImageIcon("src/pic/start" + j + ".png");
					start[j] = new JLabel(startPic);
				}
				start[0].setBounds(90, 50, 160, 160);
				start[1].setBounds(685, 50, 160, 160);
				start[2].setBounds(685, 645, 160, 160);
				start[3].setBounds(90, 645, 160, 160);
				for(int j = 0; j < 4; j ++)
					mainFrame.add(start[j]);

				JLabel star = new JLabel(new ImageIcon("src/Pic2/star.png"));
				star.setBounds(280, 220, 400, 400);
				//mainFrame.add(star);

				ImageIcon image = new ImageIcon("src/Pic2/backGround72.jpg");
				JLabel label=new JLabel(image);
				label.setBounds(0,0,1500,900);
				mainFrame.add(label);

				mainFrame.setIconImage(icon);
				int realPlayers = 0;
				if(finalI == 1) realPlayers = 1;
				controller.initializeGame(choosePlayerComponent.totPlayers, realPlayers);
				mainFrame.setVisible(true);
				dispose();
				MusicComponent.musicPlay.playMusic();
				MusicComponent.musicPlay2.musicStop();
			});
		}
		JButton returnBtn = new JButton();
		returnBtn.setOpaque(false);
		returnBtn.setContentAreaFilled(false);
		returnBtn.setBorder(null);
		returnBtn.setMargin(new Insets(0, 0, 0, 0));
		returnBtn.setIcon(new ImageIcon("src/Pic2/returnBtn0.png"));
		returnBtn.setRolloverIcon(new ImageIcon("src/Pic2/returnBtn1.png"));
		returnBtn.setSize(108,119);
		//returnBtn.setLocation(1348, 700);
		returnBtn.setLocation(1300, 680);
		returnBtn.setVisible(false);
		add(returnBtn);

		JButton explainBtn = new JButton();
		explainBtn.setOpaque(false);
		explainBtn.setContentAreaFilled(false);
		explainBtn.setBorder(null);
		explainBtn.setMargin(new Insets(0, 0, 0, 0));
		explainBtn.setIcon(new ImageIcon("src/Pic2/explainBtn0.png"));
		explainBtn.setRolloverIcon(new ImageIcon("src/Pic2/explainBtn1.png"));
		explainBtn.setSize(135,135);
		explainBtn.setLocation(1348, 0);
		//explainBtn.setText("Save");
		add(explainBtn);
		explainPic ePic = new explainPic();
		ePic.setLocation(0, 0);
		add(ePic);

		explainBtn.addActionListener((e) -> {
			choosePlayerComponent.setVisible(false);
			returnBtn.setVisible(true);
			explainBtn.setVisible(false);
			//remove(explainBtn);
			ePic.state = 1;
			ePic.repaint();
		});

		returnBtn.addActionListener((e) -> {
			choosePlayerComponent.setVisible(true);
			returnBtn.setVisible(false);
			explainBtn.setVisible(true);
			ePic.state = 0;
			ePic.repaint();
		});

		dispose();
		MusicComponent.musicPlay2.play();
	}

}

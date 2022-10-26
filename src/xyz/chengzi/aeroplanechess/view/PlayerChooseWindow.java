package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.StartingFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PlayerChooseWindow extends JFrame {
	PlayerChooseWindow(String title) {
		super(title);
		GameFrame back = StartingFrame.mainFrame;
		back.setEnabled(false);
		PlayerChooseWindow This = this;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				back.setEnabled(true);
				This.dispose();//关闭
			}
		});
		this.setLayout(null);
		this.setResizable(false);
		setSize(550, 450);
		setLocationRelativeTo(back);
		Image icon = Toolkit.getDefaultToolkit().getImage("src/pic/Icon.png");  // 图片的具体位置
		setIconImage(icon);
		JButton[] Op = new JButton[4];
		int X = 80, Y = 160, width = 160, height = 70;
		Font btnFont = new Font("Georgia", Font.BOLD, 20);
		for(int i = 0; i <= 3; i ++) {
			Op[i] = new JButton();
			Op[i].setSize(200, 50);
			Op[i].setLocation(X + ((i % 2) == 0 ? 0 : width), Y + ((i >= 2) ? height : 0));
			Op[i].setOpaque(false);
			Op[i].setContentAreaFilled(false);
			Op[i].setBorder(null);
			Op[i].setMargin(new Insets(0, 0, 0, 0));
			Op[i].setIcon(new ImageIcon("src/Pic2/player" + (i + 1) + "0.png"));
			Op[i].setRolloverIcon(new ImageIcon("src/Pic2/player" + (i + 1) + "1.png"));
			Op[i].setFocusPainted(false);
			Op[i].setFont(btnFont);
			add(Op[i]);
		}
		for(int i = 0; i <= 3; i ++) {
			int finalI = i;
			Op[i].addActionListener((e) -> {
				back.setEnabled(true);
				This.dispose();
				StartingFrame.mainFrame.Dice.state = 0;
				if(finalI != 0) back.controller.initializeGame(finalI + 1, 0);
				else back.controller.initializeGame(4, 1);
			});
		}
		ImageIcon Background = new ImageIcon("src/Pic2/restartBackground1.jpg");
		JLabel label0 = new JLabel(Background);
		label0.setBounds(0,0,550,450);
		add(label0);
		this.setVisible(true);
	}
}

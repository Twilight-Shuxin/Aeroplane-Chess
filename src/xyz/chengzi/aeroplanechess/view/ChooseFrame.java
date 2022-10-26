package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.GameIO.GameIO;
import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;

public class ChooseFrame extends JFrame {
	JButton[] Test = new JButton[100];
	ChooseFrame(String title) {
		super(title);
		this.setSize(550, 448);
		this.setResizable(false);
		Image icon = Toolkit.getDefaultToolkit().getImage("src/pic/Icon.png");  // 图片的具体位置
		this.setIconImage(icon);
		ChooseFrame This = this;
		StartingFrame.mainFrame.setEnabled(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				StartingFrame.mainFrame.setEnabled(true);
				This.dispose();//关闭
			}
		});

		int now = 1;
		File f = new File("saved" + now + ".txt");
		while(f.isFile()) {
			now ++;
			f = new File("saved" + now + ".txt");
		}
		now -= 1;
		System.out.println(now + "()");
		int t = 0;
		if(now < 2) t = 2 - now;
		JPanel panel = new JPanel(new GridLayout(now + 1 + t,1));
		ImageIcon image = new ImageIcon("src/Pic2/loadBackground.jpg");
		JLabel label=new JLabel(image);
		label.setBounds(0,0,550,350);
		panel.add(label);
		Font btnFont = new Font("Georgia", Font.BOLD,25);

		for(int i = 1; i <= now; i ++) {
			Test[i] = new JButton();
			Test[i].setFocusPainted(false);
			Test[i].setOpaque(false);
			Test[i].setContentAreaFilled(false);
			Test[i].setBorder(null);
			Test[i].setMargin(new Insets(0, 0, 0, 0));
			Test[i].setIcon(new ImageIcon("src/Pic2/record" + i + "0.png"));
			Test[i].setRolloverIcon(new ImageIcon("src/Pic2/record" + i + "1.png"));
			panel.add(Test[i]);
		}
		for(int i = now + 1; i <= now + t; i ++) {
			Test[i] = new JButton();
			Test[i].setFocusPainted(false);
			Test[i].setOpaque(false);
			Test[i].setContentAreaFilled(false);
			Test[i].setBorder(null);
			Test[i].setMargin(new Insets(0, 0, 0, 0));
			Test[i].setIcon(new ImageIcon("src/Pic2/nullRec0.png"));
			Test[i].setRolloverIcon(new ImageIcon("src/Pic2/nullRec1.png"));
			panel.add(Test[i]);
		}
		for(int i = 1; i <= now + t; i ++) {
			int finalI = i;
			int finalNow = now;
			Test[i].addActionListener((e) -> {
				try {
					if(finalI <= finalNow) {
						GameController controller = GameIO.Load(finalI);
						if(controller == null) {
							JOptionPane.showMessageDialog(this, "Something's wrong with this record...");
						}
						else {
							StartingFrame.mainFrame.controller = controller;
							StartingFrame.mainFrame.setEnabled(true);
							This.dispose();
						}
					}
					else JOptionPane.showMessageDialog(this, "No record here.");
				} catch (FileNotFoundException fileNotFoundException) {
					fileNotFoundException.printStackTrace();
				}
			});
		}
		final JScrollPane scroll_1 = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll_1);
		this.setVisible(true);
	}
}

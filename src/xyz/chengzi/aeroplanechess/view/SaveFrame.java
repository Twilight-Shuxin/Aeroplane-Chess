package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.GameIO.GameIO;
import xyz.chengzi.aeroplanechess.StartingFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class SaveFrame extends JFrame {
	JButton[] Test = new JButton[100];
	SaveFrame(String title) {
		super(title);
		this.setSize(550, 448);
		this.setResizable(false);
		StartingFrame.mainFrame.setEnabled(false);
		SaveFrame This = this;
		Image icon = Toolkit.getDefaultToolkit().getImage("src/pic/Icon.png");  // 图片的具体位置
		this.setIconImage(icon);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				StartingFrame.mainFrame.setEnabled(true);
				This.dispose();//关闭
			}
		});

		int now = 1;
		File f = new File("saved" + now + ".txt");
		while (f.isFile()) {
			now++;
			f = new File("saved" + now + ".txt");
		}
		now -= 1;
		int t = 0;
		if(now + 2 < 3) t = 1 - now;
		JPanel panel = new JPanel(new GridLayout(now + 2 + t, 1));
		ImageIcon image = new ImageIcon("src/Pic2/saveBackground.jpg");
		JLabel label = new JLabel(image);
		label.setBounds(0, 0, 550, 350);
		panel.add(label);
		Font btnFont = new Font("Georgia", Font.BOLD,25);

		for (int i = 1; i <= now; i++) {
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
		for(int i = now + 1; i <= now + t + 1; i ++) {
			Test[i] = new JButton();
			Test[i].setFocusPainted(false);
			Test[i].setOpaque(false);
			Test[i].setContentAreaFilled(false);
			Test[i].setBorder(null);
			Test[i].setMargin(new Insets(0, 0, 0, 0));
			Test[i].setIcon(new ImageIcon("src/Pic2/newRec0.png"));
			Test[i].setRolloverIcon(new ImageIcon("src/Pic2/newRec1.png"));
			panel.add(Test[i]);
		}
		for(int i = 1; i <= now + t + 1; i ++) {
			int finalI = i, finalNow = now;
			Test[i].addActionListener((e) -> {
				try {
					if(finalI <= finalNow) {
						int a = JOptionPane.showConfirmDialog(this, "Do you want to overwrite the previous record?", "Reminder",
								JOptionPane.YES_NO_OPTION);
						if (a == 0) {
							GameIO.Save(StartingFrame.mainFrame.controller, finalI);
							JOptionPane.showMessageDialog(this, "Successfully Saved Record_" + finalI + "!");
							StartingFrame.mainFrame.setEnabled(true);
							This.dispose();
						}
					}
					else {
						if(finalNow < 15) {
							GameIO.Save(StartingFrame.mainFrame.controller, finalNow + 1);
							JOptionPane.showMessageDialog(this, "Successfully Saved Record_" + (finalNow + 1) + "!");
							StartingFrame.mainFrame.setEnabled(true);
							This.dispose();
						}
						else {
							JOptionPane.showMessageDialog(this,"Maximum record space reached!");
						}
					}
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			});
		}

		final JScrollPane scroll_1 = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll_1);
		this.setVisible(true);
	}
}

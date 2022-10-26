package xyz.chengzi.aeroplanechess;

import javax.swing.*;
import java.awt.*;

public class ChoosePlayerComponent extends JComponent {
	JButton[] Players = new JButton[6];
	public int totPlayers = 0;

	ChoosePlayerComponent() {
		setLayout(null);
		setSize(1500, 500);
		//Players[1] = new JRadioButton("Single");
		Players[1] = new JButton();
		Players[2] = new JButton(); totPlayers = 2;
		Players[3] = new JButton();
		Players[4] = new JButton();
		Font btnFont = new Font("Consolas", Font.BOLD, 31);

		int btnLength = 200, btnWidth = 150;
		for (int i = 1; i <= 4; i++) {
			int index = i - 1;
			Players[i].setLocation(btnLength * index, 0);
			Players[i].setSize(btnWidth, btnLength);
			Players[i].setOpaque(false);
			Players[i].setFocusPainted(false);
			Players[i].setOpaque(false);
			Players[i].setContentAreaFilled(false);
			Players[i].setBorder(null);
			Players[i].setMargin(new Insets(0, 0, 0, 0));
			Players[i].setIcon(new ImageIcon("src/Pic2/choose" + (i - 1)+ "0.png"));
			Players[i].setRolloverIcon(new ImageIcon("src/Pic2/choose" + (i - 1)+ "1.png"));
			add(Players[i]);
		//	btnGroup.add(Players[i]);
		}
	}

	/*public void itemStateChanged(ItemEvent e) {
		if (Players[2].isSelected()) totPlayers = 2;
		else if(Players[3].isSelected()) totPlayers = 3;
		else totPlayers = 4;
	}*/
}

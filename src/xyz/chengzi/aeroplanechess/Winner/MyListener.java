package xyz.chengzi.aeroplanechess.Winner;

import xyz.chengzi.aeroplanechess.Frame2.WinFrame;
import xyz.chengzi.aeroplanechess.StartingFrame;
import xyz.chengzi.aeroplanechess.view.MusicComponent;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static xyz.chengzi.aeroplanechess.view.GameFrame.PLAYER_NAMES;

public class MyListener implements PropertyChangeListener {
	JFrame back;
	public MyListener(JFrame back) {
		this.back = back;
	}
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("WinnerProperty")) {
			String a = event.getNewValue().toString();
			System.out.println(a + "??");
			if(a.charAt(0) == '1') showWinWindow(1);
			else if(a.charAt(0) == '2') showWinWindow(2);
			else if(a.charAt(0) == '3') showWinWindow(3);
			else if(a.charAt(0) == '4') showWinWindow(4);
		}
	}

	public void showWinWindow(int player) {
		StartingFrame.mainFrame.statusLabel.setText(String.format("<html>[%s] : <br> You win the game! </html>", PLAYER_NAMES[player - 1]));
		int rec = 0;
		if(MusicComponent.musicPlay.state == 1) {
			MusicComponent.musicPlay.musicStop();
			MusicComponent.musicWin.playMusic();
			rec = 1;
		}
		WinFrame winFrame = new WinFrame("Congratulations!", back, player, rec);
		winFrame.setSize(750, 580);
		winFrame.setLocationRelativeTo(back);
		Image icon = Toolkit.getDefaultToolkit().getImage("src/pic/Icon.png");  // 图片的具体位置
		winFrame.setIconImage(icon);
		winFrame.setVisible(true);
	}
}


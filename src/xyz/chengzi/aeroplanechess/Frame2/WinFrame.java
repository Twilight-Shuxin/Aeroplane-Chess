package xyz.chengzi.aeroplanechess.Frame2;

import xyz.chengzi.aeroplanechess.view.MusicComponent;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WinFrame extends JFrame {
	public WinFrame(String title, JFrame back, int winner, int rec) {
		super(title);
		back.setEnabled(false);
		WinFrame This = this;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(rec == 1) {
					MusicComponent.musicPlay.playMusic();
					MusicComponent.musicWin.musicStop();
				}
				back.setEnabled(true);
				This.dispose();//关闭
			}
		});
		this.setResizable(false);

		ImageIcon startBackground;
		startBackground = new ImageIcon("src/Pic2/Winner" + winner + ".jpg");
		JLabel label0 = new JLabel(startBackground);
		label0.setBounds(0,0,750,550);
		add(label0);
	}
}

package xyz.chengzi.aeroplanechess.Frame2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class battlePlayerComponent extends JComponent implements ActionListener {
	public int state = 0;
	Timer timer = new Timer(100, this);
	int player, side;

	public battlePlayerComponent(int player, int side) {
		setSize(305, 220);
		this.player = player;
		this.side = side;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			paintPlayer(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void paintPlayer(Graphics g) throws IOException {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BufferedImage bufImage;
		bufImage = ImageIO.read(new File("src/Pic2/battleIcon" + (player + 1) + "" + side + "" + state + ".png"));
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(bufImage, 0, 0, getWidth(), getHeight(), this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}

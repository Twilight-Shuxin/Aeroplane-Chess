package xyz.chengzi.aeroplanechess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class explainPic extends JComponent {
	public int state = 0;

	public explainPic() {
		setSize(1500, 900);
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
		if(state == 0) bufImage = ImageIO.read(new File("src/Pic2/startBackground6.jpg"));
		//if(state == 0) bufImage = ImageIO.read(new File("src/Pic2/winnerAll4.png"));
		else bufImage = ImageIO.read(new File("src/Pic2/explain.jpg"));
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(bufImage, 0, 0, getWidth(), getHeight(), this);
	}
}


/*import javax.swing.*;
import java.awt.*;
import java.io.IOException;*/

/*public class DiceRolling extends JComponent {
	//Timer timer = new Timer(36, this);
	private int now = 0;
	public int state = 0;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			paintDice(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintDice(Graphics g) throws IOException {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BufferedImage bufImage;
		bufImage = ImageIO.read(new File("src/pic/Dice1" + (now + 1) + ".png"));
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(bufImage, 0, 0, bufImage.getWidth(this), bufImage.getHeight(this), this);

		g.setColor(Color.RED);
		g.fillRect(30, 30, 50,30);

		//timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		//if(state == 1) now = (now + 1) % 6;
		repaint();
	}
}*/

package xyz.chengzi.aeroplanechess.Winner;

import xyz.chengzi.aeroplanechess.Frame2.BattleFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DiceRolling2 extends JComponent implements ActionListener {
	Timer timer = new Timer(80, this);
	private int now = 0;
	public int state = 0;
	public int num = 0;
	BattleFrame battleFrame;

	public DiceRolling2(int x, int y, BattleFrame battleFrame) {
		setSize(x, y);
		this.battleFrame = battleFrame;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			paintDice(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void paintDice(Graphics g) throws IOException {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BufferedImage bufImage;
		if(state == -1 || state == -2) bufImage = ImageIO.read(new File("src/pic/Num" + num + ".png"));
		else bufImage = ImageIO.read(new File("src/pic/Dice1" + now + ".png"));
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(bufImage, 0, 0, getWidth(), getHeight(), this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		if(state == 1 || state == 2) {
			battleFrame.setEnabled(false);
			now = (now + 1) % 6;
		}
		if(now == 5) {
			state = -1;
			battleFrame.setEnabled(true);
		}
		repaint();
	}
}

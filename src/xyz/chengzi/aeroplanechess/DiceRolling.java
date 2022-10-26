
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

package xyz.chengzi.aeroplanechess;

import xyz.chengzi.aeroplanechess.view.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DiceRolling extends JComponent implements ActionListener {
	Timer timer = new Timer(100, this);
	GameFrame gameFrame;
	private int now = 0;
	public int state = 0;
	private int num = 0;

	public DiceRolling(GameFrame gameFrame) {
		setSize(180, 160);
		this.gameFrame = gameFrame;
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
	//	g.drawImage(bufImage, 0, 0, 50, 50, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		if(state == 1 || state == 2) {
			StartingFrame.mainFrame.setEnabled(false);
			now = (now + 1) % 6;
		}
		if(state == 1 && now == 4 && StartingFrame.mainFrame.controller.getNumber1() != -1 && StartingFrame.mainFrame.controller.getNumber2() != -1) {
			timer.setDelay(1000);
		}
		if(state == -1 && StartingFrame.mainFrame.controller.getNumber1() != -1 && StartingFrame.mainFrame.controller.getNumber2() != -1) {
			state = 2;
		}
		if(now == 5) {
			if(StartingFrame.mainFrame.controller.getNumber1() != -1 && StartingFrame.mainFrame.controller.getNumber2() != -1) {
				if(state == 2) {
					state = -2;
					now = 0;
					num = StartingFrame.mainFrame.controller.getNumber2();
					StartingFrame.mainFrame.setEnabled(true);
					StartingFrame.mainFrame.controller.status = String.format("<html> [%s] :<br>You Rolled (%d) and (%d)</html>",
							gameFrame.PLAYER_NAMES[gameFrame.controller.getCurrentPlayer()],
							StartingFrame.mainFrame.controller.getNumber1(),
							StartingFrame.mainFrame.controller.getNumber2());
					gameFrame.statusLabel.setText(String.format("<html> [%s] :<br>You Rolled (%d) and (%d)</html>",
							gameFrame.PLAYER_NAMES[gameFrame.controller.getCurrentPlayer()],
							StartingFrame.mainFrame.controller.getNumber1(),
							StartingFrame.mainFrame.controller.getNumber2()));
				}
				else {
					state = -1;
					timer.setDelay(100);
					now = 0;
					num = StartingFrame.mainFrame.controller.getNumber1();
				}
			}
			else if(!StartingFrame.mainFrame.diceSelectorComponent.isRandomDice() && StartingFrame.mainFrame.controller.getNumber1() != -1) {
				state = -1; now = 0;
				num = StartingFrame.mainFrame.controller.getNumber1();
				StartingFrame.mainFrame.setEnabled(true);
				StartingFrame.mainFrame.controller.status = String.format("<html> [%s] : <br> You Rolled a (%d) </html>",
						gameFrame.PLAYER_NAMES[gameFrame.controller.getCurrentPlayer()],
						StartingFrame.mainFrame.controller.getNumber1());
				gameFrame.statusLabel.setText(String.format("<html> [%s] : <br> You Rolled a (%d) </html>",
						gameFrame.PLAYER_NAMES[gameFrame.controller.getCurrentPlayer()],
						StartingFrame.mainFrame.controller.getNumber1()));
			}
		}
		repaint();
	}
}

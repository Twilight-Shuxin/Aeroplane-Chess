package xyz.chengzi.aeroplanechess;

import javax.swing.*;

public final class JGradientButton extends JButton {
	public JGradientButton(String text) {
		super(text);
		//setContentAreaFilled(false);
	}

	/*@Override
	protected void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g.create();
		Color color = new Color(255, 175, 175, 50);
		Color color2 = new Color(192, 192, 192, 255);
		g2.setPaint(new GradientPaint( new Point(0, 0), color, new Point(0, getHeight() / 3), Color.WHITE));
		g2.fillRect(0, 0, getWidth(), getHeight() / 3);
		g2.setPaint(new GradientPaint(new Point(0, getHeight() / 3), Color.WHITE, new Point(0, getHeight()), color));
		g2.fillRect(0, getHeight() / 3, getWidth(), getHeight());
		g2.dispose();



		super.paintComponent(g);
	}*/

}
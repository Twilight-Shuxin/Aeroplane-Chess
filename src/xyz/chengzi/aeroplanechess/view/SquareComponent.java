package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.StartingFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SquareComponent extends JPanel implements ActionListener {
    private final Color color;
    private final int player;
    private final int index;
    public Timer timer = new Timer(400, this);
    public int step = 0;

    public SquareComponent(int size, Color color, int player, int index) {
        this.setOpaque(false);
        setLayout(new GridLayout(1, 1)); // Use 1x1 grid layout
        setSize(size, size);
        this.color = color;
        this.player = player;
        this.index = index;//每个颜色的第几个格子
    }

    public Color getColor() {
        return color;
    }

    public int getPlayer() {
        return player;
    }

    public int getIndex() {
        return index;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            paintSquare(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private transient BufferedImage bufImage;
    private void paintSquare(Graphics g) throws IOException {
        int player = 0;
        if(color.getRGB() == Color.YELLOW.getRGB()) player = 0;
        else if(color.getRGB() == Color.GREEN.getRGB()) player = 3;
        else if(color.getRGB() == Color.BLUE.getRGB()) player = 2;
        else player = 1;
        String s = "0";
        if(index == 18) s = "1";
        else if(index == 23) s = "2";
        else if(index > 18) s = "3";
        else if(index == 4 || index == 7) s = "4";
        else if(index >= 12) s = "5";
        bufImage = ImageIO.read(new File("src/pic/sq" + player + s + (step % 2) + ".png"));
      //  bufImage = ImageIO.read(new File("src/pic/sq0" + s + (step % 2) + ".png"));

        //bufImage = ImageIO.read(new File("src/pic/test.png"));
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(bufImage, 0, 0, bufImage.getWidth(this), bufImage.getHeight(this), this);
    }

    public void actionPerformed(ActionEvent e) {
        step += 1;
        StartingFrame.mainFrame.setEnabled(false);
        if(step == 8) {
            timer.stop();
            StartingFrame.mainFrame.setEnabled(true);
            StartingFrame.mainFrame.starComponent.state = 0;
            StartingFrame.mainFrame.starComponent.repaint();
        }
        repaint();
    }
}

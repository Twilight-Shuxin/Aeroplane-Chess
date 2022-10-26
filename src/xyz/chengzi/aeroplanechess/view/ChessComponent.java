package xyz.chengzi.aeroplanechess.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChessComponent extends JComponent {
    private Color color;
    private int state;
    public int entered;
    public  int player;

    public ChessComponent(Color color, int state) {
        this.color = color;
        this.state = state;
        this.entered = 0;
        if(color.getRGB() == Color.YELLOW.getRGB()) player = 0;
        else if(color.getRGB() == Color.BLUE.getRGB()) player = 1;
        else if(color.getRGB() == Color.RED.getRGB()) player = 2;
        else player = 3;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            paintChess(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setState(int x) {
        state = x;
        return;
    }

    private transient BufferedImage bufImage;
    private void paintChess(Graphics g) throws IOException {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(state != -1) bufImage = ImageIO.read(new File("src/Pic2/pic" + (player + 1) + state + entered + ".png"));
        else bufImage = ImageIO.read(new File("src/Pic2/winPlayer0.png"));
   //     bufImage = ImageIO.read(new File("src/pic/21.png"));
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(bufImage, 0, 0, bufImage.getWidth(this), bufImage.getHeight(this), this);
    }
}

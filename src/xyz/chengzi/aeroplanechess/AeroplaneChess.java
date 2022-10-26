package xyz.chengzi.aeroplanechess;

import javazoom.jl.decoder.JavaLayerException;
import xyz.chengzi.aeroplanechess.view.MusicComponent;

import javax.swing.*;
import java.io.FileNotFoundException;

public class AeroplaneChess {
    public static void main(String[] args) throws FileNotFoundException {
        MusicComponent a = new MusicComponent();
        System.setProperty("sun.java2d.win.uiScaleX", "96dpi");//dpi 与像素有关，改变界面比例
        System.setProperty("sun.java2d.win.uiScaleY", "96dpi");
        SwingUtilities.invokeLater(() -> {//不需要理解
            StartingFrame startingFrame = null;
            try {
                startingFrame = new StartingFrame("Welcome!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
            startingFrame.setVisible(true);
        });
    }
}

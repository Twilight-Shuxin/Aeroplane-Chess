package xyz.chengzi.aeroplanechess.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileNotFoundException;

public class Music {
    Player player;
    Media music;
    MediaPlayer mediaPlayer;

    public Music(String file) {
        music = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(music);
    }

    public void play() throws FileNotFoundException, JavaLayerException {
        mediaPlayer.play();
        state = 1;
    }

    public  void musicStop() {
        mediaPlayer.stop();
        state = 0;
    }

    public int state = 0;
    public void playMusic(){
        if(state == 0){
            mediaPlayer.play();
            state = 1;
        }
        else{
            mediaPlayer.stop();
            state = 0;
        }
    }
}
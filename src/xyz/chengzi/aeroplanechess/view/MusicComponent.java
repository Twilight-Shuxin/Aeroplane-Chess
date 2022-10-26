package xyz.chengzi.aeroplanechess.view;

import javafx.application.Platform;
import xyz.chengzi.aeroplanechess.controller.Music;

public class MusicComponent {
	public static Music musicPlay;
	public static Music musicWin;
	public static Music musicBattle;
	public static Music musicPlay2;

	public MusicComponent() {
		Platform.startup(() ->
		{
			musicPlay2 = new Music("src/music/music4.mp3");
			musicPlay = new Music("src/music/music1.mp3");
			musicWin = new Music("src/music/music2.mp3");
			musicBattle = new Music("src/music/music3.mp3");
		});
	}
}

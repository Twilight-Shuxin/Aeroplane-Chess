package xyz.chengzi.aeroplanechess.GameIO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewBufferedWriter extends BufferedWriter {
	NewBufferedWriter(FileWriter fileWriter) {
		super(fileWriter);
	}

	public void write(int s) throws IOException {
		super.write(s + " "); super.write("\n");
	}
}

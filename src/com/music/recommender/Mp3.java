package com.music.recommender;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javazoom.jl.player.Player;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

public class Mp3 {
	private String filename;
	private Player player;
	private double totalTime = 0;

	/**
	 * MP3 constructor
	 * 
	 * @param filename
	 *            name of input file
	 */
	public Mp3(String filename) {
		this.filename = filename;
	}

	/**
	 * Creates a new Player
	 */
	public void play() {
		try {
			FileInputStream fis = new FileInputStream(this.filename);
			BufferedInputStream bis = new BufferedInputStream(fis);
			this.player = new Player(bis);

			File file = new File(filename);
			AudioFileFormat baseFileFormat = new MpegAudioFileReader()
					.getAudioFileFormat(file);
			Map properties = baseFileFormat.properties();
			Long duration = (Long) properties.get("duration");
			totalTime = duration.doubleValue() / (1000.0);
		} catch (Exception e) {
			System.err.printf("%s\n", e.getMessage());
		}

		new Thread() {
			@Override
			public void run() {
				try {
					player.play();
				} catch (Exception e) {
					System.err.printf("%s\n", e.getMessage());
				}
			}
		}.start();
	}

	/**
	 * Closes the Player
	 */
	public double close() {
		int currentPosition = player.getPosition();
		if (this.player != null) {
			this.player.close();
		}
		return ((currentPosition / totalTime) * 100);
	}
}

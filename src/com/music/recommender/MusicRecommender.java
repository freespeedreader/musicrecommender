package com.music.recommender;

public class MusicRecommender {

	public static void main(String[] args) throws InterruptedException {
		Mp3 mp3 = new Mp3("music/test.mp3");

		mp3.play();
		Thread.sleep(62900);
		mp3.close();
		//1009033
	}
}

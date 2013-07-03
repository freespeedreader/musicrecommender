package com.music.recommender.index;

public class UserBehavior {

	private long musicId;
	private String musicName;
	private int noOfTimesMusicPlayed;
	private int rating;
	private String userName;

	@Override
	public String toString() {
		return "UserBehavior [musicId=" + musicId + ", musicName=" + musicName
				+ ", noOfTimesMusicPlayed=" + noOfTimesMusicPlayed
				+ ", rating=" + rating + ", userName=" + userName + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getMusicId() {
		return musicId;
	}

	public void setMusicId(long musicId) {
		this.musicId = musicId;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public int getNoOfTimesMusicPlayed() {
		return noOfTimesMusicPlayed;
	}

	public void setNoOfTimesMusicPlayed(int noOfTimesMusicPlayed) {
		this.noOfTimesMusicPlayed = noOfTimesMusicPlayed;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}

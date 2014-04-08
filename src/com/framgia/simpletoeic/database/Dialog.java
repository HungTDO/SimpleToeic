package com.framgia.simpletoeic.database;

public class Dialog {
	
	private int id = 0;

	private int partId = 0;

	private String content;

	private String imgUrl;
	
	private String audioUrl;

	public Dialog(int id, int partId, String content, String imgUrl, String audioUrl) {
		this.id = id;
		this.partId = partId;
		this.content = content;
		this.imgUrl = imgUrl;
		this.audioUrl = audioUrl;
	}

	public int getId() {
		return id;
	}

	public int getPartId() {
		return partId;
	}

	public String getContent() {
		return content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getAudioUrl() {
		return audioUrl;
	}
	
}

package com.example.test;

public class ListData {
	public static final int SEND=1;
	public static final int RECEIVER=2;
	private String contentString;
	private int flag;
	private String timeString;
	
	public String getTimeString() {
		return timeString;
	}
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	public ListData(String contentString,int flag,String time) {
		setContentString(contentString);
		setFlag(flag);
		setTimeString(time);
	}
	public String getContentString() {
		return contentString;
	}
	public void setContentString(String contentString) {
		this.contentString = contentString;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}

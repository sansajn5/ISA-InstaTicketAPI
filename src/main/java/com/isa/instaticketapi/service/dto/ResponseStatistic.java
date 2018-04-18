package com.isa.instaticketapi.service.dto;

public class ResponseStatistic {

	private String date;
	private int attendence;

	public ResponseStatistic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseStatistic(String date, int attendence) {
		super();
		this.date = date;
		this.attendence = attendence;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAttendence() {
		return attendence;
	}

	public void setAttendence(int attendence) {
		this.attendence = attendence;
	}

}

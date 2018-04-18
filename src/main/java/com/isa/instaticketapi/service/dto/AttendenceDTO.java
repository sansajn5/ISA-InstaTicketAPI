package com.isa.instaticketapi.service.dto;

public class AttendenceDTO {
	
	private String dateFrom;
	private String dateTo;

	public AttendenceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public AttendenceDTO(String dateFrom, String dateTo) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

}

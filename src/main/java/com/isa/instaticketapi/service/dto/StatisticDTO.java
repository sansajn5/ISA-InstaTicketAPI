package com.isa.instaticketapi.service.dto;

public class StatisticDTO {
	
	private String dateFrom;
	private String dateTo;

	public StatisticDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public StatisticDTO(String dateFrom, String dateTo) {
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

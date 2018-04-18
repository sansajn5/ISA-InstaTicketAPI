package com.isa.instaticketapi.web.rest.vm;

import java.util.ArrayList;

import com.isa.instaticketapi.service.dto.ResponseStatistic;

public class StatisticResponse {

	private ArrayList<ResponseStatistic> list;

	public StatisticResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatisticResponse(ArrayList<ResponseStatistic> list) {
		super();
		this.list = list;
	}

	public ArrayList<ResponseStatistic> getList() {
		return list;
	}

	public void setList(ArrayList<ResponseStatistic> list) {
		this.list = list;
	}

}

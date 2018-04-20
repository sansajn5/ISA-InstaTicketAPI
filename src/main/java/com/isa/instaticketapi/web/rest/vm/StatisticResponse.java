package com.isa.instaticketapi.web.rest.vm;

import java.util.ArrayList;

import com.isa.instaticketapi.service.dto.ResponseStatistic;

public class StatisticResponse {

	private ArrayList<ResponseStatistic> list;
	int sum;

	public StatisticResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public StatisticResponse(ArrayList<ResponseStatistic> list, int sum) {
		super();
		this.list = list;
		this.sum = sum;
	}

	public ArrayList<ResponseStatistic> getList() {
		return list;
	}

	public void setList(ArrayList<ResponseStatistic> list) {
		this.list = list;
	}

}

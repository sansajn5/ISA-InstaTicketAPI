package com.isa.instaticketapi.web.rest.vm;

import java.util.ArrayList;

import com.isa.instaticketapi.service.dto.ResponseAttendence;

public class AttendenceResponse {

	private ArrayList<ResponseAttendence> list;

	public AttendenceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttendenceResponse(ArrayList<ResponseAttendence> list) {
		super();
		this.list = list;
	}

	public ArrayList<ResponseAttendence> getList() {
		return list;
	}

	public void setList(ArrayList<ResponseAttendence> list) {
		this.list = list;
	}

}

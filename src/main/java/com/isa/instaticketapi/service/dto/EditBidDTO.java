package com.isa.instaticketapi.service.dto;

public class EditBidDTO {
	
	private Long id;
	
	private String sum;
	
	public EditBidDTO() {
		// TODO Auto-generated constructor stub
	}

	public EditBidDTO(Long id, String sum) {
		super();
		this.id = id;
		this.sum = sum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}
	
	

}

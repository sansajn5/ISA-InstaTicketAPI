package com.isa.instaticketapi.web.rest.vm.FanZoneResource;

import java.util.List;

import com.isa.instaticketapi.domain.Bid;

public class BidsResponse {
	
	
	private List<Bid> bids;
	
	public BidsResponse() {
		// TODO Auto-generated constructor stub
	}
	
	
	

	public BidsResponse(List<Bid> bids) {
		super();
		this.bids = bids;
	}



	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
	
	
	

}

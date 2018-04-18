package com.isa.instaticketapi.web.resource;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.ItemsResponse;

public class FanzoneResourceTest extends AbstractResourceTest {
	
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	@Transactional
	public void getItemsSuccesfull() throws Exception{
		
		String uri = "/api/fanzone/items";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		ItemsResponse itemsResponse = mapFromJson(result.getResponse().getContentAsString(), ItemsResponse.class);
		Assert.assertEquals(2, itemsResponse.getItems().size());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}

}

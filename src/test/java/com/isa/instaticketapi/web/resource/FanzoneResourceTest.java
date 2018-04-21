package com.isa.instaticketapi.web.resource;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.service.dto.ChangeOfferDTO;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.ItemResponse;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.ItemsResponse;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.OfferResponse;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.OffersResponse;

public class FanzoneResourceTest extends AbstractResourceTest {
	
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	@Transactional
	public void testGetItemsSuccesfull() throws Exception{
		
		String uri = "/api/fanzone/items";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		ItemsResponse itemsResponse = mapFromJson(result.getResponse().getContentAsString(), ItemsResponse.class);
		Assert.assertEquals(2, itemsResponse.getItems().size());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}
	
	
	@Test
	@Transactional
	public void testGetItemSuccesfulll() throws Exception{
		
		String uri = "/api/fanzone/item/1";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		ItemResponse itemsResponse = mapFromJson(result.getResponse().getContentAsString(), ItemResponse.class);
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
		
		Assert.assertNotNull(itemsResponse.getItem());
		
	}
	
	
	
	@Test
	@Transactional
	public void testDeleteItemsUnsuccesfull() throws Exception{
		
		String uri = "/api/fanzone/delete-item/44447";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(405, status);
	}
	
	
	@Test
	@Transactional
	public void testAddItemsUnsuccesfull() throws Exception{
		
		String uri = "/api/fanzone/new-item";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(405, status);
	}
	
	
	@Test
	@Transactional
	public void testGetItemSuccesfull() throws Exception{
		
		String uri = "/api/fanzone/item/2";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		ItemResponse itemsResponse = mapFromJson(result.getResponse().getContentAsString(), ItemResponse.class);
		Assert.assertEquals("1200", itemsResponse.getItem().getPrice());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
	}
	
	
	
	@Test
	@Transactional
	public void testGetItemUnsuccesfull() throws Exception{
		
		String uri = "/api/fanzone/item/ff";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		//ItemResponse itemsResponse = mapFromJson(result.getResponse().getContentAsString(), ItemResponse.class);
		//Assert.assertEquals("1200", itemsResponse.getItem().getPrice());
		int status = result.getResponse().getStatus();
		Assert.assertEquals(400, status);
	}
	
	
	

	@Test
	@Transactional
	public void testEditOfferSuccesfull() throws Exception{
		
		String uri = "/api/fanzone/edit-offer";
		
		ChangeOfferDTO coDTO = new ChangeOfferDTO("txa","txt","txt");
		Integer x = 123312;
		Long lx = x.longValue();
		coDTO.setId(lx);
		String input = mapToJson(coDTO);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(input)).andReturn();
		OfferResponse offerResponse = mapFromJson(result.getResponse().getContentAsString(), OfferResponse.class);
		
		Assert.assertNull(offerResponse.getOffer());
		
	}
	

	@Test
	@Transactional
	public void testNewOffersUnsuccesfull() throws Exception{
		
		String uri = "/api/fanzone/new-offer";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(405, status);
	}


	@Test
	@Transactional
	public void testGetBidsForOffersUnsuccessfull() throws Exception{
		
		
		String uri = "/api/fanzone/get-bids-for-offer/xx";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(400, status);
	}
	
	
	@Test
	@Transactional
	public void testAcceptingBidForBidsUnsuccesfull() throws Exception{
		
		String uri = "/api/fanzone/confirm-item-reservation";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(405, status);
		
	}
	
	

}

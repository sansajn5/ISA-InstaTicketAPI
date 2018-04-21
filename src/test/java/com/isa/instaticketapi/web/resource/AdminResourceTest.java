package com.isa.instaticketapi.web.resource;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.service.dto.user.UsersResponse;



public class AdminResourceTest extends AbstractResourceTest {

	
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	@Transactional
	public void testGetUsersSuccesfulll() throws Exception{
		
		String uri = "/api/admin/get-users";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		UsersResponse UsersResponse = mapFromJson(result.getResponse().getContentAsString(), UsersResponse.class);
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
		
		Assert.assertNotNull(UsersResponse.getUsers());
		
	}
	
	
	
	@Test
	@Transactional
	public void testAddSystemAdminRoleUnsuccesfull() throws Exception{
		
		String uri = "/api/admin/delete-system-admin-role/5";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(405, status);
		
	}
	
	
	@Test
	@Transactional
	public void testPlacemAdminRoleSuccesfull() throws Exception{
		
		String uri = "/api/admin/employed-place-admins/1";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		//UsersResponse UsersResponse = mapFromJson(result.getResponse().getContentAsString(), UsersResponse.class);
		int status = result.getResponse().getStatus();
		Assert.assertEquals(200, status);
		
	}
	
	
	
	
	@Test
	@Transactional
	public void testDeleteRoleUnsuccesfull() throws Exception{
		
		String uri = "/api/admin/delete-role/0258";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		//UsersResponse UsersResponse = mapFromJson(result.getResponse().getContentAsString(), UsersResponse.class);
		int status = result.getResponse().getStatus();
		Assert.assertEquals(405, status);
		
	}
	
	
	@Test
	@Transactional
	public void testFanZoneRoleUnsuccesfull() throws Exception{
		
		String uri = "/api/admin/fanzone-admin-role/55";
		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		//UsersResponse UsersResponse = mapFromJson(result.getResponse().getContentAsString(), UsersResponse.class);
		int status = result.getResponse().getStatus();
		Assert.assertEquals(405, status);
		
	}
	
	
	
}

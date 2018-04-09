package com.isa.instaticketapi.web.resource;

import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.web.rest.vm.PlaceResource.CinemaResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

public class PlaceResourceTest extends AbstractResourceTest {

    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    @Transactional
    public void testGetCinemas() throws Exception {
        String uri = "/api/place/cinemas";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(mapFromJson(result.getResponse().getContentAsString(),CinemaResponse.class));
        CinemaResponse cinemaResponse = mapFromJson(result.getResponse().getContentAsString(),CinemaResponse.class);
        Assert.assertNotNull(cinemaResponse.getCinemas());
        int status = result.getResponse().getStatus();
        Assert.assertEquals(200,status);
    }

}

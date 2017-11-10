package isa.instaTicketAPI.web.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import isa.instaTicketAPI.services.UserService;

@RestController
@RequestMapping("/product")
@Api(value="shop")
public class TestSwagger {

	 private final Logger log = LoggerFactory.getLogger(TestSwagger.class);
	
	@Autowired
	UserService userService;
	
	 @ApiOperation(value = "View a list of available products",response = Iterable.class)
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "Successfully retrieved list"),
	            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	    }
	    )
	    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
	    public List<String> list(){
	     List<String> temp = new ArrayList<String>();   
		 temp.add("Nemanja");
		 temp.add("swagger");
		 
		 return temp;
	    }
	
	 @ApiOperation(value="Test funckija",response = String.class)
	 	@ApiResponses(value = {
	            @ApiResponse(code = 200, message = "Successfully done"),
	            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	    }
	 )
	 @RequestMapping(value="/test",method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<String> test(){
		 String testt = "nemanja proverava";
		 log.debug("GET /test {}",testt);
		 return ResponseEntity.ok().body(userService.getUser().getUsername());
	 }
	
}


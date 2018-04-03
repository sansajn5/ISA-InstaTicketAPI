package com.isa.instaticketapi.web.rest;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.service.FanZoneService;
import com.isa.instaticketapi.service.dto.ChangeItemDTO;
import com.isa.instaticketapi.service.dto.ItemDTO;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.ItemResponse;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.ItemsResponse;
import com.isa.instaticketapi.web.rest.vm.UserResource.AdminRole;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/fanzone")
public class FanZoneResource {
	
	private final Logger log = LoggerFactory.getLogger(FanZoneResource.class);
	

	@Autowired
	private FanZoneService fanZoneService;

	@Autowired
	private ItemRepository itemRepository;
	
	
	
	// add 
	@GetMapping("/getItems}")
	public ResponseEntity<ItemsResponse> getItems() {

		
		List<Item> items = fanZoneService.getItems();
		log.debug("TEST {}", items);
		return new ResponseEntity<>(new ItemsResponse(items), HttpStatus.OK);
		
		
	}
	
	
	@ApiOperation(value = "Adding new item", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@PostMapping("/new-item")
	public ResponseEntity<ItemResponse> addNewItem(@RequestBody ItemDTO itemDto) throws SQLException {
				
		Item item = fanZoneService.addNewItem(itemDto);				
		
		return new ResponseEntity<>(new ItemResponse(item),HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Deleting item", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@DeleteMapping("/delete-item/{id}")
	public ResponseEntity<ItemResponse> deleteItem(@PathVariable("id") Long id) {
		
		Item item = fanZoneService.deleteItem(id);
		
		return new ResponseEntity<>(new ItemResponse(item),HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Adding new item", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@PutMapping("/edit-item")
	public ResponseEntity<ItemResponse> editItem(@RequestBody ChangeItemDTO itemDto) throws SQLException {
		
		Item item = fanZoneService.editItem(itemDto);
		
		return new ResponseEntity<>(new ItemResponse(item),HttpStatus.OK);
		
	}
	
}

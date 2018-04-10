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
import com.isa.instaticketapi.domain.Offer;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.service.FanZoneService;
import com.isa.instaticketapi.service.dto.ChangeItemDTO;
import com.isa.instaticketapi.service.dto.ChangeOfferDTO;
import com.isa.instaticketapi.service.dto.ItemDTO;
import com.isa.instaticketapi.service.dto.OfferDTO;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.ItemResponse;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.ItemsResponse;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.OfferResponse;
import com.isa.instaticketapi.web.rest.vm.FanZoneResource.OffersResponse;
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
	
	
	
	
	
	@ApiOperation(value = "Listing all items from fan zone", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@GetMapping("/items")
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
	
	
	@ApiOperation(value = "Listing all items from fan zone", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@GetMapping("/item/{id}")
	public ResponseEntity<ItemResponse> getItem(@PathVariable("id") Long id) throws SQLException {
		
		Item item = fanZoneService.getItemById(id);
		
		return new ResponseEntity<>(new ItemResponse(item),HttpStatus.OK);
	}
	
	
	
	
	
	
	@ApiOperation(value = "Editing existing item", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@PutMapping("/edit-item/{id}")
	public ResponseEntity<ItemsResponse> editItem(@PathVariable("id") Long id, @RequestBody ChangeItemDTO itemDto) throws SQLException {
		
		
		List<Item> items = fanZoneService.editItem(itemDto,id);
		
		return new ResponseEntity<>(new ItemsResponse(items),HttpStatus.OK);
		
	}
	
	
	
	@ApiOperation(value = "Listing all offers from fan zone", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@GetMapping("/getOffers")
	public ResponseEntity<OffersResponse> getOffers() {

		
		List<Offer> offers = fanZoneService.getOffers();
		log.debug("TEST {}", offers);
		return new ResponseEntity<>(new OffersResponse(offers), HttpStatus.OK);
		
		
	}
	
	
	
	@ApiOperation(value = "Adding new offer", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@PostMapping("/new-offer")
	public ResponseEntity<OfferResponse> addNewOffer(@RequestBody OfferDTO offerDTO) throws SQLException {
				
		Offer offer = fanZoneService.addNewOffer(offerDTO);		
		
		return new ResponseEntity<>(new OfferResponse(offer),HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Deleting offer", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@DeleteMapping("/delete-offer/{id}")
	public ResponseEntity<OfferResponse> deleteOffer(@PathVariable("id") Long id) {
		
		Offer offer = fanZoneService.deleteOffer(id);
		
		return new ResponseEntity<>(new OfferResponse(offer),HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "Editing existing offer", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@PutMapping("/edit-offer")
	public ResponseEntity<OfferResponse> editOffer(@RequestBody ChangeOfferDTO offerDto) throws SQLException {
		
		Offer offer = fanZoneService.editOffer(offerDto);
		
		return new ResponseEntity<>(new OfferResponse(offer),HttpStatus.OK);
		
	}
	
}

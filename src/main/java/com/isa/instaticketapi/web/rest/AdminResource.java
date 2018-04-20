package com.isa.instaticketapi.web.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.domain.Authority;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.EmploymentPlaceRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.service.AdminService;
import com.isa.instaticketapi.service.dto.account.AdminRoleDTO;
import com.isa.instaticketapi.service.dto.user.UserAuthDTO;
import com.isa.instaticketapi.service.dto.user.UserResponse;
import com.isa.instaticketapi.service.dto.user.UsersResponse;
import com.isa.instaticketapi.web.rest.vm.UserResource.AdminRole;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/admin")
public class AdminResource {

	private final Logger log = LoggerFactory.getLogger(AccountResource.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private EmploymentPlaceRepository employmentPlaceRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	
	
	
	
	@ApiOperation(value = "Getting all users", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@GetMapping("/get-users")
	public ResponseEntity<UsersResponse>  getUsers() {
		
		List<User> users = adminService.getUsers();
		
		List<UserAuthDTO> auths = new ArrayList<UserAuthDTO>();
		
		for(User us : users) {
			
			UserAuthDTO auth = new UserAuthDTO();
			auth.setId(us.getId());
			
			List<String> authorities = new ArrayList<String>();
			
			
			for(Authority a : us.getAuthorities()) {
				authorities.add(a.getName());
			}
			
			
			auth.setAuthorities(authorities);
			
			auths.add(auth);
					
		}
		
		
		return new ResponseEntity<>(new UsersResponse(users,auths),HttpStatus.OK);
	}
	
	
	
	
	
	
	
	@ApiOperation(value = "Setting system admin role to user", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PostMapping("/system-admin-role/{id}")
	public ResponseEntity<UserResponse> setSystemAdminRole(@PathVariable("id") Long id) {
		log.debug("REST request to set system admin role to user : {}", id);
		
		User user = adminService.setSystemAdminRole(id);
		
		return new ResponseEntity<>(new UserResponse(user),HttpStatus.OK);
		
	}
	
	
	
	@ApiOperation(value = "Deleting system admin role to user", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PutMapping("/delete-system-admin-role/{id}")
	public ResponseEntity<UserResponse> deleteSystemAdminRole(@PathVariable("id") Long id) {
		
		log.debug("REST request to set system admin role to user : {}", id);
		
		User user = adminService.deleteSystemAdminRole(id);
		
		return new ResponseEntity<>(new UserResponse(user),HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	@ApiOperation(value = "Setting fanzone admin role to user", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PostMapping("/fanzone-admin-role/{id}")
	public ResponseEntity<UserResponse> setFanZoneAdminRole(@PathVariable("id") Long id) throws SQLException {
		log.debug("REST request to set system admin role to user : {}", id);
		
		User user = adminService.setFanZoneAdminRole(id);
		
		return new ResponseEntity<>(new UserResponse(user),HttpStatus.OK);
		
	}
	
	
	
	@ApiOperation(value = "Deleting fanzone admin role to user", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PutMapping("/delete-fanzone-admin-role/{id}")
	public ResponseEntity<UserResponse> deleteFanZoneAdminRole(@PathVariable("id") Long id) throws SQLException {
		
		log.debug("REST request to set system admin role to user : {}", id);
		
		User user = adminService.deleteFanZoneAdminRole(id);
		
		return new ResponseEntity<>(new UserResponse(user),HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	@ApiOperation(value = "Removing role to user", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PostMapping("/delete-role/{id}")
	public void removeRole(@PathVariable("id") Long id) throws SQLException {
			
		log.debug("REST request to remove role to user : {}", id);
		
		adminService.removeRole(id);
		
	}
	
	
	
	@ApiOperation(value = "Setting place admin role to user", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PostMapping("/place-admin-role/{id}")
	public void setPlaceAdminRole(@RequestBody AdminRoleDTO adminRoleDTO, @PathVariable("id") Long id) throws SQLException {
		
		log.debug("REST request to set place admin role to user : {}", adminRoleDTO);
		
		
		adminService.setPlaceAdminRole(adminRoleDTO, id);
		
			
		// dodati response
		
	}
	
	
	
	
	
}

package com.isa.instaticketapi.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.service.AdminService;
import com.isa.instaticketapi.service.dto.account.AdminRoleDTO;
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
	
	
	@ApiOperation(value = "Setting system admin role to user", response = AdminRole.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Succesfully created projection"),
			@ApiResponse(code = 400, message = "Some attribute is already in use"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Error on server side"),
			@ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
	@Transactional
	@PostMapping("/system-admin-role")
	public void setSystemAdminRole(@RequestBody AdminRoleDTO adminRoleDTO) {
		log.debug("REST request to create Projection : {}", adminRoleDTO);
		
		adminService.setSystemAdminRole(adminRoleDTO.getUsername());
		
	}
	
	
	
}

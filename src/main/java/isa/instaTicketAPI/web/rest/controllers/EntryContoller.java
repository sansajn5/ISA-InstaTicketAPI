package isa.instaTicketAPI.web.rest.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import isa.instaTicketAPI.domain.User;
import isa.instaTicketAPI.domain.enumerations.Role;
import isa.instaTicketAPI.domain.enumerations.UserStatus;
import isa.instaTicketAPI.service.AuthorityService;
import isa.instaTicketAPI.service.UserService;
import isa.instaTicketAPI.utils.TokenUtils;
import isa.instaTicketAPI.utils.UserUtils;
import isa.instaTicketAPI.web.rest.dto.ConfirmUserDTO;
import isa.instaTicketAPI.web.rest.dto.LoginUserDTO;
import isa.instaTicketAPI.web.rest.dto.RegistrateUserDTO;
import isa.instaTicketAPI.web.rest.dto.ResponseMessage;

@RestController
@RequestMapping(value = "/api/entry")
public class EntryContoller {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	UserService userService;

	@Autowired
	AuthorityService authorityService;

	@Autowired
	UserUtils userUtils;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@ApiOperation(value = "Registration new user", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Succesfully created user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 488, message = "Username allready exist"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@Transactional
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> registrate(@RequestBody RegistrateUserDTO registrateUser) {

		User user = new User();
		user.setUsername(registrateUser.getUsername());
		user.setEmail(registrateUser.getEmail());
		user.setPassword(passwordEncoder.encode(registrateUser.getPassword()));
		user.setStatus(UserStatus.DISABLED);
		user.setName(registrateUser.getName());
		user.setLastName(registrateUser.getLastName());

		if (userService.findByUsername(registrateUser.getUsername()) != null) {
			return new ResponseEntity<ResponseMessage>(new ResponseMessage("Username allready exist"),
					HttpStatus.NOT_ACCEPTABLE);
		}
		
		user.setConfirmationLink(UUID.randomUUID().toString());
		
		if ("user".equals(registrateUser.getAuthority())) {
			user.setAuthority(authorityService.findByName("USER"));
			user.setRole(Role.USER);
			userService.register(user);
			return new ResponseEntity<ResponseMessage>(new ResponseMessage("Succesfully created user"), HttpStatus.OK);
		} else {
			user.setAuthority(authorityService.findByName("ADMIN"));
			user.setRole(Role.ADMIN);
			userService.register(user);
			return new ResponseEntity<ResponseMessage>(new ResponseMessage("Succesfully created admin"), HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Login for registred users", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Succesfully logged in"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 400, message = "Credentials are not valid"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> login(@RequestBody LoginUserDTO loginUser) {
		
		try {
			UserDetails details = userDetailsService.loadUserByUsername(loginUser.getUsername());

			if (details.getAuthorities().size() == 0) {
				return new ResponseEntity<>(new ResponseMessage(""), HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			for(GrantedAuthority a : details.getAuthorities()){
				System.out.println(a.getAuthority());
			}
			
			if(!userService.checkEnabled(details.getUsername())) {
				return new ResponseEntity<>(new ResponseMessage("You're account is not activated!"),HttpStatus.OK);
			}
			
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
					loginUser.getPassword());

			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			return new ResponseEntity<>(new ResponseMessage(tokenUtils.generateToken(details)), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(new ResponseMessage("Invalid login"), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Loging out from SecurityContext",response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Succesfully loged out"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Confirmation link for enabeling user account",response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Succesfully enabled account"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/confirm/{id:.+}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessage> confirmAccount(@PathVariable("id")String value, @RequestBody ConfirmUserDTO confirmUser) {
	
		if(!userService.checkLink(value)) 
			return new ResponseEntity<ResponseMessage>(new ResponseMessage("Invaild url"), HttpStatus.OK);
	
		if(!userService.confirmAccount(confirmUser.getPassword(),value)) 
			return new ResponseEntity<ResponseMessage>(new ResponseMessage("Invalid password"), HttpStatus.OK);
		
		return new ResponseEntity<ResponseMessage>(new ResponseMessage("Your account is ready to use"), HttpStatus.OK);
	}
	
}

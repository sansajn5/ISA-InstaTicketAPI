package com.isa.instaticketapi.web.rest;

import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.jwt.JWTConfigurer;
import com.isa.instaticketapi.security.jwt.TokenProvider;
import com.isa.instaticketapi.service.MailService;
import com.isa.instaticketapi.service.UserService;
import com.isa.instaticketapi.service.dto.LoginDTO;
import com.isa.instaticketapi.service.dto.UserDTO;
import com.isa.instaticketapi.service.mapper.UserMapper;
import com.isa.instaticketapi.web.rest.vm.AccountResource.JWTTokenResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;


/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * POST  /signup : register new user
     *
     * @param userDTO object providing information about new user
     * @throws HttpStatus 400 (Bad Request) if username is already used
     * @throws HttpStatus 400 (Bad Request) if email is already used
     */
    @ApiOperation(value = "Registration new user", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succesfully created user"),
            @ApiResponse(code = 400, message = "Some attribute is already in use"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Error on server side"),
            @ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
    @Transactional
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signupAccount(@Valid @RequestBody UserDTO userDTO){
        log.debug("REST request to sign-up User : {}", userDTO);

        userRepository.findOneByUsername(userDTO.getUsername()).ifPresent( user -> {throw new IllegalArgumentException("Username is already used");});
        userRepository.findOneByEmailIgnoreCase(userDTO.getUsername()).ifPresent( user -> {throw new IllegalArgumentException("Email is already used");});
        User user = userMapper.userDTOToUser(userDTO);
        userService.signupUser(user,userDTO.getPassword());
        mailService.sendActivationEmail(user);
    }

    /**
     * POST  /signin : Authenticate user
     *
     * @param loginDTO object providing information required for login
     * @return
     */
    @ApiOperation(value = "Authenticate user", response = JWTTokenResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succesfully signed in"),
            @ApiResponse(code = 400, message = "Wrong credentials"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Error on server side"),
            @ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
    @PostMapping("/authenticate")
    public ResponseEntity<JWTTokenResponse> authorize(@Valid @RequestBody LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginDTO.isRememberMe() == null) ? false : loginDTO.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTTokenResponse(jwt), httpHeaders, HttpStatus.OK);
    }



    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @ApiOperation(value = "Check if current user is authenticated", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Error on server side"),
            @ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws HttpStatus 400 (Bad Request) if the user couldn't be activated
     */
    @ApiOperation(value = "Activate the registered user", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "User couldn't be activated"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Error on server side"),
            @ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("No user was found for this reset key");
        }
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws HttpStatus 400 (Bad Request) if the user couldn't be returned
     */
    @ApiOperation(value = "Get the current user", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "User couldn't be returned"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Error on server side"),
            @ApiResponse(code = 503, message = "Server is unavilable or under maintance")})
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
                .map(UserDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("User could not be found"));
    }
}






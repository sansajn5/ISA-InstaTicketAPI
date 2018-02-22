package com.isa.instaticketapi.web.rest;

import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.service.UserService;
//import com.isa.instaticketapi.web.rest.errors.EmailAlreadyUsedException;
//import com.isa.instaticketapi.web.rest.errors.LoginAlreadyUsedException;
import com.isa.instaticketapi.web.rest.vm.AccountResource.JWTTokenResponse;
import com.isa.instaticketapi.web.rest.vm.UserResource.FriendsResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  UserService userService;


    /**
     * GET /getMyFriends : Get list of friends
     *
     * @return
     */
    @ApiOperation(value = "Get list of friends", response = FriendsResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Succesfully retrived list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Error on server side"),
            @ApiResponse(code = 503, message = "Server is unavilable or under maintance") })
    @GetMapping("/getMyFriends")
    public  ResponseEntity<FriendsResponse> getMyFriends(){
        //TODO
        return null;
    }

}

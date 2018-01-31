package com.isa.instaticketapi.web.rest;

import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.AuthoritiesConstants;
import com.isa.instaticketapi.service.UserService;
import com.isa.instaticketapi.service.dto.UserDTO;
//import com.isa.instaticketapi.web.rest.errors.EmailAlreadyUsedException;
//import com.isa.instaticketapi.web.rest.errors.LoginAlreadyUsedException;
import com.isa.instaticketapi.web.rest.vm.AccountResource.SignUpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

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

}

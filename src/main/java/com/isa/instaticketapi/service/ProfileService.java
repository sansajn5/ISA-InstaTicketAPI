package com.isa.instaticketapi.service;

import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import org.hibernate.mapping.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing current users
 *
 * @author sansajn
 */
@Service
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private UserRepository userRepository;


}

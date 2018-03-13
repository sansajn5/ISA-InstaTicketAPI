package com.isa.instaticketapi.service;


import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.web.rest.vm.UserResource.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing user.
 *
 * @author sansajn
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<Friend> findMyFriends() {
        User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
        List<Friend> friends = new ArrayList<Friend>();
        userRepository.findAllFriendsById(logged.getId()).forEach(user -> {
            friends.add(new Friend(user.getUsername(),user.getEmail()));
        });
        return friends;
    }

}

package com.isa.instaticketapi.service;


import com.isa.instaticketapi.domain.Friends;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.FriendRequestRepository;
import com.isa.instaticketapi.repository.FriendsRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.web.rest.vm.UserResource.Friend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendsRepository friendsRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;


    /**
     * Finding friends for currently logged user.
     *
     * @return
     */
    public List<Friend> findMyFriends() {
        log.debug("Started searching friends for logged user");
        User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
        List<Friends> friendsAsUsers = friendsRepository.findAllByUser(logged);
        List<Friend> friends = new ArrayList<>();
        friendsAsUsers.forEach(friend -> {
            log.debug("User {} , and email {}", friend.getFriend().getUsername(), friend.getFriend().getEmail());
            friends.add(new Friend(friend.getFriend().getUsername(), friend.getFriend().getEmail()));
        });
        return friends;
    }

    public User sendFriendRequest(String email) {
        return null;
    }


}

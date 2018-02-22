package com.isa.instaticketapi.config;

import com.isa.instaticketapi.domain.Authority;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.AuthorityRepository;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for making initial seed data
 *
 * @author sansajn
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger log = LoggerFactory.getLogger(DataLoader.class);


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        seedAuthorities();
        seedUsers();
    }

    /**
     *  Setting up authorities for common init database
     */
    public void seedAuthorities() {
        log.info("Starting seed for authority");
        Authority superAdmin = new Authority();
        Authority admin = new Authority();
        Authority user = new Authority();
        Authority guest = new Authority();

        superAdmin.setName(AuthoritiesConstants.SUPER_ADMIN);
        admin.setName(AuthoritiesConstants.ADMIN);
        user.setName(AuthoritiesConstants.USER);
        guest.setName(AuthoritiesConstants.ANONYMOUS);

        authorityRepository.save(superAdmin);
        authorityRepository.save(admin);
        authorityRepository.save(user);
        authorityRepository.save(guest);

        log.info("Seeds for authority are completed");
    }

    /**
     *  Setting up users for common init database
     */
    public void seedUsers() {
        log.info("Strting seed for users");
        Set<Authority> authoritiesSuperAdmin = new HashSet<>();
        Set<Authority> authoritiesAdmin = new HashSet<>();
        Set<Authority> authoritiesUser = new HashSet<>();
        Set<Authority> authoritiesGuest = new HashSet<>();

        Authority authoritySuperAdmin = authorityRepository.findOne(AuthoritiesConstants.SUPER_ADMIN);
        Authority authorityAdmin = authorityRepository.findOne(AuthoritiesConstants.ADMIN);
        Authority authorityUser = authorityRepository.findOne(AuthoritiesConstants.USER);
        Authority authorityGuest = authorityRepository.findOne(AuthoritiesConstants.ANONYMOUS);

        authoritiesSuperAdmin.add(authoritySuperAdmin);
        authoritiesSuperAdmin.add(authorityAdmin);
        authoritiesSuperAdmin.add(authorityAdmin);
        authoritiesSuperAdmin.add(authorityUser);
        authoritiesSuperAdmin.add(authorityGuest);

        authoritiesAdmin.add(authorityAdmin);
        authoritiesAdmin.add(authorityUser);
        authoritiesAdmin.add(authorityGuest);

        authoritiesUser.add(authorityUser);
        authoritiesUser.add(authorityGuest);

        User superAdmin = new User();
        User sansajn = new User();
        User milica = new User();
        User dejan = new User();

        superAdmin.setUsername("super");
        superAdmin.setPassword(passwordEncoder.encode("super"));
        superAdmin.setFirstName("SUPER_ADMIN");
        superAdmin.setLastName("SUPER");
        superAdmin.setAuthorities(authoritiesSuperAdmin);
        superAdmin.setActivated(true);
        superAdmin.setAuthorities(authoritiesSuperAdmin);
        superAdmin.setEmail("super@super.com");

        sansajn.setUsername("sansajn");
        sansajn.setPassword(passwordEncoder.encode("sansajn"));
        sansajn.setFirstName("Nemanja");
        sansajn.setLastName("Mudrinic");
        sansajn.setAuthorities(authoritiesAdmin);
        sansajn.setActivated(true);
        sansajn.setEmail("sansajn@super.com");

        dejan.setUsername("dejan");
        dejan.setPassword(passwordEncoder.encode("dejan"));
        dejan.setFirstName("Dejan");
        dejan.setLastName("Mijic");
        dejan.setAuthorities(authoritiesAdmin);
        dejan.setActivated(true);
        dejan.setEmail("dejan@super.com");

        milica.setUsername("milica");
        milica.setPassword(passwordEncoder.encode("milica"));
        milica.setFirstName("Milica");
        milica.setLastName("Kovacevic");
        milica.setAuthorities(authoritiesAdmin);
        milica.setActivated(true);
        milica.setEmail("milica@super.com");

        userRepository.save(superAdmin);
        userRepository.save(sansajn);
        userRepository.save(milica);
        userRepository.save(dejan);

        log.info("Seeds for users are completed");

    }

}

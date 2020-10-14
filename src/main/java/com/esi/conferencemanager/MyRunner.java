package com.esi.conferencemanager;

import com.esi.conferencemanager.Dto.UserRegistration;
import com.esi.conferencemanager.Model.Conference;
import com.esi.conferencemanager.Model.Roles;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Services.ConferenceService;
import com.esi.conferencemanager.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {
    private final UserService userService ;
    private final ConferenceService conferenceService ;

    public MyRunner(UserService userService, ConferenceService conferenceService) {
        this.userService = userService;
        this.conferenceService = conferenceService;
    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // add users with diff roles - add conferences
        User admin = new User();
        admin.setFName("Admin");
        admin.setLName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setRole(Roles.ROLE_ADMIN);
        admin.setUser_reviews(null);
        admin.setUserPapers(null);
        admin.setPassword(passwordEncoder.encode("1234"));
        userService.save(admin);
        User reviewer = new User();
        reviewer.setFName("Reviewer");
        reviewer.setLName("Reviewer");
        reviewer.setEmail("reviewer@gmail.com");
        reviewer.setRole(Roles.ROLE_REVIEWER);
        reviewer.setUser_reviews(null);
        reviewer.setUserPapers(null);
        reviewer.setPassword(passwordEncoder.encode("1234"));
        userService.save(reviewer);
        User reviewer1 = new User();
        reviewer1.setFName("Reviewer1");
        reviewer1.setLName("Reviewer1");
        reviewer1.setEmail("reviewer1@gmail.com");
        reviewer1.setRole(Roles.ROLE_REVIEWER);
        reviewer1.setUser_reviews(null);
        reviewer1.setUserPapers(null);
        reviewer1.setPassword(passwordEncoder.encode("1234"));
        userService.save(reviewer1);
        userService.registration(new UserRegistration("author", "author", "author@gmail.com", "12345"));




    }
}

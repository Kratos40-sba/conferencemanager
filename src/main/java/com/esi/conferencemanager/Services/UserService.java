package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Dto.UserRegistration;
import com.esi.conferencemanager.Model.*;
import com.esi.conferencemanager.Repositories.AssignmentRepo;
import com.esi.conferencemanager.Repositories.PaperRepo;
import com.esi.conferencemanager.Repositories.ReviewRepo;
import com.esi.conferencemanager.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PaperRepo paperRepo ;
    private final ReviewRepo reviewRepo ;
    private final AssignmentRepo assignmentRepo ;
    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PaperRepo paperRepo, ReviewRepo reviewRepo, AssignmentRepo assignmentRepo) {
        this.userRepo = userRepo;
        this.paperRepo = paperRepo;
        this.reviewRepo = reviewRepo;
        this.assignmentRepo = assignmentRepo;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Roles roles) {
        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roles.getAuthority());
        return Collections.singletonList(simpleGrantedAuthority);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthorities(user.getRole()));
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    public User registration(UserRegistration userRegistration){
        User user = new User();
        user.setFName(userRegistration.getFirst_name());
        user.setLName(userRegistration.getLast_name());
        user.setEmail(userRegistration.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        user.setRole(Roles.ROLE_AUTHOR);
        user.setUserPapers(null);
        user.setUser_reviews(null);
        user.setPaper_for_reviewing(null);
        user.setFeedbacks(null);
        return userRepo.save(user) ;
    }
    public void makeUserAdmin(Long user_Id){
        User user = userRepo.getOne(user_Id);
        user.setRole(Roles.ROLE_ADMIN);
        userRepo.save(user);
    }
    public List<User> getReviewers(){
        List<User> users = userRepo.findAll();
        List<User> revewers = new ArrayList<>();
        for(User u : users){
            if (u.getRole().equals(Roles.ROLE_REVIEWER)){
                revewers.add(u);
            }
        }
        return revewers;
    }
    public List<User> getAdmins(){
        List<User> users = userRepo.findAll();
        List<User> admins = new ArrayList<>();
        for(User u : users){
            if (u.getRole().equals(Roles.ROLE_ADMIN)){
                admins.add(u);
            }
        }
        return admins;
    }
    public void makeUserReviewer(Long user_Id){
        User user = userRepo.getOne(user_Id);
        user.setRole(Roles.ROLE_REVIEWER);
        userRepo.save(user);
    }
    public void makeUserAuthor(Long user_Id){
        User user = userRepo.getOne(user_Id);
        user.setRole(Roles.ROLE_AUTHOR);
        userRepo.save(user);
    }
    public void deleteUser(Long user_id){
        User user = userRepo.getOne(user_id);
        List<Review> reviews = user.getUser_reviews();
        List<Paper> papers = user.getUserPapers();

        user.setPaper_for_reviewing(null);
        for(Paper p : papers){
            assignmentRepo.deleteAll(p.getAssignments());
        }

        reviewRepo.deleteAll(reviews);
        paperRepo.deleteAll(papers);
        userRepo.delete(user);
        assignmentRepo.deleteAll();

    }
    public void save(User user) {
        userRepo.save(user);
    }
    public User getByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public void updateProfile (Long id){

         userRepo.save( userRepo.getOne(id));
    }
    public User getOne(Long id){
        return userRepo.getOne(id);
    }


}

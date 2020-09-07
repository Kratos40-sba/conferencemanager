package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Dto.UserRegistration;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.Review;
import com.esi.conferencemanager.Model.Roles;
import com.esi.conferencemanager.Model.User;
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PaperRepo paperRepo ;
    private final ReviewRepo reviewRepo ;
    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PaperRepo paperRepo, ReviewRepo reviewRepo) {
        this.userRepo = userRepo;
        this.paperRepo = paperRepo;
        this.reviewRepo = reviewRepo;

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
        return userRepo.save(user) ;
    }
    public void makeUserAdmin(Long user_Id){
        User user = userRepo.getOne(user_Id);
        user.setRole(Roles.ROLE_ADMIN);
        userRepo.save(user);
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
        reviewRepo.deleteAll(reviews);
        paperRepo.deleteAll(papers);
        userRepo.delete(user);
    }
    public void save(User user) {
        userRepo.save(user);
    }
    public User getByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public void updateProfile (User user){
        user.setId(user.getId());
        user.setRole(user.getRole());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setFName(user.getFName());
        user.setLName(user.getLName());
        userRepo.save(user);
    }
    public User getOne(Long id){
        return userRepo.getOne(id);
    }


}

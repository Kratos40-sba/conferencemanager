package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Model.Message;
import com.esi.conferencemanager.Repositories.FeedbackRepo;
import com.esi.conferencemanager.Repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
public class FeedbackService {
    private final FeedbackRepo feedbackRepo ;
    private final UserRepo userRepo ;
    public FeedbackService(FeedbackRepo feedbackRepo, UserRepo userRepo) {
        this.feedbackRepo = feedbackRepo;
        this.userRepo = userRepo;
    }
    public void createFeedback (Long user_id , Message feedback, Principal principal){
        feedback.setReciver(userRepo.getOne(user_id).getEmail());
        feedback.setSender(userRepo.findByEmail(principal.getName()).getEmail());
        feedback.setUser_message(userRepo.getOne(user_id));
        feedback.setDate(LocalDateTime.now());
         feedbackRepo.save(feedback);
    }

}

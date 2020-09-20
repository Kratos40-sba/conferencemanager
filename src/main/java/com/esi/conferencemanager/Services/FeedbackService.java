package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Model.Message;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Repositories.FeedbackRepo;
import com.esi.conferencemanager.Repositories.PaperRepo;
import com.esi.conferencemanager.Repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
public class FeedbackService {
    private final FeedbackRepo feedbackRepo ;
    private final UserRepo userRepo ;
    private final PaperRepo paperRepo ;
    public FeedbackService(FeedbackRepo feedbackRepo, UserRepo userRepo, PaperRepo paperRepo) {
        this.feedbackRepo = feedbackRepo;
        this.userRepo = userRepo;
        this.paperRepo = paperRepo;
    }
    public void createFeedback (Long user_id , Message feedback, Principal principal){
        feedback.setReciver(userRepo.getOne(user_id).getEmail());
        feedback.setSender(userRepo.findByEmail(principal.getName()).getEmail());
        feedback.setUser_message(userRepo.getOne(user_id));
        feedback.setDate(LocalDateTime.now());
         feedbackRepo.save(feedback);
    }
    public void acceptedPaperFeedback(Message feedback , Long paper_id , Principal principal){
        Paper paper = paperRepo.getOne(paper_id);
        feedback.setReciver(paper.getAuthor().getEmail());
        feedback.setUser_message(paper.getAuthor());
        feedback.setSender(userRepo.findByEmail(principal.getName()).getEmail());
        feedback.setDate(LocalDateTime.now());
        feedback.setSubject("Paper-Status");
        feedback.setBody("Your paper have been accepted");
        feedbackRepo.save(feedback);

    }
    public void refusedPaperFeedback(Message feedback , Long paper_id , Principal principal){
        Paper paper = paperRepo.getOne(paper_id);
        feedback.setReciver(paper.getAuthor().getEmail());
        feedback.setUser_message(paper.getAuthor());
        feedback.setSender(userRepo.findByEmail(principal.getName()).getEmail());
        feedback.setDate(LocalDateTime.now());
        feedback.setSubject("Paper-Status");
        feedback.setBody("Your paper have been refused");
        feedbackRepo.save(feedback);

    }

}

package com.esi.conferencemanager.Controllers;

import com.esi.conferencemanager.Model.Message;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.Roles;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Services.ConferenceService;
import com.esi.conferencemanager.Services.FeedbackService;
import com.esi.conferencemanager.Services.PaperService;
import com.esi.conferencemanager.Services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class PublicController {
    // see all conferences
    // download paper
    private final UserService userService ;
    private final ConferenceService conferenceService;
    private final PaperService paperService;
    private final FeedbackService feedbackService ;

    public PublicController(UserService userService, ConferenceService conferenceService, PaperService paperService, FeedbackService feedbackService) {
        this.userService = userService;
        this.conferenceService = conferenceService;
        this.paperService = paperService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/home")
    public String showHome(Principal current_user, Model model){
        User user = userService.getByEmail(current_user.getName());
        model.addAttribute("user",user);
        model.addAttribute("papers",paperService.myPapers(current_user));
        model.addAttribute("papers_rev",paperService.getReviewerPapers(current_user));
        model.addAttribute("feedbacks",user.getFeedbacks());
        return "home";
    }
    @GetMapping("/conference-list")
    public String conferencesList(Model model){
        model.addAttribute("conferences",conferenceService.getAllConferences());
        return "conference_list";
    }
    @GetMapping("/download-file/{paper_id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("paper_id") Long id){
        Paper file = paperService.getOnePaper(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+file.getFileName()+"\"")
                .body(new ByteArrayResource(file.getData()));
    }
    @GetMapping("/p/send-feedback-to-admin")
    public String getAdmins(Model model){
        model.addAttribute("admins",userService.getAdmins());
        return "send_feedback_to_admin";
    }
    @GetMapping("/p/send-feedback/{user_id}")
    public String getFeedbackForm(@PathVariable("user_id")  Long user_id ,Model model){
        model.addAttribute("feedback", new Message());
        User user = userService.getOne(user_id);
        model.addAttribute("user",user);
        model.addAttribute("feedbacks",user.getFeedbacks());
        return "feedback_form";
    }
    @PostMapping("/p/send-feedback/{user_id}")
    public String createFeedback(@PathVariable("user_id") Long user_id , @ModelAttribute Message feedback , Principal principal){
        feedbackService.createFeedback(user_id,feedback,principal);
        User current_user = userService.getByEmail(principal.getName());
        if (current_user.getRole().equals(Roles.ROLE_ADMIN)){
            return "redirect:/admin/users-list";
        }else{
            return "redirect:/p/send-feedback-to-admin";
        }


    }
    @GetMapping("/error")
    public String errResp (){
        return "err";
}


}

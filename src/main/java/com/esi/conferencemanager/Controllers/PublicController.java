package com.esi.conferencemanager.Controllers;

import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Services.ConferenceService;
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

    public PublicController(UserService userService, ConferenceService conferenceService, PaperService paperService) {
        this.userService = userService;
        this.conferenceService = conferenceService;
        this.paperService = paperService;
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
    @GetMapping("/profile-edit/{user_id}")
    public String editProfile(@PathVariable("user_id") Long id , Model model){
       model.addAttribute("user",userService.getOne(id));
        return"edit_profile";
    }
    @PostMapping("/profile-edit/{user_id}")
    public String editProfile(@ModelAttribute("user") User user){
        userService.updateProfile(user);
        return "redirect:/home";
    }
    @GetMapping("/download-file/{paper_id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("paper_id") Long id){
        Paper file = paperService.getOnePaper(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+file.getFileName()+"\"")
                .body(new ByteArrayResource(file.getData()));
    }



}

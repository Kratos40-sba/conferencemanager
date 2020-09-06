package com.esi.conferencemanager.Controllers;

import com.esi.conferencemanager.Model.Conference;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Services.ConferenceService;
import com.esi.conferencemanager.Services.PaperService;
import com.esi.conferencemanager.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final PaperService paperService ;
    private final ConferenceService conferenceService ;
    private final UserService userService ;

    public AuthorController(PaperService paperService, ConferenceService conferenceService, UserService userService) {
        this.paperService = paperService;
        this.conferenceService = conferenceService;
        this.userService = userService;
    }

    @GetMapping("/create-paper/{conference_id}")
    public String getPaperForm(@PathVariable("conference_id") Long conference_id , Model model){
        Conference conference = conferenceService.getConference(conference_id);
        model.addAttribute("paper",new Paper());
        model.addAttribute("conference",conference);
        model.addAttribute("papers",conference.getPapers());
        return "paper_panel";
    }
    @PostMapping("/create-paper/{conference_id}")
    public String createPaper(@ModelAttribute Paper paper,@PathVariable("conference_id") Long conference_id , @RequestParam("file") MultipartFile file , Principal principal) {
        User author = userService.getByEmail(principal.getName());
        paperService.createPaper(file,conference_id,author,paper);
        return "redirect:/author/create-paper/{conference_id}";
    }
    // add a paper to a specefic conference
    // update / delete a paper
    // check your paper status
    // upload paper files
}
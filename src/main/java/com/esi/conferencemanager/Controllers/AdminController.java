package com.esi.conferencemanager.Controllers;

import com.esi.conferencemanager.Model.Conference;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.Review;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Services.ConferenceService;
import com.esi.conferencemanager.Services.PaperService;
import com.esi.conferencemanager.Services.UserService;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService ;
    private final ConferenceService conferenceService ;
    private final PaperService paperService;
    public AdminController(UserService userService, ConferenceService conferenceService, PaperService paperService) {
        this.userService = userService;
        this.conferenceService = conferenceService;
        this.paperService = paperService;
    }
    @GetMapping("/users-list")
    public String getUsers(Model model){
        model.addAttribute("users",userService.getAllUsers());
        return "users";
    }
    @GetMapping("/user-delete/{user_id}")
    public String deleteUser(@PathVariable("user_id") Long user_id){
        userService.deleteUser(user_id);
        return "redirect:/admin/users-list";
    }
    @GetMapping("/user-details/{user_id}")
    public String getUser(@PathVariable("user_id") Long user_id ,Model model){
        User user = userService.getOne(user_id);
        List<Paper> papers = user.getUserPapers();
        List<Review> reviews = user.getUser_reviews();
        model.addAttribute("user",user);
        model.addAttribute("papers",papers);
        model.addAttribute("reviews",reviews);
        return "user_detail";
    }
    @GetMapping("/make-user-admin/{user_id}")
    public String makeUserAdmin(@PathVariable("user_id") Long user_id){
        userService.makeUserAdmin(user_id);
        return "redirect:/admin/users-list";
    }
    @GetMapping("/make-user-reviewer/{user_id}")
    public String makeUserReviewer(@PathVariable("user_id") Long user_id){
        userService.makeUserReviewer(user_id);
        return "redirect:/admin/users-list";
    }
    @GetMapping("/make-user-author/{user_id}")
    public String makeUserAuthor(@PathVariable("user_id") Long user_id){
        userService.makeUserAuthor(user_id);
        return "redirect:/admin/users-list";
    }
    @GetMapping("/create-conference")
    public String getConferenceForm(@PathVariable("conference_id") Optional<Long> conference_id , Model model){
        Conference conference = new Conference();
        model.addAttribute("conference",conference);
        model.addAttribute("conferences",conferenceService.getAllConferences());
        return "conference_panel";
    }
    @PostMapping("/create-conference")
    public String createConference(@ModelAttribute Conference conference){
        conferenceService.createConference(conference);
        return "redirect:/admin/create-conference";
    }
    @GetMapping("/update-conference/{id}")
    public String getConferenceUpdateForm(@PathVariable("id")Long id,Model model){
        model.addAttribute("conference",conferenceService.getConference(id));
        return "conference_edit";
    }
    @PostMapping("/update-conference/{id}")
    public String updateConference(@PathVariable("id") Long id,@ModelAttribute("conference") Conference conference){
        conferenceService.updateConference(conference);
        return "redirect:/conference-list";
    }
    @GetMapping("/delete-conference/{conference_id}")
    public String deleteConference(@PathVariable("conference_id") Long conference_id){
        conferenceService.deleteConference(conference_id);
        return "redirect:/admin/create-conference";
    }
    @GetMapping("/close-conference/{conference_id}")
    public String closeConference(@PathVariable("conference_id") Long conference_id){
        conferenceService.closeConference(conference_id);
        return "redirect:/admin/create-conference";
    }
    @GetMapping("/papers-submitted")
    public String getPapers(Model model){
        model.addAttribute("papers",paperService.getPapers());
        return "admin_papers";
    }
    @GetMapping("/paper-detail/{paper_id}")
    public String getPaperDetail(@PathVariable("paper_id") Long paper_di, Model model){
        Paper paper = paperService.getOnePaper(paper_di);
        String conference = paper.getConference().getTitre();
        List<Review> reviews = paper.getReviews();
        String author = paper.getAuthor().getEmail();
        model.addAttribute("paper",paper);
        model.addAttribute("author",author);
        model.addAttribute("conference",conference);
        model.addAttribute("reviews",reviews);
        return "paper_detail";
    }
    @GetMapping("/papers-conference/{conf_id}")
    public String GetPapers(@PathVariable("conf_id") Long conf_id , Model model){
        model.addAttribute("papers",conferenceService.getConference(conf_id).getPapers());
        return "papers_conference";
    }



    // user managment  delete / make admin/reviewer / send email to a user / list all users
    // conference managment / create - update - delete
    // shcudel managment
    // reviews managment / assigne paper to reviewer / accept a paper based on review / show all reviews

}

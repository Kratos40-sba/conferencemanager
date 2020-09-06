package com.esi.conferencemanager.Controllers;

import com.esi.conferencemanager.Model.Review;
import com.esi.conferencemanager.Services.PaperService;
import com.esi.conferencemanager.Services.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/reviewer")
public class ReviewerController {
    private final PaperService paperService ;
    private final ReviewService reviewService ;

    public ReviewerController(PaperService paperService, ReviewService reviewService) {
        this.paperService = paperService;
        this.reviewService = reviewService;
    }

    // create/update/delete a review
    // consulte your reviews pages
    // download paper
    @GetMapping("create-review/{paper_id}")
    public String getReviewForm(@PathVariable("paper_id") Long paper_id , Model model){
        model.addAttribute("review",new Review());
        model.addAttribute("paper",paperService.getOnePaper(paper_id));
        return "review_form";
    }
    @PostMapping("create-review/{paper_id}")
    public String createReview(@PathVariable("paper_id") Long paper_id , @ModelAttribute("review")Review review, Principal principal){
        reviewService.createReview(paper_id,review,principal);
        return "redirect:/home";
    }

}
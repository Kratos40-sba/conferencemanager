package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.Review;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Repositories.PaperRepo;
import com.esi.conferencemanager.Repositories.ReviewRepo;
import com.esi.conferencemanager.Repositories.UserRepo;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepo reviewRepo ;
    private final PaperRepo paperRepo ;
    private final UserRepo userRepo ;

    public ReviewService(ReviewRepo reviewRepo, PaperRepo paperRepo, UserRepo userRepo) {
        this.reviewRepo = reviewRepo;
        this.paperRepo = paperRepo;
        this.userRepo = userRepo;
    }


    public List<Review> getAllReviews(){
        return reviewRepo.findAll();
    }
    public Review createReview (Long paper_Id ,Review review, Principal current_user){
        Paper paper = paperRepo.getOne(paper_Id);
        review.setReviewed_paper(paper);
        paper.setReviewed(true);
        User reviewer = userRepo.findByEmail(current_user.getName());
        review.setReviewer(reviewer);
        review.setReviewDate(LocalDateTime.now());
        review.setComment(review.getComment());
        double s = (review.getMark1()+review.getMark2()+review.getMark3()+ review.getMark4())/4;
        review.setMark(s);
        return  reviewRepo.save(review);
    }
    public void deleteReview(Long review_Id){
        Review review = reviewRepo.getOne(review_Id);
        review.setReviewer(null);
        review.setReviewed_paper(null);
        reviewRepo.deleteById(review_Id);
    }
    public Review updateReview(Long review_Id){
        return reviewRepo.save(reviewRepo.getOne(review_Id));
    }

}

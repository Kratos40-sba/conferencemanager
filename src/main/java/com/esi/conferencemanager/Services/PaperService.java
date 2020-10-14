package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Dto.Paper_admin_response;
import com.esi.conferencemanager.Dto.Papers_Reviewer;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaperService {
    private final PaperRepo paperRepo ;
    private final ConferenceRepo conferenceRepo ;
    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo ;
    private final AssignmentRepo assignmentRepo;
    public PaperService(PaperRepo paperRepo, ConferenceRepo conferenceRepo, ReviewRepo reviewRepo, UserRepo userRepo, AssignmentRepo assignmentRepo) {
        this.paperRepo = paperRepo;
        this.conferenceRepo = conferenceRepo;
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
        this.assignmentRepo = assignmentRepo;
    }
    public List<Paper> getAllPapers(){
        return paperRepo.findAll();
    }
    public Paper getOnePaper(Long paper_Id){
        return paperRepo.getOne(paper_Id);
    }
    public void  deletePaper(Long paper_Id){
        Paper paper = paperRepo.getOne(paper_Id);
       // assignmentRepo.deleteAll(paper.getAssignments());
       // reviewRepo.deleteAll(paper.getReviews());
        paperRepo.delete(paper);
    }
    public Paper createPaper(MultipartFile file , Long conference_Id , User author,Paper paper){
        String filename = file.getOriginalFilename();
        try {
            paper.setData(file.getBytes());
            paper.setFileName(filename);
            paper.setFileType(file.getContentType());
            paper.setReviews(null);
            paper.setConference(conferenceRepo.getOne(conference_Id));
            paper.setAuthor(author);
            paper.setSubb_date(LocalDateTime.now());
            paper.setStatus(false);
            paper.setReviewed(false);
            return paperRepo.save(paper);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("problem");
        }
        return null;
    }
    public void updatePaper(Paper paper){
        paperRepo.save(paper);
    }
    public List<Paper_admin_response> getPapers(){
        List<Paper> papers = paperRepo.findAll();
        List<Paper_admin_response> rsponse = new ArrayList<>();
        papers.forEach(paper -> rsponse.add(new Paper_admin_response(paper.getId(),paper.getTitre(),paper.getConference().getTitre(), paper.getAuthor().getEmail(),paper.getSubb_date())));
        return rsponse ;
    }
    public List<Paper> myPapers(Principal principal){
        User author = userRepo.findByEmail(principal.getName());
        List<Paper> papers = author.getUserPapers();
        return papers ;
    }
    public List<Papers_Reviewer> getReviewerPapers(Principal principal){
        User reviewer = userRepo.findByEmail(principal.getName());
        // this should be grabbing papers from assigned papers

        List<Paper> papers = reviewer.getPaper_for_reviewing();
        List<Papers_Reviewer> rsponse = new ArrayList<>();
        papers.forEach(paper -> rsponse.add(new Papers_Reviewer(paper.getId(),paper.getTitre(),paper.getAbstraction(),paper.getConference().getTitre())));
        return rsponse;

    }
    public void acceptPaper (Long id){
        Paper paper = paperRepo.getOne(id);
        paper.setStatus(true );
        paperRepo.save(paper);
    }
    public void refusePaper (Long id){
        Paper paper = paperRepo.getOne(id);
        paper.setStatus(false);
        paperRepo.save(paper);
    }


}

package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Dto.Paper_admin_response;
import com.esi.conferencemanager.Dto.Papers_Reviewer;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Repositories.ConferenceRepo;
import com.esi.conferencemanager.Repositories.PaperRepo;
import com.esi.conferencemanager.Repositories.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaperService {
    private final PaperRepo paperRepo ;
    private final ConferenceRepo conferenceRepo ;
    private final UserRepo userRepo ;
    public PaperService(PaperRepo paperRepo, ConferenceRepo conferenceRepo, UserRepo userRepo) {
        this.paperRepo = paperRepo;
        this.conferenceRepo = conferenceRepo;
        this.userRepo = userRepo;
    }
    public List<Paper> getAllPapers(){
        return paperRepo.findAll();
    }
    public Paper getOnePaper(Long paper_Id){
        return paperRepo.getOne(paper_Id);
    }
    public void  deletePaper(Long paper_Id){
        paperRepo.deleteById(paper_Id);
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
    public List<Papers_Reviewer> getReviewerPapers(){
        // this should be grabbing papers from assigned papers
        List<Paper> papers = paperRepo.findAll();
        List<Papers_Reviewer> rsponse = new ArrayList<>();
        papers.forEach(paper -> rsponse.add(new Papers_Reviewer(paper.getId(),paper.getTitre(),paper.getAbstraction(),paper.getConference().getTitre())));
        return rsponse;

    }

}

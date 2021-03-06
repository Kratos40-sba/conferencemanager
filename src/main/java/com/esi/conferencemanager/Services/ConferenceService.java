package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Model.Conference;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Repositories.ConferenceRepo;
import com.esi.conferencemanager.Repositories.PaperRepo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConferenceService {
    private final ConferenceRepo conferenceRepo ;
    private final PaperRepo paperRepo ;

    public ConferenceService(ConferenceRepo conferenceRepo, PaperRepo paperRepo) {
        this.conferenceRepo = conferenceRepo;
        this.paperRepo = paperRepo;
    }
    public List<Conference> getAllConferences(){
        return conferenceRepo.findAll();
    }
    public Conference createConference(Conference conference){
            conference.setOpend(true);
            conference.setPapers(null);
            return conferenceRepo.save(conference);
    }
    public void deleteConference(Long id) {
        Conference conference = conferenceRepo.getOne(id);
        List<Paper> papers = conference.getPapers();
        paperRepo.deleteAll(papers);
        conferenceRepo.deleteById(id);
    }
    public Conference getConference(Long id){
        return conferenceRepo.getOne(id);
    }
    public void updateConference(Conference conference){
        conference.setOpend(true);
        conferenceRepo.save(conference);
    }
    public Conference closeConference(Long conference_Id){
        Conference conference = conferenceRepo.getOne(conference_Id);
        if(conference.isOpend()){
            conference.setOpend(false);
        }else {
            conference.setOpend(true);
        }

        return conferenceRepo.save(conference);
    }


}

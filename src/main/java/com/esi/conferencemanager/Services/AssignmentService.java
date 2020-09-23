package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Model.Assignment;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Repositories.AssignmentRepo;
import com.esi.conferencemanager.Repositories.PaperRepo;
import com.esi.conferencemanager.Repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepo assignmentRepo ;
    private final PaperRepo paperRepo ;
    private final UserRepo userRepo ;

    public AssignmentService(AssignmentRepo assignmentRepo, PaperRepo paperRepo, UserRepo userRepo) {
        this.assignmentRepo = assignmentRepo;
        this.paperRepo = paperRepo;
        this.userRepo = userRepo;
    }
    public void createAssigment(Long paper_id , List<Long> reviewers_id , Assignment assignment) {
        List<User> reviewers = new ArrayList<User>();
        Paper paper = paperRepo.getOne(paper_id);
        assignment.setPaper_assigned(paper);
        for (Long id : reviewers_id){
         reviewers.add(userRepo.getOne(id));

        }
        assignment.setReviewer_assigned(reviewers);
        assignment.setAssignement_date(LocalDateTime.now());
         assignmentRepo.save(assignment);

    }
    public List<Assignment> assignmentList(){
        return  assignmentRepo.findAll();
    }
    public void deletAssignment(Long id){
        assignmentRepo.deleteById(id);
    }

}

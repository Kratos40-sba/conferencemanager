package com.esi.conferencemanager.Services;

import com.esi.conferencemanager.Model.Assignment;
import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.User;
import com.esi.conferencemanager.Repositories.AssignmentRepo;
import com.esi.conferencemanager.Repositories.PaperRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepo assignmentRepo ;
    private final PaperRepo paperRepo ;

    public AssignmentService(AssignmentRepo assignmentRepo, PaperRepo paperRepo) {
        this.assignmentRepo = assignmentRepo;
        this.paperRepo = paperRepo;
    }
    public Assignment createAssigment(Long paper_id , List<User> reviewers , Assignment assignment) {
        Paper paper = paperRepo.getOne(paper_id);
        assignment.setPaper_assigned(paper);
        assignment.setReviewer_assigned(reviewers);
        assignment.setAssignement_date(LocalDateTime.now());
        return assignmentRepo.save(assignment);

    }
    public List<Assignment> assignmentList(){
        return  assignmentRepo.findAll();
    }
    public void deletAssignment(Long id){
        assignmentRepo.deleteById(id);
    }

}

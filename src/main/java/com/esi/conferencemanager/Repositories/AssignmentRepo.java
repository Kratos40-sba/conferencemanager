package com.esi.conferencemanager.Repositories;

import com.esi.conferencemanager.Model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignment,Long> {
}

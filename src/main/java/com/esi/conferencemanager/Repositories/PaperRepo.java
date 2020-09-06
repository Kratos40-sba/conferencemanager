package com.esi.conferencemanager.Repositories;

import com.esi.conferencemanager.Model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepo extends JpaRepository<Paper,Long> {
}

package com.esi.conferencemanager.Repositories;

import com.esi.conferencemanager.Model.Paper;
import com.esi.conferencemanager.Model.Review;
import com.esi.conferencemanager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review,Long> {
    public Boolean existsByReviewed_paperAndAndReviewer(Paper paper, User reviewer);
}

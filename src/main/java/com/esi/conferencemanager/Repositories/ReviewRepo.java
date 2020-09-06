package com.esi.conferencemanager.Repositories;

import com.esi.conferencemanager.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review,Long> {
}

package com.esi.conferencemanager.Repositories;

import com.esi.conferencemanager.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepo extends JpaRepository<Message,Long> {
}

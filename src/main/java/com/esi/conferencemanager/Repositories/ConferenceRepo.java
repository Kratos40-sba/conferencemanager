package com.esi.conferencemanager.Repositories;

import com.esi.conferencemanager.Model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ConferenceRepo extends JpaRepository<Conference,Long> {

}

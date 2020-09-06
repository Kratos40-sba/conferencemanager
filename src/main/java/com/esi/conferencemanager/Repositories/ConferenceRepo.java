package com.esi.conferencemanager.Repositories;

import com.esi.conferencemanager.Model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConferenceRepo extends JpaRepository<Conference,Long> {

}

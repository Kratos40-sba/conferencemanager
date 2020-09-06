package com.esi.conferencemanager.Repositories;

import com.esi.conferencemanager.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    public User findByEmail(String email);
}

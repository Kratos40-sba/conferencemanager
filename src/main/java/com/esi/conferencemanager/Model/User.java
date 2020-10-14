package com.esi.conferencemanager.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id ;
    @NonNull
    private String fName , lName ;
    @NonNull
    private String password ;
    @NonNull
    private String email ;
    @OneToMany(mappedBy = "author")
    private List<Paper> userPapers ;
    @ManyToMany
    private List<Paper> paper_for_reviewing ;
    @OneToMany(mappedBy = "reviewer")
    private List<Review> user_reviews ;
    private Roles role ;
    @OneToMany(mappedBy = "user_message")
    private List<Message> feedbacks ;
}

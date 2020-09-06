package com.esi.conferencemanager.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @OneToMany(mappedBy = "reviewer")
    private List<Review> user_reviews ;
    private Roles role ;
}

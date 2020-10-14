package com.esi.conferencemanager.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment implements Serializable {
    @Id
    @GeneratedValue
    private Long id ;
    @OneToOne
    private Paper paper_assigned ;
    @OneToMany
    private List<User> reviewer_assigned ;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime assignement_date ;

}

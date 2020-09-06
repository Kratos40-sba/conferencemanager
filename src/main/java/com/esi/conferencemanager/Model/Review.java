package com.esi.conferencemanager.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Review implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private int mark;
    private String comment;
    private LocalDateTime reviewDate;
    @ManyToOne
    private Paper reviewed_paper ;
    @ManyToOne
    private User reviewer ;

}

package com.esi.conferencemanager.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Conference implements Serializable {
    @Id
    @GeneratedValue
    private Long id ;
    @NonNull
    private String titre ;
    @NonNull
    private String descreption ;
    private boolean opend ;
    private String topic ;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start , end ;
    @OneToMany(mappedBy = "conference")
    private List<Paper> papers ;



}

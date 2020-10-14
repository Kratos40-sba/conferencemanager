package com.esi.conferencemanager.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Paper implements Serializable {
    @Id
    @GeneratedValue
    private Long id ;
    @NonNull
    private String titre ;
    private String abstraction ;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime subb_date ;
    private boolean status , reviewed ;
    private String fileName ;
    private String fileType  ;
    @Lob
    private byte[] data ;
    @ManyToOne
    private Conference conference ;
    @ManyToOne
    private User author ;
    @OneToMany(mappedBy = "reviewed_paper" )
    private List<Review> reviews ;
    public Paper(String fileName , String fileType , byte[] data){
        this.fileName = fileName ;
        this.fileType = fileType ;
        this.data = data ;
    }


}

package com.esi.conferencemanager.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Papers_Reviewer {
    private Long paper_id ;
    private String title , abstraction , conference ;

}

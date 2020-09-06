package com.esi.conferencemanager.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration implements Serializable {
    private String first_name , last_name , email , password ;
}

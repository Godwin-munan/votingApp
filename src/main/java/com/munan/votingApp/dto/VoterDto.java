package com.munan.votingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoterDto {
    private String name;
    private String username;
    private Integer age;
    private String gender;
}

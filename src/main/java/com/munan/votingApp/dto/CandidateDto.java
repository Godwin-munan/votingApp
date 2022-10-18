package com.munan.votingApp.dto;

import com.munan.votingApp.model.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CandidateDto {

    private String name;
    private Integer age;
    private String healthStatus;
    private String position;
    private String party;
}

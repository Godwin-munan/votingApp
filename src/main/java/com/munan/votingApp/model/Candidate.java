package com.munan.votingApp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="candidate")
@NoArgsConstructor
@Getter
@Setter
public class Candidate implements  Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String healthStatus;

    @ManyToOne
    private Position position;

    @ManyToOne
    private Party party;
}

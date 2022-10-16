package com.munan.votingApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vote")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    private Voter voter;

    @ManyToOne
    private Candidate candidate;


}

package com.munan.votingApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="voter")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Voter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private Integer age;

    @ManyToOne
    private Gender gender;


}

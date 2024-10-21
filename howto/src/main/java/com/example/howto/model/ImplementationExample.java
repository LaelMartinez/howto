package com.example.howto.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class ImplementationExample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language; 

    @Lob
    private String exampleCode; 
    
    private String description; 
    

    @ManyToOne
    @JoinColumn(name = "howto_id")
    @JsonBackReference
    private Howto howto; 
}
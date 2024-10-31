package com.example.howto.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class Api {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiName;
    private String description;

    @OneToMany(mappedBy = "api", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Endpoint> endpoints;
    
    @OneToMany(mappedBy = "api", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ImplementationExample> implementationExamples;     
	
}

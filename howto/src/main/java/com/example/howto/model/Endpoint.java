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
public class Endpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String httpMethod;

    @Lob
    private String inputExample;
    @Lob
    private String outputExample;

    @ManyToOne
    @JoinColumn(name = "api_id")
    @JsonBackReference    
    private Api api;

}

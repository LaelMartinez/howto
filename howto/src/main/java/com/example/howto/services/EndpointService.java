package com.example.howto.services;

import com.example.howto.exceptions.ResourceNotFoundException;
import com.example.howto.model.Endpoint;
import com.example.howto.repositories.EndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EndpointService {

    @Autowired
    private EndpointRepository endpointRepository;




    public Optional<Endpoint> getEndpoint(Long id){
        return endpointRepository.findById(id);
    }

    public List<Endpoint> getAllEndpoint(Long idAPI ){
        return endpointRepository.findAll();
    }

    public Endpoint save(Endpoint endpoint){
        return endpointRepository.save(endpoint);
    }

    public ResponseEntity<Void> deleteEndpoint(Long endpointId) {



        Endpoint endpoint = endpointRepository.findById(endpointId)
                .orElseThrow(() -> new ResourceNotFoundException("Endpoint not found"));
        endpointRepository.delete(endpoint);
        return ResponseEntity.noContent().build();
    }

}

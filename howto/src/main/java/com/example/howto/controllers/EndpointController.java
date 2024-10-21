package com.example.howto.controllers;

import com.example.howto.exceptions.ResourceNotFoundException;
import com.example.howto.model.Endpoint;
import com.example.howto.model.Howto;
import com.example.howto.repositories.EndpointRepository;
import com.example.howto.repositories.HowtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/howtos/{howtoId}/endpoints")
public class EndpointController {

    @Autowired
    private HowtoRepository howtoRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @PostMapping
    public Endpoint createEndpoint(@PathVariable Long howtoId, @RequestBody Endpoint endpoint) {
        Howto howto = howtoRepository.findById(howtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        endpoint.setHowto(howto);
        return endpointRepository.save(endpoint);
    }

    @GetMapping
    public List<Endpoint> getAllEndpoints(@PathVariable Long howtoId) {
        return endpointRepository.findByHowtoId(howtoId);
    }

    @PutMapping("/{endpointId}")
    public ResponseEntity<Endpoint> updateEndpoint(@PathVariable Long howtoId, @PathVariable Long endpointId, @RequestBody Endpoint newEndpoint) {
        Howto howto = howtoRepository.findById(howtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        Optional<Endpoint> existingEndpoint = endpointRepository.findById(endpointId);
        if (existingEndpoint.isPresent()) {
            Endpoint endpoint = existingEndpoint.get();
            endpoint.setUrl(newEndpoint.getUrl());
            endpoint.setHttpMethod(newEndpoint.getHttpMethod());
            endpoint.setInputExample(newEndpoint.getInputExample());
            endpoint.setOutputExample(newEndpoint.getOutputExample());
            endpoint.setHowto(howto);

            return ResponseEntity.ok(endpointRepository.save(endpoint));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{endpointId}")
    public ResponseEntity<Void> deleteEndpoint(@PathVariable Long howtoId, @PathVariable Long endpointId) {
        Howto howto = howtoRepository.findById(howtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        Optional<Endpoint> endpoint = endpointRepository.findById(endpointId);
        if (endpoint.isPresent() && endpoint.get().getHowto().getId().equals(howtoId)) {
            endpointRepository.deleteById(endpointId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

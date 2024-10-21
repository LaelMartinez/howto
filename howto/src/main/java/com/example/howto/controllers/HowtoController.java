package com.example.howto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.howto.exceptions.ResourceNotFoundException;
import com.example.howto.model.Howto;
import com.example.howto.repositories.EndpointRepository;
import com.example.howto.repositories.HowtoRepository;
import com.example.howto.repositories.ImplementationExampleRepository;

@RestController
@RequestMapping("/howtos")
public class HowtoController {

    @Autowired
    private HowtoRepository howtoRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private ImplementationExampleRepository implementationExampleRepository;

    @PostMapping
    public Howto createHowto(@RequestBody Howto howto) {
        return howtoRepository.save(howto);
    }

    @GetMapping("/{id}")
    public Howto getHowto(@PathVariable Long id) {
        return howtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Howto not found"));
    }

    @GetMapping
    public List<Howto> getAllHowtos() {
        return howtoRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Howto> updateHowto(@PathVariable Long id, @RequestBody Howto updatedHowto) {
        Howto existingHowto = howtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        existingHowto.setApiName(updatedHowto.getApiName());
        existingHowto.setDescription(updatedHowto.getDescription());

        Howto savedHowto = howtoRepository.save(existingHowto);
        return ResponseEntity.ok(savedHowto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHowto(@PathVariable Long id) {
        Howto howto = howtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        if (!endpointRepository.findByHowtoId(id).isEmpty() || !implementationExampleRepository.findByHowtoId(id).isEmpty()) {
            return ResponseEntity.status(400).build();  // Bad Request se houver Endpoints ou Implementações vinculadas
        }

        howtoRepository.delete(howto);
        return ResponseEntity.noContent().build();
    }
}


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
import com.example.howto.model.ImplementationExample;
import com.example.howto.repositories.HowtoRepository;
import com.example.howto.repositories.ImplementationExampleRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Controller de Implementações", tags = "Implement")
@RestController
@RequestMapping("/howtos/{howtoId}/implementation-examples")
public class ImplementationExampleController {

    @Autowired
    private ImplementationExampleRepository implementationExampleRepository;

    @Autowired
    private HowtoRepository howtoRepository;

    @GetMapping
    public List<ImplementationExample> getAllImplementationExamples(@PathVariable Long howtoId) {
        return implementationExampleRepository.findByHowtoId(howtoId);
    }

    @ApiOperation(value = "Cadastra uma Implementação", notes = "Cadastra uma Implementação")
    @PostMapping
    public ResponseEntity<ImplementationExample> createImplementationExample(@PathVariable Long howtoId, @RequestBody ImplementationExample implementationExample) {
        Howto howto = howtoRepository.findById(howtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        implementationExample.setHowto(howto);
        ImplementationExample savedExample = implementationExampleRepository.save(implementationExample);
        return ResponseEntity.ok(savedExample);
    }

    @ApiOperation(value = "Altera uma Implementação", notes = "Altera uma Implementação")
    @PutMapping("/{id}")
    public ResponseEntity<ImplementationExample> updateImplementationExample(@PathVariable Long howtoId, @PathVariable Long id, @RequestBody ImplementationExample updatedImplementationExample) {
        Howto howto = howtoRepository.findById(howtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        ImplementationExample existingExample = implementationExampleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Implementation example not found"));

        if (!existingExample.getHowto().getId().equals(howtoId)) {
            return ResponseEntity.status(400).build();  
        }

        existingExample.setLanguage(updatedImplementationExample.getLanguage());
        existingExample.setExampleCode(updatedImplementationExample.getExampleCode());
        existingExample.setDescription(updatedImplementationExample.getDescription());

        ImplementationExample savedExample = implementationExampleRepository.save(existingExample);
        return ResponseEntity.ok(savedExample);
    }

    @ApiOperation(value = "Deleta uma Implementação", notes = "Deleta uma Implementação")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImplementationExample(@PathVariable Long howtoId, @PathVariable Long id) {
        Howto howto = howtoRepository.findById(howtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        ImplementationExample implementationExample = implementationExampleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Example not found"));

        if (!implementationExample.getHowto().getId().equals(howtoId)) {
            return ResponseEntity.status(400).build();  
        }

        implementationExampleRepository.delete(implementationExample);
        return ResponseEntity.noContent().build();
    }
}

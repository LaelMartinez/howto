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
import com.example.howto.model.Api;
import com.example.howto.model.ImplementationExample;
import com.example.howto.repositories.ApiRepository;
import com.example.howto.repositories.ImplementationExampleRepository;

import io.swagger.annotations.ApiOperation;

@io.swagger.annotations.Api(value = "Controller de Implementações", tags = "Implement")
@RestController
@RequestMapping("/howtos/{apiid}/implementation-examples")
public class ImplementationExampleController {

    @Autowired
    private ImplementationExampleRepository implementationExampleRepository;

    @Autowired
    private ApiRepository apiRepository;

    @GetMapping
    public List<ImplementationExample> getAllImplementationExamples(@PathVariable Long apiId) {
        return implementationExampleRepository.findByApiId(apiId);
    }

    @ApiOperation(value = "Cadastra uma Implementação", notes = "Cadastra uma Implementação")
    @PostMapping
    public ResponseEntity<ImplementationExample> createImplementationExample(@PathVariable Long apiId, @RequestBody ImplementationExample implementationExample) {
        Api api = apiRepository.findById(apiId)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        implementationExample.setApi(api);
        ImplementationExample savedExample = implementationExampleRepository.save(implementationExample);
        return ResponseEntity.ok(savedExample);
    }

    @ApiOperation(value = "Altera uma Implementação", notes = "Altera uma Implementação")
    @PutMapping("/{id}")
    public ResponseEntity<ImplementationExample> updateImplementationExample(@PathVariable Long apiId, @PathVariable Long id, @RequestBody ImplementationExample updatedImplementationExample) {
        Api howto = apiRepository.findById(apiId)
                .orElseThrow(() -> new ResourceNotFoundException("API not found"));

        ImplementationExample existingExample = implementationExampleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Implementation example not found"));

        if (!existingExample.getApi().getId().equals(apiId)) {
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
    public ResponseEntity<Void> deleteImplementationExample(@PathVariable Long apiId, @PathVariable Long id) {
        Api api = apiRepository.findById(apiId)
                .orElseThrow(() -> new ResourceNotFoundException("API not found"));

        ImplementationExample implementationExample = implementationExampleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Example not found"));

        if (!implementationExample.getApi().getId().equals(apiId)) {
            return ResponseEntity.status(400).build();  
        }

        implementationExampleRepository.delete(implementationExample);
        return ResponseEntity.noContent().build();
    }
}

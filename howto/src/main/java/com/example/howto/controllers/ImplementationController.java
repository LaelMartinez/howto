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
import com.example.howto.model.Implementation;
import com.example.howto.repositories.ApiRepository;
import com.example.howto.repositories.ImplementationRepository;

import io.swagger.annotations.ApiOperation;

@io.swagger.annotations.Api(value = "Controller de Implementações", tags = "Implement")
@RestController
@RequestMapping("/howtos/{apiid}/implementation")
public class ImplementationController {

    @Autowired
    private ImplementationRepository implementationRepository;

    @Autowired
    private ApiRepository apiRepository;

    @ApiOperation(value = "Lista Implementações de uma API", notes = "Lista Implementações de uma API")
    @GetMapping
    public List<Implementation> getAllImplementation(@PathVariable Long apiId) {
        return implementationRepository.findByApiId(apiId);
    }

    @ApiOperation(value = "Cadastra uma Implementação", notes = "Cadastra uma Implementação")
    @PostMapping
    public ResponseEntity<Implementation> createImplementation(@PathVariable Long apiId, @RequestBody Implementation implementation) {
        Api api = apiRepository.findById(apiId)
                .orElseThrow(() -> new ResourceNotFoundException("Api not found"));

        implementation.setApi(api);
        Implementation savedExample = implementationRepository.save(implementation);
        return ResponseEntity.ok(savedExample);
    }

    @ApiOperation(value = "Altera uma Implementação", notes = "Altera uma Implementação")
    @PutMapping("/{id}")
    public ResponseEntity<Implementation> updateImplementation(@PathVariable Long apiId, @PathVariable Long id, @RequestBody Implementation updatedImplementation) {
        Api howto = apiRepository.findById(apiId)
                .orElseThrow(() -> new ResourceNotFoundException("API not found"));

        Implementation existingExample = implementationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Implementation not found"));

        if (!existingExample.getApi().getId().equals(apiId)) {
            return ResponseEntity.status(400).build();  
        }

        existingExample.setLanguage(updatedImplementation.getLanguage());
        existingExample.setExampleCode(updatedImplementation.getExampleCode());
        existingExample.setDescription(updatedImplementation.getDescription());

        Implementation savedExample = implementationRepository.save(existingExample);
        return ResponseEntity.ok(savedExample);
    }

    @ApiOperation(value = "Deleta uma Implementação", notes = "Deleta uma Implementação")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImplementation(@PathVariable Long apiId, @PathVariable Long id) {
        Api api = apiRepository.findById(apiId)
                .orElseThrow(() -> new ResourceNotFoundException("API not found"));

        Implementation implementation = implementationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Example not found"));

        if (!implementation.getApi().getId().equals(apiId)) {
            return ResponseEntity.status(400).build();  
        }

        implementationRepository.delete(implementation);
        return ResponseEntity.noContent().build();
    }
}

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
import com.example.howto.repositories.EndpointRepository;
import com.example.howto.repositories.ApiRepository;
import com.example.howto.repositories.ImplementationExampleRepository;

import io.swagger.annotations.ApiOperation;


@io.swagger.annotations.Api(value = "Controller de APIs", tags = "Api")
@RestController
@RequestMapping("/apis")
public class ApiController {

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private ImplementationExampleRepository implementationExampleRepository;

    @PostMapping
    public Api createHowto(@RequestBody Api howto) {
        return apiRepository.save(howto);
    }

    @ApiOperation(value = "Retorna uma Api", notes = "Retorna uma Api cadastrada")
    @GetMapping("/{id}")
    public Api getApi(@PathVariable Long id) {
        return apiRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("API not found"));
    }


    @ApiOperation(value = "Alterar uma Api", notes = "Altera uma Api cadastrada")
    @PutMapping("/{id}")
    public ResponseEntity<Api> updateHowto(@PathVariable Long id, @RequestBody Api updatedHowto) {
        Api existingHowto = apiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Api not found"));

        existingHowto.setApiName(updatedHowto.getApiName());
        existingHowto.setDescription(updatedHowto.getDescription());

        Api savedHowto = apiRepository.save(existingHowto);
        return ResponseEntity.ok(savedHowto);
    }

    @ApiOperation(value = "Deletar uma Api", notes = "Deleta uma Api cadastrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApi(@PathVariable Long id) {
        Api howto = apiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Api not found"));

        if (!endpointRepository.findByApiId(id).isEmpty() || !implementationExampleRepository.findByApiId(id).isEmpty()) {
            return ResponseEntity.status(400).build();  // Bad Request se houver Endpoints ou Implementações vinculadas
        }

        apiRepository.delete(howto);
        return ResponseEntity.noContent().build();
    }








    @ApiOperation(value = "Listar todos as Api", notes = "Retorna uma lista de todas as Api cadastradas")
    @GetMapping
    public List<Api> getAllApi() {

        return apiRepository.findAll();

    }



}


package com.example.howto.controllers;

import com.example.howto.exceptions.ResourceNotFoundException;
import com.example.howto.model.Endpoint;
import com.example.howto.model.Api;
import com.example.howto.repositories.EndpointRepository;
import com.example.howto.repositories.ApiRepository;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@io.swagger.annotations.Api(value = "Controller de EndPoints", tags = "Endpoint")
@RestController
@RequestMapping("/apis/{apiId}/endpoints")
public class EndpointController {

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @ApiOperation(value = "Cadastra um Endpoint", notes = "Cadastra um Endpoint")
    @PostMapping
    public Endpoint createEndpoint(@PathVariable Long apiId, @RequestBody Endpoint endpoint) {
        Api api = apiRepository.findById(apiId)
                .orElseThrow(() -> new ResourceNotFoundException("API not found"));

        endpoint.setApi(api);
        return endpointRepository.save(endpoint);
    }

    @ApiOperation(value = "Lista Endpoints Cadastrados", notes = "Lista Endpoints Cadastrados")
    @GetMapping
    public List<Endpoint> getAllEndpoints(@PathVariable Long apiId) {
        return endpointRepository.findByApiId(apiId);
    }

    @ApiOperation(value = "Altera um Endpoints", notes = "Altera um Endpoints")
    @PutMapping("/{endpointId}")
    public ResponseEntity<Endpoint> updateEndpoint(@PathVariable Long apiId, @PathVariable Long endpointId, @RequestBody Endpoint newEndpoint) {
        Api api = apiRepository.findById(apiId)
                .orElseThrow(() -> new ResourceNotFoundException("API not found"));

        Optional<Endpoint> existingEndpoint = endpointRepository.findById(endpointId);
        if (existingEndpoint.isPresent()) {
            Endpoint endpoint = existingEndpoint.get();
            endpoint.setUrl(newEndpoint.getUrl());
            endpoint.setHttpMethod(newEndpoint.getHttpMethod());
            endpoint.setInputExample(newEndpoint.getInputExample());
            endpoint.setOutputExample(newEndpoint.getOutputExample());
            endpoint.setApi(api);

            return ResponseEntity.ok(endpointRepository.save(endpoint));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Deleta um Endpoints", notes = "Deleta um Endpoint")
    @DeleteMapping("/{endpointId}")
    public ResponseEntity<Void> deleteEndpoint(@PathVariable Long apiId, @PathVariable Long endpointId) {
        Api howto = apiRepository.findById(apiId)
                .orElseThrow(() -> new ResourceNotFoundException("Howto not found"));

        Optional<Endpoint> endpoint = endpointRepository.findById(endpointId);
        if (endpoint.isPresent() && endpoint.get().getApi().getId().equals(apiId)) {
            endpointRepository.deleteById(endpointId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

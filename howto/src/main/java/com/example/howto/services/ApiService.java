package com.example.howto.services;

import com.example.howto.exceptions.ResourceNotFoundException;
import com.example.howto.model.Api;
import com.example.howto.repositories.ApiRepository;
import com.example.howto.repositories.EndpointRepository;
import com.example.howto.repositories.ImplementationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApiService {

    @Autowired
    private ApiRepository apiRepository;
    @Autowired
    private EndpointRepository endpointRepository;
    @Autowired
    private ImplementationRepository implementationRepository;


    public Optional<Api> getApi(Long id){
        return apiRepository.findById(id);
    }

    public List<Api> getAllApi(){
        return apiRepository.findAll();
    }

    public Api saveApi(Api api){
        return apiRepository.save(api);
    }

    public ResponseEntity<Void> deleteApi(Long id) {
        Api api = apiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Api not found"));

        if (!endpointRepository.findByApiId(id).isEmpty() || !implementationRepository.findByApiId(id).isEmpty()) {
            return ResponseEntity.status(400).build();  // Bad Request se houver Endpoints ou Implementações vinculadas
        }

        apiRepository.delete(api);

        return ResponseEntity.noContent().build();
    }


}

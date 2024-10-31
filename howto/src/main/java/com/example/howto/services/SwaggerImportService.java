package com.example.howto.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SwaggerImportService {

    @Autowired
    private ApiService apiService;

    public void importarSwagger(File arquivo) {
        // parsear JSON com Jackson
            try {
                ObjectMapper mapper = new ObjectMapper();
                Api[] apis = mapper.readValue(arquivo, Api[].class);

                // Salvar no banco de dados
                for (Api api : apis) {
                    // salvar api e endpoints
                }
            } catch (IOException e) {
                // lidar com exceção
            }
        // criar entidades de API e endpoint
        // salvar no banco de dados
    }
}

package com.example.howto.controllers;
import com.example.howto.services.SwaggerImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;


@io.swagger.annotations.Api(value = "Importação de Arquivo Swagger", tags = "Api")


@RestController
@RequestMapping("/importar-swagger")
public class ImportarController {

    @Autowired
    private SwaggerImportService swaggerImportService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importarSwagger(@RequestParam("arquivo") MultipartFile arquivo) {
        try {
            swaggerImportService.importarSwagger(arquivo);
            return ResponseEntity.ok("Arquivo importado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao importar arquivo: " + e.getMessage());
        }
    }
}

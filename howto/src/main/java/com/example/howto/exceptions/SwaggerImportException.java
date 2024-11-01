package com.example.howto.exceptions;

public class SwaggerImportException extends ResourceNotFoundException {
    public SwaggerImportException(String mensagem) {
        super(mensagem);
    }
}

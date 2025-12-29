package io.aquilesdias.msavaliadorcredito.application.exception;

public class DadosClienteException extends Exception{

    public DadosClienteException(){
        super("Dados do cliente não encontrados pelo CPF.");
    }
}

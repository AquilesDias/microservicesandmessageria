package io.aquilesdias.msclientes.application;

import io.aquilesdias.msclientes.domain.Client;
import lombok.Data;

@Data
public class ClientSaveRequest {

    String cpf;
    String name;
    Integer age;


    public Client toModel(){
        return new Client(cpf, name, age);
    }



}

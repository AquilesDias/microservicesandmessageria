package io.aquilesdias.msclientes.application;

import io.aquilesdias.msclientes.domain.Client;
import io.aquilesdias.msclientes.infra.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    @Transactional
    public Client save (Client client){
        return repository.save(client);
    }

    @GetMapping
    public Optional<Client> findByCPF(String cpf){
        return repository.findByCpf(cpf);
    }
}

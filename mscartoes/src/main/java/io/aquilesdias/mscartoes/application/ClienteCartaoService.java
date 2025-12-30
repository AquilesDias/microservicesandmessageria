package io.aquilesdias.mscartoes.application;

import io.aquilesdias.mscartoes.domain.ClienteCartao;
import io.aquilesdias.mscartoes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteCartaoService {

    private final ClienteRepository repository;

    public List<ClienteCartao> findByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}

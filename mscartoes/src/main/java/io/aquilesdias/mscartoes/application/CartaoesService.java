package io.aquilesdias.mscartoes.application;

import io.aquilesdias.mscartoes.domain.Cartao;
import io.aquilesdias.mscartoes.infra.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoesService {

    private final CartaoRepository repository;

    @Transactional
    public Cartao save(Cartao cartao){
        return repository.save(cartao);
    }

    public List<Cartao> getCartaoRendaMenorIgual(Long renda){
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return  repository.findByRendaLessThanEqual(rendaBigDecimal);

    }
}

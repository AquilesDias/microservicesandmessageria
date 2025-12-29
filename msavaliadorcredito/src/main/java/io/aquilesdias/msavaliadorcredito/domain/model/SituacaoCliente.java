package io.aquilesdias.msavaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class SituacaoCliente {

    private DadosCliente dadosCliente;
    private List<CartaoCliente> cartaoCliente;
}

package io.aquilesdias.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cartao {

    private Long id;
    private String name;
    private String bandeiraCartao;
    private BigDecimal renda;
    private BigDecimal limitBasic;
}

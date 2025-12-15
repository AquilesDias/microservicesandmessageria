package io.aquilesdias.mscartoes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeiraCartao;

    private BigDecimal renda;
    private BigDecimal limitBasic;


    public Cartao(String name, BandeiraCartao bandeiraCartao, BigDecimal renda, BigDecimal limitBasic) {
        this.name = name;
        this.bandeiraCartao = bandeiraCartao;
        this.renda = renda;
        this.limitBasic = limitBasic;
    }
}

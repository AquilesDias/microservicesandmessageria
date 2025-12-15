package io.aquilesdias.mscartoes.application.representation;

import io.aquilesdias.mscartoes.domain.BandeiraCartao;
import io.aquilesdias.mscartoes.domain.Cartao;
import lombok.Data;
import java.math.BigDecimal;


@Data
public class CartaoRequest {

    String name;
    BandeiraCartao bandeiraCartao;
    BigDecimal renda;
    BigDecimal limit;

    public Cartao toModel (){
        return new Cartao( name, bandeiraCartao, renda, limit);
    }
}

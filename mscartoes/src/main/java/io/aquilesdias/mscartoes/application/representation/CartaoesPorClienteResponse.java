package io.aquilesdias.mscartoes.application.representation;

import io.aquilesdias.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartaoesPorClienteResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;


    public static CartaoesPorClienteResponse fromModel(ClienteCartao model){
        return new CartaoesPorClienteResponse(
            model.getCartao().getName(),
            model.getCartao().getBandeiraCartao().toString(),
            model.getLimite()
        );

    }
}

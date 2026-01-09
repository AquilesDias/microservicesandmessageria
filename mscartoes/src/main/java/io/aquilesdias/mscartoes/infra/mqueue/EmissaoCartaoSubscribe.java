package io.aquilesdias.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.aquilesdias.mscartoes.domain.Cartao;
import io.aquilesdias.mscartoes.domain.ClienteCartao;
import io.aquilesdias.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.aquilesdias.mscartoes.infra.repository.CartaoRepository;
import io.aquilesdias.mscartoes.infra.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EmissaoCartaoSubscribe {

    private final CartaoRepository cartaoRepository;
    private final ClienteRepository clienteRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) throws JsonProcessingException {

        try{
            var mapper = new ObjectMapper();

            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);

            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteRepository.save(clienteCartao);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

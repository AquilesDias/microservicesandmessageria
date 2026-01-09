package io.aquilesdias.msavaliadorcredito.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import io.aquilesdias.msavaliadorcredito.application.exception.DadosClienteException;
import io.aquilesdias.msavaliadorcredito.application.exception.ErroComunicacaoMicroservicesException;
import io.aquilesdias.msavaliadorcredito.application.exception.ErroSolicitacaoCartaoException;
import io.aquilesdias.msavaliadorcredito.domain.model.*;
import io.aquilesdias.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.aquilesdias.msavaliadorcredito.infra.clients.ClienteResourceClient;
import io.aquilesdias.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvalidadorCreditoService {

    private final ClienteResourceClient resourceClient;
    private final CartoesResourceClient resourceCartao;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;


    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteException, ErroComunicacaoMicroservicesException{

        try{
            ResponseEntity<DadosCliente> dadosClienteResponse = resourceClient.getClient(cpf);
            ResponseEntity<List<CartaoCliente>> dadosCartaoResponse = resourceCartao.getCartoesByCliente(cpf);

            return SituacaoCliente.builder()
                    .dadosCliente(dadosClienteResponse.getBody())
                    .cartaoCliente(dadosCartaoResponse.getBody())
                    .build();
        } catch (FeignException ex) {
            int status = ex.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteException();
            }

            throw new ErroComunicacaoMicroservicesException(ex.getMessage(), status);
        }

    }

    public RetornoAvaliacaoCliente realizarAvaliacaoCliente (String cpf, Long renda)
            throws DadosClienteException, ErroComunicacaoMicroservicesException {

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = resourceClient.getClient(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = resourceCartao.getCartaoRenda(renda);

            List<Cartao> cartaos = cartoesResponse.getBody();


            var listaCartoesAprovados = cartaos.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimitBasic();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getAge());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado cartaoAprovado = new CartaoAprovado();
                cartaoAprovado.setCartao(cartao.getName());
                cartaoAprovado.setBandeira(cartao.getBandeiraCartao());
                cartaoAprovado.setLimiteAprovado(limiteAprovado);


                return cartaoAprovado;

            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException ex) {
            int status = ex.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteException();
            }

            throw new ErroComunicacaoMicroservicesException(ex.getMessage(), status);
        }

    }

    public ProtocoloSolicitacaoCartao solicitacaoEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){

        try{
            emissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }

    }
}

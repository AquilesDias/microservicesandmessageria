package io.aquilesdias.msavaliadorcredito.application;

import io.aquilesdias.msavaliadorcredito.application.exception.DadosClienteException;
import io.aquilesdias.msavaliadorcredito.application.exception.ErroComunicacaoMicroservicesException;
import io.aquilesdias.msavaliadorcredito.domain.model.DadosAvaliacao;
import io.aquilesdias.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import io.aquilesdias.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/avaliar-credito")
public class AvaliadorCreditoController {

    private final AvalidadorCreditoService avalidadorCreditoService;

    @GetMapping
    public String status(){
        return "ok";
    }


    @GetMapping( value = "situacao-cliente", params = "cpf" )
    public ResponseEntity situacaoCliente( @RequestParam("cpf") String cpf ){

        try {
            SituacaoCliente situacao = avalidadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacao);
        } catch (DadosClienteException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }


    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dadosAvaliacao){

        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avalidadorCreditoService
                    .realizarAvaliacaoCliente(dadosAvaliacao.getCpf(), dadosAvaliacao.getRenda() );
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }

}

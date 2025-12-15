package io.aquilesdias.mscartoes.application;

import io.aquilesdias.mscartoes.application.representation.CartaoRequest;
import io.aquilesdias.mscartoes.application.representation.CartaoesPorClienteResponse;
import io.aquilesdias.mscartoes.domain.Cartao;
import io.aquilesdias.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/cartoes")
public class CartoesResource {

    public final CartaoesService cartaoesService;
    public final ClienteCartaoService clienteCartaoService;

    @GetMapping("ok")
    public String status(){
        return "ok mscartoes";
    }

    @PostMapping
    public ResponseEntity<Cartao> save(@RequestBody CartaoRequest request){

        Cartao cartao = request.toModel();
        cartaoesService.save(cartao);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartaoRenda(@RequestParam("renda") Long renda){

        List<Cartao> list = cartaoesService.getCartaoRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }


    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartaoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf){

        List<ClienteCartao> lista = clienteCartaoService.findByCpf(cpf);
        List<CartaoesPorClienteResponse> resultList = lista.stream()
                .map(CartaoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);
    }

}

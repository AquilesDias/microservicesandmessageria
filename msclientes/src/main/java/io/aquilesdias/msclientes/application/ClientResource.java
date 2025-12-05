package io.aquilesdias.msclientes.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/client")
public class ClientResource {

    private final ClientService service;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClientSaveRequest request){

        service.save(request.toModel());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(request.cpf)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity getClient(@RequestParam("cpf") String cpf){
        var client = service.findByCPF(cpf);

        if ( client.isEmpty() ){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(client);
    }
}

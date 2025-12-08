package io.aquilesdias.mscartoes.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cartoes")
public class CartoesResource {


    @GetMapping
    public String status(){
        return "ok mscartoes";
    }
}

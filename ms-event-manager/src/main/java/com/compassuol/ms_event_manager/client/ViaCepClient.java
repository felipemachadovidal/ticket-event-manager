package com.compassuol.ms_event_manager.client;

import com.compassuol.ms_event_manager.dto.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws", configuration = ViaCepResponse.class)
public interface ViaCepClient {

    @GetMapping("/{cep}/json/")
    ViaCepResponse getCepDetails(@PathVariable("cep") String cep);
}
package com.compassuol.ms_event_manager.dto;

import lombok.Data;

@Data
public class ViaCepResponse {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
}

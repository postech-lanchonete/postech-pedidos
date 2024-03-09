package br.com.postech.pedidos.adapters.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("unused")
public class ClienteResponseDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
}

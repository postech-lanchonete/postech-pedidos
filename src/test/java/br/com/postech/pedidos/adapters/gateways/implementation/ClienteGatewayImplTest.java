package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteGatewayImplTest {

    @Mock
    private RestTemplate restTemplate;

    private ClienteGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gateway = new ClienteGatewayImpl(restTemplate, "http://example.com/");
    }

    @Test
    void buscarPorCpf_DeveRetornarRespostaCorreta() {
        String cpf = "123.321.123";
        ClienteResponseDTO respostaSimulada = new ClienteResponseDTO();
        when(restTemplate.getForObject(eq("http://example.com/{cpf}"), eq(ClienteResponseDTO.class), eq(cpf)))
                .thenReturn(respostaSimulada);

        ClienteResponseDTO resposta = gateway.buscarPorCpf(cpf);

        assertEquals(respostaSimulada, resposta);
    }
}
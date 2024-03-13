package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.drivers.external.ClienteGatewayImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void buscarPorId_DeveRetornarRespostaCorreta() {
        Long id = 123L;
        ClienteResponseDTO respostaSimulada = new ClienteResponseDTO();
        when(restTemplate.getForObject("http://example.com/{id}", ClienteResponseDTO.class, id))
                .thenReturn(respostaSimulada);

        ClienteResponseDTO resposta = gateway.buscarPorId(id);

        assertEquals(respostaSimulada, resposta);
    }
}
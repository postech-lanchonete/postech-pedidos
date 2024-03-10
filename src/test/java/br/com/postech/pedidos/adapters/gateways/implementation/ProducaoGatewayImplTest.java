package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.drivers.external.PedidoGatewayImpl;
import br.com.postech.pedidos.drivers.external.ProducaoGatewayImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducaoGatewayImplTest {

    @Mock
    private RestTemplate restTemplate;

    private ProducaoGatewayImpl producaoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enviarParaProducao_DeveRetornarRespostaCorreta() {

    }
}

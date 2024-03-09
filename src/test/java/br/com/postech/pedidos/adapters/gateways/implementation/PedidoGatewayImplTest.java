package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.drivers.external.PedidoGatewayImpl;
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
class PedidoGatewayImplTest {

    @Mock
    private RestTemplate restTemplate;

    private PedidoGatewayImpl pedidoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoGateway = new PedidoGatewayImpl(restTemplate, "http://example.com");
    }

    @Test
    void enviarParaProducao_DeveRetornarRespostaCorreta() {
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO();
        PedidoResponseDTO respostaSimulada = new PedidoResponseDTO();
        when(restTemplate.postForObject(eq("http://example.com"), any(), eq(PedidoResponseDTO.class)))
            .thenReturn(respostaSimulada);

        PedidoResponseDTO resposta = pedidoGateway.enviarParaProducao(pedidoRequestDTO);

        assertEquals(respostaSimulada, resposta);
    }
}

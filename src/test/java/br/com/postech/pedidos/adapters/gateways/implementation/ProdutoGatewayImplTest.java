package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
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
class ProdutoGatewayImplTest {
    @Mock
    private RestTemplate restTemplate;

    private ProdutoGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gateway = new ProdutoGatewayImpl(restTemplate, "http://example.com/");
    }

    @Test
    void buscarPorId_DeveRetornarRespostaCorreta() {
        Long idProduto = 1L;
        ProdutoResponseDTO respostaSimulada = new ProdutoResponseDTO();
        when(restTemplate.getForObject(eq("http://example.com/{id}"), eq(ProdutoResponseDTO.class), eq(idProduto)))
                .thenReturn(respostaSimulada);

        ProdutoResponseDTO resposta = gateway.buscarPorId(idProduto);

        assertEquals(respostaSimulada, resposta);
    }
}

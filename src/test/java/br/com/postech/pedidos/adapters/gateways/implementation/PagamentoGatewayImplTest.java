package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class PagamentoGatewayImplTest {
    public static final String URL = "http://example.com/";
    @Mock
    private RestTemplate restTemplate;

    private PagamentoGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gateway = new PagamentoGatewayImpl(restTemplate, URL);
    }

    @Test
    void pagar_DeveRetornarRespostaCorreta() {
        PagamentoRequestDTO pagamentoRequestDTO = new PagamentoRequestDTO();
        PagamentoResponseDTO respostaSimulada = new PagamentoResponseDTO();

        when(restTemplate.postForObject(
                eq(URL),
                eq(pagamentoRequestDTO),
                eq(PagamentoResponseDTO.class)))
                .thenReturn(respostaSimulada);

        PagamentoResponseDTO resposta = gateway.pagar(pagamentoRequestDTO);

        assertEquals(respostaSimulada, resposta);
    }

    @Test
    void desfazerPagamento_DeveRetornarRespostaCorreta() {
        PagamentoRequestDTO pagamentoRequestDTO = new PagamentoRequestDTO();
        PagamentoResponseDTO respostaSimulada = new PagamentoResponseDTO();
        ResponseEntity<PagamentoResponseDTO> respostaEntity = new ResponseEntity<>(respostaSimulada, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(URL),
                eq(HttpMethod.DELETE),
                any(),
                eq(PagamentoResponseDTO.class)))
                .thenReturn(respostaEntity);

        PagamentoResponseDTO resposta = gateway.desfazerPagamento(pagamentoRequestDTO);

        assertEquals(respostaSimulada, resposta);
    }
}
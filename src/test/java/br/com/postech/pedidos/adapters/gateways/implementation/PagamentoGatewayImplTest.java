package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.drivers.external.PagamentoGatewayImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
@ExtendWith(MockitoExtension.class)
class PagamentoGatewayImplTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private PagamentoGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gateway = new PagamentoGatewayImpl(kafkaTemplate, new ObjectMapper());
    }

    @Test
    void pagar_DeveRetornarRespostaCorreta() {

    }

    @Test
    void desfazerPagamento_DeveRetornarRespostaCorreta() {

    }
}
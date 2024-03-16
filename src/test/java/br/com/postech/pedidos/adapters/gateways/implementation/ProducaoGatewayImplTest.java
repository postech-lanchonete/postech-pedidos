package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.business.exceptions.BadRequestException;
import br.com.postech.pedidos.core.entities.Pedido;
import br.com.postech.pedidos.drivers.external.ProducaoGatewayImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducaoGatewayImplTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProducaoGatewayImpl producaoGateway;

    @Test
    void enviarParaProducao_DeveRetornarRespostaCorreta() throws JsonProcessingException {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        String jsonPedido = "{\"id\":1}";
        when(objectMapper.writeValueAsString(pedido)).thenReturn(jsonPedido);

        producaoGateway.enviarParaProducao(pedido);

        verify(objectMapper, times(1)).writeValueAsString(pedido);
        verify(kafkaTemplate, times(1)).send(anyString(), eq(jsonPedido));
    }

    @Test
    void enviarParaProducao_DeveRetornarErroAoSerializar() throws JsonProcessingException {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(objectMapper.writeValueAsString(pedido)).thenThrow(JsonProcessingException.class);

        assertThrows(BadRequestException.class, () -> producaoGateway.enviarParaProducao(pedido));

        verify(objectMapper, times(1)).writeValueAsString(pedido);
        verify(kafkaTemplate, times(0)).send(anyString(), anyString());
    }
}

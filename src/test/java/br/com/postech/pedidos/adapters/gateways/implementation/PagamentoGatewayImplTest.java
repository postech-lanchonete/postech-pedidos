package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.drivers.external.PagamentoGatewayImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoGatewayImplTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PagamentoGatewayImpl pagamentoGateway;

    @Test
    void pagar_deveEnviarPagamentoParaKafka() throws JsonProcessingException {
        PagamentoRequestDTO pagamentoRequestDTO = new PagamentoRequestDTO();
        pagamentoRequestDTO.setPedido(new PedidoRequestDTO());
        pagamentoRequestDTO.getPedido().setId(1L);
        String jsonPagamento = "{\"id\":1}";
        when(objectMapper.writeValueAsString(pagamentoRequestDTO)).thenReturn(jsonPagamento);

        pagamentoGateway.pagar(pagamentoRequestDTO);

        verify(objectMapper, times(1)).writeValueAsString(pagamentoRequestDTO);
        verify(kafkaTemplate, times(1)).send(anyString(), eq(jsonPagamento));
    }
}

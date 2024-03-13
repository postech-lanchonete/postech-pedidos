package br.com.postech.pedidos.adapters.input.subscribers;

import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.adapters.gateways.ProducaoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.core.entities.Pedido;
import br.com.postech.pedidos.core.enums.StatusPagamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class PagamentoSubscriberTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @Mock
    private ProducaoGateway producaoGateway;

    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private PagamentoSubscriber pagamentoSubscriber;

    @Test
    void consumeError_Success() {
        String value = "{\"valor\":100.0,\"status\":\"APROVADO\",\"pedido\":{\"id\":123,\"statusPagamento\":\"PENDENTE\",\"produtos\":[{\"id\":1,\"nome\":\"Produto 1\",\"preco\":10.0}],\"idCliente\":456}}";
        when(pedidoGateway.buscarPorId(anyLong())).thenReturn(new Pedido());

        pagamentoSubscriber.consumeError(value);

        verify(pedidoGateway, times(1)).buscarPorId(anyLong());
        verify(pedidoGateway, times(1)).salvar(any(Pedido.class));
    }

    @Test
    void consumeError_Failure() {
        String value = "some_value";

        assertThrows(NegocioException.class, () -> pagamentoSubscriber.consumeError(value));
    }

    @Test
    void consumeSuccess_Success() throws JsonProcessingException {
        String value = "{\"valor\":100.0,\"status\":\"APROVADO\",\"pedido\":{\"id\":123,\"statusPagamento\":\"PENDENTE\",\"produtos\":[{\"id\":1,\"nome\":\"Produto 1\",\"preco\":10.0}],\"idCliente\":456}}";
        PagamentoResponseDTO responseDTO = new PagamentoResponseDTO();
        responseDTO.setStatus(StatusPagamento.APROVADO);
        when(pedidoGateway.buscarPorId(anyLong())).thenReturn(new Pedido());

        pagamentoSubscriber.consumeSuccess(value);

        verify(pedidoGateway, times(1)).buscarPorId(anyLong());
        verify(pedidoGateway, times(1)).salvar(any(Pedido.class));
        verify(producaoGateway, times(1)).enviarParaProducao(any(Pedido.class));
    }

    @Test
    void consumeSuccess_Failure() {
        String value = "some_value";

        assertThrows(NegocioException.class, () -> pagamentoSubscriber.consumeSuccess(value));
    }
}
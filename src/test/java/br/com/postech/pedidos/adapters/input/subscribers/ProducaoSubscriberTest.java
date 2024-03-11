package br.com.postech.pedidos.adapters.input.subscribers;

import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.core.entities.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducaoSubscriberTest {

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private ProducaoSubscriber producaoSubscriber;

    @Test
    void consumeSuccess_Success() {
        String value = "{\"id\":123,\"status\":\"FINALIZADO\"}";
        when(pedidoGateway.buscarPorId(anyLong())).thenReturn(new Pedido());

        producaoSubscriber.consumeSuccess(value);

        verify(pedidoGateway, times(1)).buscarPorId(anyLong());
        verify(pedidoGateway, times(1)).salvar(any(Pedido.class));
    }

    @Test
    void consumeSuccess_Failure() {
        String value = "FALHA\"id\":123,\"status\":\"FINALIZADO\"}";

        try {
            producaoSubscriber.consumeSuccess(value);
        } catch (NegocioException e) {
            verify(pedidoGateway, never()).buscarPorId(anyLong());
            verify(pedidoGateway, never()).salvar(any(Pedido.class));
            return;
        }
        throw new AssertionError("Expected RuntimeException was not thrown");
    }
}
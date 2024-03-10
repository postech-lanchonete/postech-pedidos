package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.adapter.PedidoAdapter;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.core.entities.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoBuscarTodosUseCaseTest {
    @Mock
    private PedidoAdapter pedidoAdapter;
    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private PedidoBuscarTodosUseCase pedidoBuscarTodosUseCase;

    @Test
    void realizar_deveBuscarTodosPedidos() {
        when(pedidoGateway.buscarTodos()).thenReturn(List.of(new Pedido()));

        List<PedidoResponseDTO> responseDTOS = pedidoBuscarTodosUseCase.realizar();
        assertEquals(1L, responseDTOS.size());
    }

}
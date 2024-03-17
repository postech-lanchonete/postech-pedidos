package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.presenters.PedidoPresenter;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.drivers.external.PedidoGateway;
import br.com.postech.pedidos.business.exceptions.NotFoundException;
import br.com.postech.pedidos.business.usecases.implementation.cliente.ClienteBuscarPoIdUseCase;
import br.com.postech.pedidos.core.entities.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoBuscarPorIdUseCaseTest {
    @Mock
    private PedidoGateway pedidoGateway;

    @Spy
    private PedidoPresenter pedidoPresenter = Mappers.getMapper(PedidoPresenter.class);

    @Mock
    private ClienteBuscarPoIdUseCase clienteBuscarPoIdUseCase;

    @InjectMocks
    private PedidoBuscarPorIdUseCase pedidoBuscarPorIdUseCase;

    @Test
    void realizar_deveBuscarPedidoPorId() {
        when(pedidoGateway.buscarPorId(1L)).thenReturn(new Pedido());
        when(clienteBuscarPoIdUseCase.realizar(any())).thenReturn(new ClienteResponseDTO());
        assertNotNull(pedidoBuscarPorIdUseCase.realizar(1L));
    }

    @Test
    void realizar_deveBuscarPedidoPorIdQuandoClienteNaoExistir() {
        when(pedidoGateway.buscarPorId(1L)).thenReturn(new Pedido());
        when(clienteBuscarPoIdUseCase.realizar(any())).thenThrow(new NotFoundException("Cliente não encontrado"));
        assertNotNull(pedidoBuscarPorIdUseCase.realizar(1L));
    }
}
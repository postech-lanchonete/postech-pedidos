package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.presenters.PedidoPresenter;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.drivers.external.PedidoGateway;
import br.com.postech.pedidos.business.exceptions.NotFoundException;
import br.com.postech.pedidos.business.usecases.UseCase;
import br.com.postech.pedidos.core.entities.Pedido;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("pedidoBuscarPorIdUseCase")
public class PedidoBuscarPorIdUseCase implements UseCase<Long, PedidoResponseDTO> {

    private final PedidoGateway pedidoGateway;
    private final PedidoPresenter pedidoPresenter;

    private final UseCase<Long, ClienteResponseDTO> clienteBuscarPorIdUseCase;

    public PedidoBuscarPorIdUseCase(@Qualifier("clienteBuscarPoIdUseCase") UseCase<Long, ClienteResponseDTO> clienteBuscarPorIdUseCase,
                                    PedidoGateway pedidoGateway, PedidoPresenter pedidoPresenter) {
        this.pedidoGateway = pedidoGateway;
        this.pedidoPresenter = pedidoPresenter;
        this.clienteBuscarPorIdUseCase = clienteBuscarPorIdUseCase;
    }

    @Override
    public PedidoResponseDTO realizar(Long id) {
        Pedido pedido = pedidoGateway.buscarPorId(id);
        ClienteResponseDTO cliente;
        try {
            cliente = clienteBuscarPorIdUseCase.realizar(pedido.getIdCliente());
        } catch (NotFoundException exception) {
            cliente = new ClienteResponseDTO();
        }
        return pedidoPresenter.toDto(pedido, cliente);
    }
}

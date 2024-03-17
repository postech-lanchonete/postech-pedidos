package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.presenters.PedidoPresenter;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.drivers.external.PedidoGateway;
import br.com.postech.pedidos.business.usecases.UseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("pedidoBuscarTodosUseCase")
public class PedidoBuscarTodosUseCase implements UseCase.SemEntrada<List<PedidoResponseDTO>> {

    private final PedidoGateway pedidoGateway;
    private final PedidoPresenter pedidoPresenter;

    public PedidoBuscarTodosUseCase(PedidoGateway pedidoGateway, PedidoPresenter pedidoPresenter) {
        this.pedidoGateway = pedidoGateway;
        this.pedidoPresenter = pedidoPresenter;
    }

    @Override
    public List<PedidoResponseDTO> realizar() {
        return pedidoGateway.buscarTodos().stream().map(pedidoPresenter::toDto).toList();
    }
}

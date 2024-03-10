package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.adapter.PedidoAdapter;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.business.usecases.UseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("pedidoBuscarTodosUseCase")
public class PedidoBuscarTodosUseCase implements UseCase.SemEntrada<List<PedidoResponseDTO>> {

    private final PedidoGateway pedidoGateway;
    private final PedidoAdapter pedidoAdapter;

    public PedidoBuscarTodosUseCase(PedidoGateway pedidoGateway, PedidoAdapter pedidoAdapter) {
        this.pedidoGateway = pedidoGateway;
        this.pedidoAdapter = pedidoAdapter;
    }

    @Override
    public List<PedidoResponseDTO> realizar() {
        return pedidoGateway.buscarTodos().stream().map(pedidoAdapter::toDto).toList();
    }
}

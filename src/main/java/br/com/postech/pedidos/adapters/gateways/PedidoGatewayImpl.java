package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.drivers.external.PedidoGateway;
import br.com.postech.pedidos.adapters.repositories.PedidoRepository;
import br.com.postech.pedidos.business.exceptions.NotFoundException;
import br.com.postech.pedidos.core.entities.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoGatewayImpl implements PedidoGateway {

    private final PedidoRepository pedidoRepository;

    public PedidoGatewayImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Pedido não encontrado com o id %d", id)));
    }


}

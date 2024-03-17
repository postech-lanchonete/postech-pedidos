package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.core.entities.Pedido;

import java.util.List;

public interface PedidoGateway extends Gateway {

    Pedido salvar(Pedido pedido);
    Pedido buscarPorId(Long id);
    List<Pedido> buscarTodos();

}

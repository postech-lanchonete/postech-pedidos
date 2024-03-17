package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.core.entities.Pedido;

public interface ProducaoGateway {

    void enviarParaProducao(Pedido pedido);

}

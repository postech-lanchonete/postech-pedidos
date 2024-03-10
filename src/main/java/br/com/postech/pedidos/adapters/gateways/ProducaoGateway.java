package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.core.entities.Pedido;

public interface ProducaoGateway {

    void enviarParaProducao(Pedido pedido);

}

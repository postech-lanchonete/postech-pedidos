package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;

public interface PedidoGateway extends Gateway {

    PedidoResponseDTO enviarParaProducao(PedidoRequestDTO pedido);

}

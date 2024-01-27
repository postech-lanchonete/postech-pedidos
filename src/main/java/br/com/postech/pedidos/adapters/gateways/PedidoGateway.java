package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;

public interface PedidoGateway extends Gateway<PedidoRequestDTO> {

    PedidoResponseDTO enviarParaProducao(PedidoRequestDTO pedido);

}

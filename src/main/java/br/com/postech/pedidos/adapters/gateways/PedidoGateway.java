package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusDoPedido;

public interface PedidoGateway extends Gateway {

    PedidoResponseDTO enviarParaProducao(PedidoRequestDTO pedido);
    PedidoResponseDTO mudarStatus(PedidoResponseDTO pedido, StatusDoPedido novoStatus);

    interface NotificacaoClienteGateway {
        void notificaCliente(ClienteResponseDTO cliente, String mensagem);
    }
}

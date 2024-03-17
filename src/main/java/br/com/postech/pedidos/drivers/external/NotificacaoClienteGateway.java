package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;

public interface NotificacaoClienteGateway {
    void notificaCliente(ClienteResponseDTO cliente, String mensagem);

}

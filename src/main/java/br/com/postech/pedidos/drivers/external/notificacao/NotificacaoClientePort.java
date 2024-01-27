package br.com.postech.pedidos.drivers.external.notificacao;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;

public interface NotificacaoClientePort {
    void notificaCliente(ClienteResponseDTO cliente, String mensagem);
}

package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;

public interface ClienteGateway extends Gateway {

    ClienteResponseDTO buscarPorCpf(String cpf);
}

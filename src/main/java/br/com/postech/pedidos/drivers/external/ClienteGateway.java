package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;

public interface ClienteGateway extends Gateway {

    ClienteResponseDTO buscarPorId(Long id);
}

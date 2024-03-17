package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;

public interface ProdutoGateway extends Gateway {
    ProdutoResponseDTO buscarPorId(Long id);

}

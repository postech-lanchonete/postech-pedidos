package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;

public interface ProdutoGateway extends Gateway<Long> {
    ProdutoResponseDTO buscarPorId(Long id);

}

package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;

public interface PagamentoGateway {
    PagamentoResponseDTO pagar(PagamentoRequestDTO pagamento);

}

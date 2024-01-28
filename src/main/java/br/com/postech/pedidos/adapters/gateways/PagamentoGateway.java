package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;

public interface PagamentoGateway {
    PagamentoResponseDTO pagar(PagamentoRequestDTO pagamento);
    PagamentoResponseDTO desfazerPagamento(PagamentoRequestDTO pagamento);

}

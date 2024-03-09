package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;

public interface PagamentoGateway {
    void pagar(PagamentoRequestDTO pagamento);
    void desfazerPagamento(PagamentoRequestDTO pagamento);

}

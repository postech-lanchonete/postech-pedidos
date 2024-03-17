package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;

public interface PagamentoGateway {
    void pagar(PagamentoRequestDTO pagamento);

}

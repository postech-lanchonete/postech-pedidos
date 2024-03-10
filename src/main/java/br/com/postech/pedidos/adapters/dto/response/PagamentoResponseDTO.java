package br.com.postech.pedidos.adapters.dto.response;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.core.enums.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoResponseDTO {
    private UUID id;
    private BigDecimal valor;
    private StatusPagamento status;
    private PedidoRequestDTO pedido;
}

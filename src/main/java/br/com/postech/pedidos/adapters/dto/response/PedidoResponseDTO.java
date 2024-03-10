package br.com.postech.pedidos.adapters.dto.response;

import br.com.postech.pedidos.core.enums.StatusDoPedido;
import br.com.postech.pedidos.core.enums.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoResponseDTO {
    private Long id;
    private List<ProdutoResponseDTO> produtos;
    private ClienteResponseDTO cliente;
    private StatusPagamento statusPagamento;
    private StatusDoPedido status;
    private LocalDateTime dataCriacao;
}

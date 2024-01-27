package br.com.postech.pedidos.adapters.dto.response;

import br.com.postech.pedidos.adapters.enums.StatusDoPedido;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
public class PedidoResponseDTO {
    private Long id;
    private List<ProdutoResponseDTO> produtos;
    private ClienteResponseDTO cliente;
    private PagamentoResponseDTO pagamento;
    private StatusDoPedido status;
    private LocalDateTime dataCriacao;
}

package br.com.postech.pedidos.adapters.dto.request;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusPagamento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {
    private Long id;
    private StatusPagamento statusPagamento;
    @NotEmpty(message = "Lista de produtos é obrigatória")
    private List<ProdutoResponseDTO> produtos;
    @NotNull(message = "Cliente do produto é obrigatório")
    private ClienteResponseDTO cliente;

    public PedidoRequestDTO(StatusPagamento statusPagamento, List<ProdutoResponseDTO> produtos, ClienteResponseDTO cliente) {
        this.statusPagamento = statusPagamento;
        this.produtos = produtos;
        this.cliente = cliente;
    }
}
package br.com.postech.pedidos.adapters.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("unused")
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequestDTO {
    @NotNull(message = "Pedido do produto é obrigatório")
    private PedidoRequestDTO pedido;
}

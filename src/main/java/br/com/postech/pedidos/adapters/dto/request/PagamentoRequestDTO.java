package br.com.postech.pedidos.adapters.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("pedido")
    @NotNull(message = "Pedido do produto é obrigatório")
    private PedidoRequestDTO pedido;
}

package br.com.postech.pedidos.adapters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
@Schema(description = "Objeto de transferência de dados para pedido")
public class CriacaoPedidoDTO {

    @NotNull(message = "Deve ser informado o id do cliente que fez o pedido.")
    @Schema(description = "Id do cliente que fez o pedido.")
    private Long idCliente;

    @NotEmpty(message = "A lista de produtos não pode ser vazia.")
    @Schema(description = "Lista de identificadores dos produtos.")
    private List<Long> idsProdutos;

}

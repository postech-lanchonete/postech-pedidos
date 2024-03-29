package br.com.postech.pedidos.adapters.dto.response;

import br.com.postech.pedidos.adapters.enums.CategoriaProduto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoResponseDTO {
    @Schema(description = "ID do produto.")
    private Long id;

    @Schema(description = "Nome do produto.")
    private String nome;

    @Schema(description = "Categoria do produto.", enumAsRef = true)
    private CategoriaProduto categoria;

    @Schema(description = "Preço do produto.")
    private BigDecimal preco;

    @Schema(description = "Descrição do produto.")
    private String descricao;

    @Schema(description = "Imagem do produto em BLOB.")
    private String imagem;
}
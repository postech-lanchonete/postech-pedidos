package br.com.postech.pedidos.drivers.web;

import br.com.postech.pedidos.adapters.dto.CriacaoPedidoDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/pedidos")
@Tag(name = "Pedidos", description = "Todas as operações referentes aos pedidos")
public interface PedidoAPI {

    @Operation(
            summary = "Cria um novo pedido",
            description = "Ao receber uma lista de produtos e o id de um cliente, cria um novo pedido."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado." ),
            @ApiResponse(responseCode = "404", description = "O usuário, ou os produtos não foram encontrados.", content = { @Content(schema = @Schema()) })
    })
    PedidoResponseDTO criar(@Valid @RequestBody CriacaoPedidoDTO pedido);

    @Operation(
            summary = "Buscar todos os pedidos",
            description = "Retorna todos os pedidos cadastrados no sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados.")
    })
    List<PedidoResponseDTO> buscarTodos();

    @Operation(
            summary = "Buscar pedido por id",
            description = "Retorna um pedido específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado."),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.", content = { @Content(schema = @Schema()) })
    })
    PedidoResponseDTO buscarPorId(@PathVariable Long id);

}

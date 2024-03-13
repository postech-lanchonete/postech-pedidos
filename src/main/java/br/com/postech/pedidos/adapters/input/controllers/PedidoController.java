package br.com.postech.pedidos.adapters.input.controllers;

import br.com.postech.pedidos.adapters.dto.CriacaoPedidoDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.business.usecases.UseCase;
import br.com.postech.pedidos.business.usecases.implementation.pedido.PedidoCriarUseCase;
import br.com.postech.pedidos.drivers.web.PedidoAPI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class PedidoController implements PedidoAPI {
    private final UseCase<CriacaoPedidoDTO, PedidoResponseDTO> pedidoCriarUseCase;
    private final UseCase<Long, PedidoResponseDTO> pedidoBuscarPorIdUseCase;
    private final UseCase.SemEntrada<List<PedidoResponseDTO>> pedidoBuscarTodosUseCase;

    public PedidoController(@Qualifier("pedidoCriarUseCase") PedidoCriarUseCase pedidoCriarUseCase,
                            @Qualifier("pedidoBuscarPorIdUseCase") UseCase<Long, PedidoResponseDTO> pedidoBuscarPorIdUseCase,
                            UseCase.SemEntrada<List<PedidoResponseDTO>> pedidoBuscarTodosUseCase) {
        this.pedidoCriarUseCase = pedidoCriarUseCase;
        this.pedidoBuscarPorIdUseCase = pedidoBuscarPorIdUseCase;
        this.pedidoBuscarTodosUseCase = pedidoBuscarTodosUseCase;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO criar(@RequestBody CriacaoPedidoDTO criacaoPedidoDTO) {
        return pedidoCriarUseCase.realizar(criacaoPedidoDTO);
    }

    @GetMapping
    public List<PedidoResponseDTO> buscarTodos() {
        return pedidoBuscarTodosUseCase.realizar();
    }

    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPorId(@PathVariable Long id) {
        return pedidoBuscarPorIdUseCase.realizar(id);
    }

}
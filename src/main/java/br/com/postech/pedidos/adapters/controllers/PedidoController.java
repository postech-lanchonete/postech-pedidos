package br.com.postech.pedidos.adapters.controllers;

import br.com.postech.pedidos.adapters.dto.CriacaoPedidoDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.business.usecases.UseCase;
import br.com.postech.pedidos.drivers.web.PedidoAPI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class PedidoController implements PedidoAPI {
    private final UseCase<CriacaoPedidoDTO, PedidoResponseDTO> pedidoCriarUseCase;

    public PedidoController(@Qualifier("pedidoCriarUseCase") UseCase<CriacaoPedidoDTO, PedidoResponseDTO> pedidoCriarUseCase) {
        this.pedidoCriarUseCase = pedidoCriarUseCase;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO criar(@RequestBody CriacaoPedidoDTO criacaoPedidoDTO) {
        return pedidoCriarUseCase.realizar(criacaoPedidoDTO);
    }

}
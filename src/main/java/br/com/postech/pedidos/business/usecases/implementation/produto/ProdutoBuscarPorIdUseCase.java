package br.com.postech.pedidos.business.usecases.implementation.produto;

import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.ProdutoGateway;
import br.com.postech.pedidos.business.exceptions.NotFoundException;
import br.com.postech.pedidos.business.usecases.UseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Slf4j
@Component("produtoBuscarPorIdUseCase")
public class ProdutoBuscarPorIdUseCase implements UseCase<Long, ProdutoResponseDTO> {

    private final ProdutoGateway produtoGateway;

    public ProdutoBuscarPorIdUseCase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    @Override
    public ProdutoResponseDTO realizar(Long id) {
        log.debug("Buscando produto pelo id {}", id);
        try {
            var produto = produtoGateway.buscarPorId(id);
            log.debug("Produto encontrado com sucesso");
            return produto;
        } catch (Exception exception) {
            log.error("Produto não encontrado com id {}", id);
            throw new NotFoundException(format("Produto não encontrado com id {0}", id));
        }
    }

}

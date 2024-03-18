package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.presenters.PedidoPresenter;
import br.com.postech.pedidos.adapters.dto.CriacaoPedidoDTO;
import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.drivers.external.NotificacaoClienteGateway;
import br.com.postech.pedidos.drivers.external.PedidoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.business.usecases.UseCase;
import br.com.postech.pedidos.core.entities.Pedido;
import br.com.postech.pedidos.core.enums.StatusPagamento;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("pedidoCriarUseCase")
public class PedidoCriarUseCase implements UseCase<CriacaoPedidoDTO, PedidoResponseDTO> {
    private final UseCase<Long, ClienteResponseDTO> clienteBuscarPoIdUseCase;
    private final UseCase<Long, ProdutoResponseDTO> buscarProdutoPorIdUseCase;
    private final UseCase<PagamentoRequestDTO, PagamentoResponseDTO> realizarPagamentoUseCase;

    private final NotificacaoClienteGateway notificacaoClienteGateway;
    private final PedidoPresenter pedidoPresenter;
    private final PedidoGateway pedidoGateway;

    public PedidoCriarUseCase(@Qualifier("clienteBuscarPoIdUseCase") UseCase<Long, ClienteResponseDTO> clienteBuscarPorIdUseCase,
                              @Qualifier("realizarPagamentoUseCase") UseCase<PagamentoRequestDTO, PagamentoResponseDTO> realizarPagamentoUseCase,
                              @Qualifier("produtoBuscarPorIdUseCase") UseCase<Long, ProdutoResponseDTO> buscarProdutoPorIdUseCase,
                              NotificacaoClienteGateway notificacaoClienteGateway, PedidoPresenter pedidoPresenter, PedidoGateway pedidoGateway) {
        this.clienteBuscarPoIdUseCase = clienteBuscarPorIdUseCase;
        this.buscarProdutoPorIdUseCase = buscarProdutoPorIdUseCase;
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
        this.notificacaoClienteGateway = notificacaoClienteGateway;
        this.pedidoPresenter = pedidoPresenter;
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    @Transactional
    public PedidoResponseDTO realizar(CriacaoPedidoDTO pedidoCriacao) {
        log.info("Iniciando criacao de pedido para o cliente com cpf {}", pedidoCriacao.getIdCliente());
        ClienteResponseDTO clienteResponseDTO = clienteBuscarPoIdUseCase.realizar(pedidoCriacao.getIdCliente());
        log.info("Iniciando criacao de pedido para o cliente com cpf {}", pedidoCriacao.getIdCliente());

        List<ProdutoResponseDTO> produtos = buscarProdutos(pedidoCriacao.getIdsProdutos());
        log.info("Produtos encontrados com sucesso");
        Pedido pedido = pedidoPresenter.toEntity(clienteResponseDTO, produtos);
        pedidoGateway.salvar(pedido);

        try {
            PagamentoRequestDTO pagamentoRequestDTO = pedidoPresenter.toPedidoRequestDTO(pedido);
            realizarPagamentoUseCase.realizar(pagamentoRequestDTO);
            notificacaoClienteGateway.notificaCliente(clienteResponseDTO, "Seu pagamento esta sendo processado.");
        } catch (Exception e) {
            log.error("Comunicacao com o gateway de pagamento falhou", e);
            throw new NegocioException("Comunicacao com o gateway de pagamento falhou. Tente novamente mais tarde.");
        }

        var result = pedidoPresenter.toDto(pedido, clienteResponseDTO);
        result.setStatusPagamento(StatusPagamento.PENDENTE);
        return result;
    }

    private List<ProdutoResponseDTO> buscarProdutos(List<Long> idProdutos) {
        log.info("Buscando lista de produtos com {} produtos", idProdutos.size());
        var produtos = idProdutos.stream().map(buscarProdutoPorIdUseCase::realizar).toList();
        log.debug("Lista de produtos encontrada com sucesso");
        return produtos;
    }

}

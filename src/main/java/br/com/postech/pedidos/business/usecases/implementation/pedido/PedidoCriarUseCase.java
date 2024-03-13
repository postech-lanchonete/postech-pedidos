package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.adapter.PedidoAdapter;
import br.com.postech.pedidos.adapters.dto.CriacaoPedidoDTO;
import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.NotificacaoClienteGateway;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
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
    private final PedidoAdapter pedidoAdapter;
    private final PedidoGateway pedidoGateway;

    public PedidoCriarUseCase(@Qualifier("clienteBuscarPoIdUseCase") UseCase<Long, ClienteResponseDTO> clienteBuscarPorIdUseCase,
                              @Qualifier("realizarPagamentoUseCase") UseCase<PagamentoRequestDTO, PagamentoResponseDTO> realizarPagamentoUseCase,
                              @Qualifier("produtoBuscarPorIdUseCase") UseCase<Long, ProdutoResponseDTO> buscarProdutoPorIdUseCase,
                              NotificacaoClienteGateway notificacaoClienteGateway, PedidoAdapter pedidoAdapter, PedidoGateway pedidoGateway) {
        this.clienteBuscarPoIdUseCase = clienteBuscarPorIdUseCase;
        this.buscarProdutoPorIdUseCase = buscarProdutoPorIdUseCase;
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
        this.notificacaoClienteGateway = notificacaoClienteGateway;
        this.pedidoAdapter = pedidoAdapter;
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    @Transactional
    public PedidoResponseDTO realizar(CriacaoPedidoDTO pedidoCriacao) {
        ClienteResponseDTO clienteResponseDTO = clienteBuscarPoIdUseCase.realizar(pedidoCriacao.getIdCliente());
        log.info("Iniciando criacao de pedido para o cliente com cpf {}", pedidoCriacao.getIdCliente());

        List<ProdutoResponseDTO> produtos = buscarProdutos(pedidoCriacao.getIdsProdutos());
        Pedido pedido = pedidoAdapter.toEntity(clienteResponseDTO, produtos);
        pedidoGateway.salvar(pedido);

        try {
            PagamentoRequestDTO pagamentoRequestDTO = pedidoAdapter.toPedidoRequestDTO(pedido);
            realizarPagamentoUseCase.realizar(pagamentoRequestDTO);
            notificacaoClienteGateway.notificaCliente(clienteResponseDTO, "Seu pagamento esta sendo processado.");
        } catch (Exception e) {
            throw new NegocioException("Pagamento não aprovado");
        }

        var result = pedidoAdapter.toDto(pedido, clienteResponseDTO);
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

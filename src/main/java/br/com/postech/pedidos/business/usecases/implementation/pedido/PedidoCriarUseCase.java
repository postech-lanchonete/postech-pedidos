package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.dto.CriacaoPedidoDTO;
import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusPagamento;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.business.usecases.UseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("pedidoCriarUseCase")
public class PedidoCriarUseCase implements UseCase<CriacaoPedidoDTO, PedidoResponseDTO> {
    private final UseCase<String, ClienteResponseDTO> buscarClientePorCpfUseCase;
    private final UseCase<Long, ProdutoResponseDTO> buscarProdutoPorIdUseCase;
    private final UseCase<PagamentoRequestDTO, PagamentoResponseDTO> realizarPagamentoUseCase;
    private final UseCase<PedidoResponseDTO, PedidoResponseDTO> pedidoDesfazerUseCase;
    private final UseCase<PedidoRequestDTO, PedidoResponseDTO> pedidoEnviarParaProducaoUseCase;

    private final PedidoGateway.NotificacaoClienteGateway notificacaoClienteGateway;

    public PedidoCriarUseCase(@Qualifier("clienteBuscarPorCpfUseCase") UseCase<String, ClienteResponseDTO> clienteBuscarPorCpfUseCase,
                              @Qualifier("realizarPagamentoUseCase") UseCase<PagamentoRequestDTO, PagamentoResponseDTO> realizarPagamentoUseCase,
                              @Qualifier("pedidoDesfazerUseCase") UseCase<PedidoResponseDTO, PedidoResponseDTO> pedidoDesfazerUseCase,
                              @Qualifier("pedidoEnviarParaProducaoUseCase") UseCase<PedidoRequestDTO, PedidoResponseDTO> pedidoEnviarParaProducaoUseCase,
                              @Qualifier("produtoBuscarPorIdUseCase") UseCase<Long, ProdutoResponseDTO> buscarProdutoPorIdUseCase, PedidoGateway.NotificacaoClienteGateway notificacaoClienteGateway) {
        this.buscarClientePorCpfUseCase = clienteBuscarPorCpfUseCase;
        this.buscarProdutoPorIdUseCase = buscarProdutoPorIdUseCase;
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
        this.pedidoDesfazerUseCase = pedidoDesfazerUseCase;
        this.pedidoEnviarParaProducaoUseCase = pedidoEnviarParaProducaoUseCase;
        this.notificacaoClienteGateway = notificacaoClienteGateway;
    }

    @Override
    public PedidoResponseDTO realizar(CriacaoPedidoDTO pedidoCriacao) {
        ClienteResponseDTO clienteResponseDTO = buscarClientePorCpfUseCase.realizar(pedidoCriacao.getCpfCliente());
        log.info("Iniciando criacao de pedido para o cliente com cpf {}", pedidoCriacao.getCpfCliente());

        List<ProdutoResponseDTO> produtos = buscarProdutos(pedidoCriacao.getIdsProdutos());

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(StatusPagamento.PENDENTE, produtos, clienteResponseDTO);
        PagamentoRequestDTO pagamentoRequestDTO = new PagamentoRequestDTO(pedidoRequestDTO);
        PedidoResponseDTO pedidoResponseDTO = pedidoEnviarParaProducaoUseCase.realizar(pedidoRequestDTO);
        try {
            pagamentoRequestDTO.getPedido().setId(pedidoResponseDTO.getId());
            PagamentoResponseDTO pagamento = realizarPagamentoUseCase.realizar(pagamentoRequestDTO);
            pedidoResponseDTO.setPagamento(pagamento);
        } catch (Exception e) {
            pedidoDesfazerUseCase.realizar(pedidoResponseDTO);
            throw new NegocioException("Pagamento não aprovado");
        }


        notificacaoClienteGateway.notificaCliente(clienteResponseDTO, "Seu pedido foi aprovado e esta sendo produzido.");
        return pedidoResponseDTO;
    }

    private List<ProdutoResponseDTO> buscarProdutos(List<Long> idProdutos) {
        log.info("Buscando lista de produtos com {} produtos", idProdutos.size());
        var produtos = idProdutos.stream().map(buscarProdutoPorIdUseCase::realizar).toList();
        log.debug("Lista de produtos encontrada com sucesso");
        return produtos;
    }

}

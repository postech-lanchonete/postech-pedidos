package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.dto.CriacaoPedidoDTO;
import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.business.usecases.UseCase;
import br.com.postech.pedidos.drivers.external.notificacao.NotificacaoClientePort;
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
    private final UseCase<PagamentoRequestDTO, PagamentoResponseDTO> pagamentoDesfazerUseCase;
    private final UseCase<PedidoRequestDTO, PedidoResponseDTO> pedidoEnviarParaProducaoUseCase;

    private final NotificacaoClientePort notificacaoClientePort;

    public PedidoCriarUseCase(@Qualifier("clienteBuscarPorCpfUseCase") UseCase<String, ClienteResponseDTO> clienteBuscarPorCpfUseCase,
                              @Qualifier("realizarPagamentoUseCase") UseCase<PagamentoRequestDTO, PagamentoResponseDTO> realizarPagamentoUseCase,
                              @Qualifier("pagamentoDesfazerUseCase") UseCase<PagamentoRequestDTO, PagamentoResponseDTO> pagamentoDesfazerUseCase,
                              @Qualifier("pedidoEnviarParaProducaoUseCase") UseCase<PedidoRequestDTO, PedidoResponseDTO> pedidoEnviarParaProducaoUseCase,
                              @Qualifier("produtoBuscarPorIdUseCase") UseCase<Long, ProdutoResponseDTO> buscarProdutoPorIdUseCase, NotificacaoClientePort notificacaoClientePort) {
        this.buscarClientePorCpfUseCase = clienteBuscarPorCpfUseCase;
        this.buscarProdutoPorIdUseCase = buscarProdutoPorIdUseCase;
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
        this.pagamentoDesfazerUseCase = pagamentoDesfazerUseCase;
        this.pedidoEnviarParaProducaoUseCase = pedidoEnviarParaProducaoUseCase;
        this.notificacaoClientePort = notificacaoClientePort;
    }

    @Override
    public PedidoResponseDTO realizar(CriacaoPedidoDTO pedidoCriacao) {
        ClienteResponseDTO clienteResponseDTO = buscarClientePorCpfUseCase.realizar(pedidoCriacao.getCpfCliente());
        log.info("Iniciando criacao de pedido para o cliente com cpf {}", pedidoCriacao.getCpfCliente());

        List<ProdutoResponseDTO> produtos = buscarProdutos(pedidoCriacao.getIdsProdutos());

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(produtos, clienteResponseDTO);
        PagamentoRequestDTO pagamentoRequestDTO = new PagamentoRequestDTO(pedidoRequestDTO);
        PagamentoResponseDTO pagamento = realizarPagamentoUseCase.realizar(pagamentoRequestDTO);

        PedidoResponseDTO pedidoResponseDTO = enviarParaProducao(pedidoRequestDTO, pagamentoRequestDTO);
        pedidoResponseDTO.setPagamento(pagamento);

        notificacaoClientePort.notificaCliente(clienteResponseDTO, "Seu pedido foi aprovado e esta sendo produzido.");
        return pedidoResponseDTO;
    }

    private PedidoResponseDTO enviarParaProducao(PedidoRequestDTO pedidoRequestDTO, PagamentoRequestDTO pagamento) {
        try {
            return pedidoEnviarParaProducaoUseCase.realizar(pedidoRequestDTO);
        } catch (NegocioException negocioException) {
            pagamentoDesfazerUseCase.realizar(pagamento);
            throw negocioException;
        }
    }

    private List<ProdutoResponseDTO> buscarProdutos(List<Long> idProdutos) {
        log.info("Buscando lista de produtos com {} produtos", idProdutos.size());
        var produtos = idProdutos.stream().map(buscarProdutoPorIdUseCase::realizar).toList();
        log.debug("Lista de produtos encontrada com sucesso");
        return produtos;
    }

}

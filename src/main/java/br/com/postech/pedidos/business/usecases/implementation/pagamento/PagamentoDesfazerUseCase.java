package br.com.postech.pedidos.business.usecases.implementation.pagamento;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusPagamento;
import br.com.postech.pedidos.adapters.gateways.PagamentoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.business.usecases.UseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("pagamentoDesfazerUseCase")
public class PagamentoDesfazerUseCase implements UseCase<PagamentoRequestDTO, PagamentoResponseDTO> {

    private final PagamentoGateway pagamentoGateway;

    public PagamentoDesfazerUseCase(PagamentoGateway pagamentoGateway) {
        this.pagamentoGateway = pagamentoGateway;
    }

    @Override
    public PagamentoResponseDTO realizar(PagamentoRequestDTO pagamentoRequest) {
        log.debug("Realizando rollback do pagamento para o cliente com cpf {}", pagamentoRequest.getPedido().getCliente().getCpf());
        try {
            var pagamento = pagamentoGateway.desfazerPagamento(pagamentoRequest);
            if (pagamento.getStatus() == StatusPagamento.ROLLBACK) {
                log.debug("Pagamento desfeito com sucesso");
                return pagamento;
            }
        } catch (Exception exception) {
            log.error("O pagamento para o cliente {} não foi desfeito. Ação manual necessária", pagamentoRequest.getPedido().getCliente().getCpf());
        }

        throw new NegocioException("Ocorreu um erro ao efetuar o pedido e o não foi possível desfazer o seu pagamento. Por favor, entre em contato com o suporte.");
    }

}

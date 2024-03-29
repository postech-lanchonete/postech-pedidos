package br.com.postech.pedidos.business.usecases.implementation.pagamento;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.drivers.external.PagamentoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.business.usecases.UseCase;
import br.com.postech.pedidos.core.enums.StatusPagamento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("realizarPagamentoUseCase")
public class PagamentoRealizarUseCase implements UseCase<PagamentoRequestDTO, PagamentoResponseDTO> {

    private final PagamentoGateway pagamentoGateway;

    public PagamentoRealizarUseCase(PagamentoGateway pagamentoGateway) {
        this.pagamentoGateway = pagamentoGateway;
    }

    @Override
    public PagamentoResponseDTO realizar(PagamentoRequestDTO pagamentoRequest) {
        log.debug("Realizando pagamento para o cliente com id {}", pagamentoRequest.getPedido().getIdCliente());
        try {
            pagamentoGateway.pagar(pagamentoRequest);
            PagamentoResponseDTO pagamentoResponseDTO = new PagamentoResponseDTO();
            pagamentoResponseDTO.setStatus(StatusPagamento.PENDENTE);
            return pagamentoResponseDTO;
        } catch (Exception exception) {
            log.error("Erro ao chamar o pagamento");
            throw new NegocioException("Pagamento não aprovado");
        }
    }

}

package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusDoPedido;
import br.com.postech.pedidos.adapters.gateways.PagamentoGateway;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.business.usecases.UseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("pedidoDesfazerUseCase")
public class PedidoDesfazerUseCase implements UseCase<PedidoResponseDTO, PedidoResponseDTO> {

    private final PedidoGateway pedidoGateway;

    public PedidoDesfazerUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public PedidoResponseDTO realizar(PedidoResponseDTO dto) {
        log.debug("Desfazendo pedido");
        try {
            return pedidoGateway.mudarStatus(dto, StatusDoPedido.CANCELADO);
        } catch (Exception exception) {
            log.error("Erro ao desfazer o pedido");
            throw new NegocioException("Pedido não desfeito");
        }
    }

}

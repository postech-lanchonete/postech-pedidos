package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.business.usecases.UseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("pedidoEnviarParaProducaoUseCase")
public class PedidoEnviarParaProducaoUseCase implements UseCase<PedidoRequestDTO, PedidoResponseDTO> {

    private final PedidoGateway pedidoGateway;

    public PedidoEnviarParaProducaoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public PedidoResponseDTO realizar(PedidoRequestDTO pedidoRequestDTO) {
        log.debug("Enviando pedido para produção");
        try {
            var pedido = pedidoGateway.enviarParaProducao(pedidoRequestDTO);
            log.debug("Pedido enviado com sucesso");
            return pedido;
        } catch (Exception exception) {
            log.error("Pedido não enviado para produção");
            throw new NegocioException(exception.getMessage());
        }
    }
}

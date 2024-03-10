package br.com.postech.pedidos.adapters.input.subscribers;

import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.adapters.gateways.ProducaoGateway;
import br.com.postech.pedidos.core.entities.Pedido;
import br.com.postech.pedidos.core.enums.StatusDoPedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PagamentoSubscriber {

    private final ObjectMapper objectMapper;
    private final PedidoGateway pedidoGateway;
    private final ProducaoGateway producaoGateway;

    public PagamentoSubscriber(ObjectMapper objectMapper, PedidoGateway pedidoGateway, ProducaoGateway producaoGateway) {
        this.objectMapper = objectMapper;
        this.pedidoGateway = pedidoGateway;
        this.producaoGateway = producaoGateway;
    }
    @KafkaListener(topics = "postech-pagamento-output-error", groupId = "postech-group-pedido")
    public void consumeError(String value) {
        try {
            PagamentoResponseDTO responseDTO = objectMapper.readValue(value, PagamentoResponseDTO.class);
            Pedido pedido = pedidoGateway.buscarPorId(responseDTO.getPedido().getId());
            pedido.setStatus(StatusDoPedido.CANCELADO);
            pedido.setStatusPagamento(responseDTO.getStatus());
            pedidoGateway.salvar(pedido);
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "postech-pagamento-output", groupId = "postech-group-pedido")
    public void consumeSuccess(String value) {
        try {
            PagamentoResponseDTO responseDTO = objectMapper.readValue(value, PagamentoResponseDTO.class);
            Pedido pedido = pedidoGateway.buscarPorId(responseDTO.getPedido().getId());
            pedido.setStatusPagamento(responseDTO.getStatus());
            pedidoGateway.salvar(pedido);
            producaoGateway.enviarParaProducao(pedido);
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

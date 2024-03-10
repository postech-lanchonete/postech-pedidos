package br.com.postech.pedidos.adapters.input.subscribers;

import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.core.entities.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducaoSubscriber {

    private final ObjectMapper objectMapper;
    private final PedidoGateway pedidoGateway;

    public ProducaoSubscriber(ObjectMapper objectMapper, PedidoGateway pedidoGateway) {
        this.objectMapper = objectMapper;
        this.pedidoGateway = pedidoGateway;
    }

    @KafkaListener(topics = "postech-producao-output", groupId = "postech-group-pedido")
    public void consumeSuccess(String value) {
        try {
            PedidoResponseDTO responseDTO = objectMapper.readValue(value, PedidoResponseDTO.class);
            Pedido pedido = pedidoGateway.buscarPorId(responseDTO.getId());
            pedido.setStatus(responseDTO.getStatus());
            pedidoGateway.salvar(pedido);
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

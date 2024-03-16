package br.com.postech.pedidos.adapters.input.subscribers;

import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.DeadLetterQueueGateway;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.core.entities.Pedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducaoSubscriber {

    public static final String TOPIC_PRODUCAO_OUTPUT = "postech-producao-output";
    public static final String TOPIC_PRODUCAO_OUTPUT_DLQ = "postech-producao-output-dlq";
    private final ObjectMapper objectMapper;
    private final PedidoGateway pedidoGateway;
    private final DeadLetterQueueGateway deadLetterQueueGateway;

    public ProducaoSubscriber(ObjectMapper objectMapper, PedidoGateway pedidoGateway, DeadLetterQueueGateway deadLetterQueueGateway) {
        this.objectMapper = objectMapper;
        this.pedidoGateway = pedidoGateway;
        this.deadLetterQueueGateway = deadLetterQueueGateway;
    }

    @KafkaListener(topics = TOPIC_PRODUCAO_OUTPUT, groupId = "postech-group-pedido")
    public void consumeSuccess(String value) {
        try {
            PedidoResponseDTO responseDTO = objectMapper.readValue(value, PedidoResponseDTO.class);
            Pedido pedido = pedidoGateway.buscarPorId(responseDTO.getId());
            pedido.setStatus(responseDTO.getStatus());
            pedidoGateway.salvar(pedido);
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            this.deadLetterQueueGateway.enviar(TOPIC_PRODUCAO_OUTPUT_DLQ, value);
        }
    }
}

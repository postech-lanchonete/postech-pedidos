package br.com.postech.pedidos.adapters.input.subscribers;

import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.drivers.external.DeadLetterQueueGateway;
import br.com.postech.pedidos.drivers.external.PedidoGateway;
import br.com.postech.pedidos.drivers.external.ProducaoGateway;
import br.com.postech.pedidos.core.entities.Pedido;
import br.com.postech.pedidos.core.enums.StatusPagamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PagamentoSubscriber {

    public static final String TOPIC_PAGAMENTO_OUTPUT = "postech-pagamento-output";
    public static final String TOPIC_PAGAMENTO_OUTPUT_DLQ = "postech-pagamento-output-dlq";
    private final ObjectMapper objectMapper;
    private final PedidoGateway pedidoGateway;
    private final ProducaoGateway producaoGateway;

    private final DeadLetterQueueGateway deadLetterQueueGateway;

    public PagamentoSubscriber(ObjectMapper objectMapper, PedidoGateway pedidoGateway, ProducaoGateway producaoGateway, DeadLetterQueueGateway deadLetterQueueGateway) {
        this.objectMapper = objectMapper;
        this.pedidoGateway = pedidoGateway;
        this.producaoGateway = producaoGateway;
        this.deadLetterQueueGateway = deadLetterQueueGateway;
    }

    @KafkaListener(topics = TOPIC_PAGAMENTO_OUTPUT, groupId = "postech-group-pedido")
    public void consumeSuccess(String value) {
        try {
            PagamentoResponseDTO responseDTO = objectMapper.readValue(value, PagamentoResponseDTO.class);
            Pedido pedido = pedidoGateway.buscarPorId(responseDTO.getPedido().getId());
            pedido.setStatusPagamento(responseDTO.getStatus());
            pedidoGateway.salvar(pedido);
            if (pedido.getStatusPagamento() == StatusPagamento.APROVADO) {
                producaoGateway.enviarParaProducao(pedido);
            }
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            this.deadLetterQueueGateway.enviar(TOPIC_PAGAMENTO_OUTPUT_DLQ, value);
        }
    }
}

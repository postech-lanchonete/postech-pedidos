package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.adapters.gateways.ProducaoGateway;
import br.com.postech.pedidos.business.exceptions.BadRequestException;
import br.com.postech.pedidos.core.entities.Pedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducaoGatewayImpl implements ProducaoGateway {

    private static final String TOPIC_PAGAMENTO = "postech-producao-input";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ProducaoGatewayImpl(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void enviarParaProducao(Pedido pedido) {
        try {
            String jsonPagamento = objectMapper.writeValueAsString(pedido);
            kafkaTemplate.send(TOPIC_PAGAMENTO, jsonPagamento);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar o objeto Pedido para JSON", e);
            throw new BadRequestException("Erro ao serializar o objeto Pedido para JSON");
        } catch (Exception e) {
            log.error("Erro ao enviar o Pedido para o Kafka", e);
            throw new BadRequestException("Erro ao enviar o Pedido para producao ");
        }
    }
}

package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.gateways.PagamentoGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private static final String TOPIC_PAGAMENTO = "pagamento-pedido-realizar-input";
    private static final String TOPIC_ROLLBACK = "pagamento-pedido-realizar-rollback";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PagamentoGatewayImpl(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void pagar(PagamentoRequestDTO pagamento) {
        try {
            String jsonPagamento = objectMapper.writeValueAsString(pagamento);
            kafkaTemplate.send(TOPIC_PAGAMENTO, jsonPagamento);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar o objeto PagamentoRequestDTO para JSON", e);
            throw new RuntimeException("Erro ao serializar o objeto PagamentoRequestDTO para JSON", e);
        } catch (Exception e) {
            log.error("Erro ao enviar o pagamento para o Kafka", e);
            throw new RuntimeException("Erro ao enviar o pagamento ", e);
        }
    }

    @Override
    public void desfazerPagamento(PagamentoRequestDTO pagamento) {
        try {
            String jsonPagamento = objectMapper.writeValueAsString(pagamento);
            kafkaTemplate.send(TOPIC_ROLLBACK, jsonPagamento);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar o objeto PagamentoRequestDTO para JSON ao realizar o rollback", e);
            throw new RuntimeException("Erro ao serializar o objeto PagamentoRequestDTO para JSON ao realizar o rollback", e);
        } catch (Exception e) {
            log.error("Erro ao enviar o pagamento para o Kafka ao realizar o rollback", e);
            throw new RuntimeException("Erro ao enviar o pagamento ao realizar o rollback ", e);
        }
    }

}

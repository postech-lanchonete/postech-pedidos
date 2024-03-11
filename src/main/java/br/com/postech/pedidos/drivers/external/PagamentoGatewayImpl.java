package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.gateways.PagamentoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private static final String TOPIC_PAGAMENTO = "postech-pagamento-input";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PagamentoGatewayImpl(KafkaTemplate<String, String> kafkaTemplate,
                                ObjectMapper objectMapper) {
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
            throw new NegocioException("Erro ao serializar o objeto PagamentoRequestDTO para JSON");
        } catch (Exception e) {
            log.error("Erro ao enviar o pagamento para o Kafka", e);
            throw new NegocioException("Erro ao enviar o pagamento ");
        }
    }

}

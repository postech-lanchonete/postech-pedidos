package br.com.postech.pedidos.adapters.input.subscribers;

import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusPagamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PagamentoInputSubscriber {

    private final ObjectMapper objectMapper;

    public PagamentoInputSubscriber(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @KafkaListener(topics = "pagamento-pedido-realizar-output", groupId = "pagamento_pedido_realizar_output_group_id")
    public void consume(String value) {
        try {
            PagamentoResponseDTO responseDTO = objectMapper.readValue(value, PagamentoResponseDTO.class);
            if (responseDTO != null && responseDTO.getStatus() == StatusPagamento.APROVADO) {
                // TODO enviar request para producao para criar o pedido
            } else {
                // TODO enviar request para producao para cancelar o pedido
            }
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

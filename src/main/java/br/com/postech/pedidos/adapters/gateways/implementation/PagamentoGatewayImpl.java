package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.PagamentoGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PagamentoGatewayImpl implements PagamentoGateway {
    private final RestTemplate restTemplate;
    private final String microservicoUrl;

    public PagamentoGatewayImpl(RestTemplate restTemplate, @Value("${microservico.pagamento.url}") String microservicoUrl) {
        this.restTemplate = restTemplate;
        this.microservicoUrl = microservicoUrl;
    }

    @Override
    public PagamentoResponseDTO pagar(PagamentoRequestDTO pagamento) {
        return restTemplate.postForObject(microservicoUrl, pagamento, PagamentoResponseDTO.class);
    }
    @Override
    public PagamentoResponseDTO desfazerPagamento(PagamentoRequestDTO pagamento) {
        HttpEntity<PagamentoRequestDTO> requestEntity = new HttpEntity<>(pagamento, new HttpHeaders());

        ResponseEntity<PagamentoResponseDTO> responseEntity = restTemplate.exchange(
                microservicoUrl,
                HttpMethod.DELETE,
                requestEntity,
                PagamentoResponseDTO.class
        );

        return responseEntity.getBody();
    }
}

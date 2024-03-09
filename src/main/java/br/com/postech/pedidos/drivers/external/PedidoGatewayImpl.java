package br.com.postech.pedidos.drivers.external;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusDoPedido;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PedidoGatewayImpl implements PedidoGateway {

    private final RestTemplate restTemplate;
    private final String microservicoUrl;

    public PedidoGatewayImpl(RestTemplate restTemplate, @Value("${microservico.producao.url}") String microservicoUrl) {
        this.restTemplate = restTemplate;
        this.microservicoUrl = microservicoUrl;
    }

    @Override
    public PedidoResponseDTO enviarParaProducao(PedidoRequestDTO pedido) {
        return restTemplate.postForObject(microservicoUrl, pedido, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO mudarStatus(PedidoResponseDTO pedido, StatusDoPedido novoStatus) {
        String url = microservicoUrl + "/" + pedido.getId() + "/status";

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<StatusDoPedido> requestEntity = new HttpEntity<>(novoStatus, headers);

        ResponseEntity<PedidoResponseDTO> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                PedidoResponseDTO.class
        );

        return responseEntity.getBody();    }
}

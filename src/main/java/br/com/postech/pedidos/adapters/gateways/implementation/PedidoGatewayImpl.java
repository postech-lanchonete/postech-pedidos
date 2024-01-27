package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import org.springframework.beans.factory.annotation.Value;
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
}

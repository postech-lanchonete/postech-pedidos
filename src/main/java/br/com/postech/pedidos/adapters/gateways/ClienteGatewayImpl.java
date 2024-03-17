package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.drivers.external.ClienteGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClienteGatewayImpl implements ClienteGateway {

    private final RestTemplate restTemplate;
    private final String microservicoUrl;

    public ClienteGatewayImpl(RestTemplate restTemplate, @Value("${microservico.clientes.url}") String microservicoUrl) {
        this.restTemplate = restTemplate;
        this.microservicoUrl = microservicoUrl;
    }

    @Override
    public ClienteResponseDTO buscarPorId(Long id) {
        return restTemplate.getForObject(microservicoUrl + "{id}", ClienteResponseDTO.class, id);
    }
}
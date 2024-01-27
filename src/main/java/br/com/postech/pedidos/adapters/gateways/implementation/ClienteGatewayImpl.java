package br.com.postech.pedidos.adapters.gateways.implementation;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.gateways.ClienteGateway;
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
    public ClienteResponseDTO buscarPorCpf(String cpf) {
        return restTemplate.getForObject(microservicoUrl + "{cpf}", ClienteResponseDTO.class, cpf);
    }
}
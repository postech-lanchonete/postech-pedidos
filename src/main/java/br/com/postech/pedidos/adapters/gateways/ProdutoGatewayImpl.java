package br.com.postech.pedidos.adapters.gateways;

import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.drivers.external.ProdutoGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProdutoGatewayImpl implements ProdutoGateway {
    private final RestTemplate restTemplate;
    private final String microservicoUrl;

    public ProdutoGatewayImpl(RestTemplate restTemplate, @Value("${microservico.produtos.url}") String microservicoUrl) {
        this.restTemplate = restTemplate;
        this.microservicoUrl = microservicoUrl;
    }

    @Override
    public ProdutoResponseDTO buscarPorId(Long id) {
        return restTemplate.getForObject(microservicoUrl + "{id}", ProdutoResponseDTO.class, id);
    }


}

package br.com.postech.pedidos.business.usecases.implementation.cliente;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.gateways.ClienteGateway;
import br.com.postech.pedidos.business.exceptions.NotFoundException;
import br.com.postech.pedidos.business.usecases.UseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import static java.text.MessageFormat.format;

@Slf4j
@AllArgsConstructor
@Component(value = "clienteBuscarPoIdUseCase")
public class ClienteBuscarPoIdUseCase implements UseCase<Long, ClienteResponseDTO> {

    private final ClienteGateway clienteGateway;

    @Override
    public ClienteResponseDTO realizar(Long id) {
        log.debug("Buscando cliente com id {}", id);
        try {
            var cliente = clienteGateway.buscarPorId(id);
            log.debug("Cliente encontrado com sucesso");
            return cliente;
        } catch (Exception exception) {
            if (exception instanceof ResourceAccessException) {
                throw new InternalError("I/O exception");
            }
            log.error("Cliente não encontrado com id {}", id);
            throw new NotFoundException(format("Cliente não encontrado com id {0}", id));
        }
    }

}

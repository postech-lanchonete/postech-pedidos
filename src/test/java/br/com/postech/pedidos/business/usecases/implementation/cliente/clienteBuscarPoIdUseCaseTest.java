package br.com.postech.pedidos.business.usecases.implementation.cliente;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.gateways.ClienteGateway;
import br.com.postech.pedidos.business.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class clienteBuscarPoIdUseCaseTest {
    private final Long ID = 12345678901L;

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private ClienteBuscarPoIdUseCase clienteBuscarPoIdUseCase;

    @Test
    void realizar_ClienteEncontrado_RetornaClienteResponseDTO() {
        ClienteResponseDTO clienteResponse = criarClienteResponseDTO();

        when(clienteGateway.buscarPorId(ID)).thenReturn(clienteResponse);
        ClienteResponseDTO resultado = clienteBuscarPoIdUseCase.realizar(ID);

        assertEquals(clienteResponse, resultado);
    }

    @Test
    void realizar_ClienteNaoEncontrado_GeraNotFoundException() {
        when(clienteGateway.buscarPorId(ID)).thenThrow(new NotFoundException("Simulando exceção ao buscar cliente"));

        assertThrows(NotFoundException.class, () -> clienteBuscarPoIdUseCase.realizar(ID));
    }

    @Test
    void realizar_ExcecaoAoBuscarCliente_GeraInternalError() {
        when(clienteGateway.buscarPorId(ID)).thenThrow(new ResourceAccessException("Simulando exceção ao buscar cliente"));

        assertThrows(InternalError.class, () -> clienteBuscarPoIdUseCase.realizar(ID));
    }

    private ClienteResponseDTO criarClienteResponseDTO() {
        var dto = new ClienteResponseDTO();
        dto.setId(ID);
        dto.setNome("Nome");
        dto.setSobrenome("Sobrenome");
        return dto;
    }
}

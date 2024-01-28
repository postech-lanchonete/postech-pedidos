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
class ClienteBuscarPorCpfUseCaseTest {
    private final String CPF = "12345678901";

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private ClienteBuscarPorCpfUseCase clienteBuscarPorCpfUseCase;

    @Test
    void realizar_ClienteEncontrado_RetornaClienteResponseDTO() {
        ClienteResponseDTO clienteResponse = criarClienteResponseDTO();

        when(clienteGateway.buscarPorCpf(CPF)).thenReturn(clienteResponse);
        ClienteResponseDTO resultado = clienteBuscarPorCpfUseCase.realizar(CPF);

        assertEquals(clienteResponse, resultado);
    }

    @Test
    void realizar_ClienteNaoEncontrado_GeraNotFoundException() {
        when(clienteGateway.buscarPorCpf(CPF)).thenThrow(new NotFoundException("Simulando exceção ao buscar cliente"));

        assertThrows(NotFoundException.class, () -> clienteBuscarPorCpfUseCase.realizar(CPF));
    }

    @Test
    void realizar_ExcecaoAoBuscarCliente_GeraInternalError() {
        when(clienteGateway.buscarPorCpf(CPF)).thenThrow(new ResourceAccessException("Simulando exceção ao buscar cliente"));

        assertThrows(InternalError.class, () -> clienteBuscarPorCpfUseCase.realizar(CPF));
    }

    private ClienteResponseDTO criarClienteResponseDTO() {
        var dto = new ClienteResponseDTO();
        dto.setCpf(CPF);
        dto.setNome("Nome");
        dto.setSobrenome("Sobrenome");
        return dto;
    }
}

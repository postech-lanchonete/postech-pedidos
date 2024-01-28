package br.com.postech.pedidos.business.usecases.implementation.pedido;

import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoEnviarParaProducaoUseCaseTest {

    @Mock
    private PedidoGateway pedidoGateway;

    @InjectMocks
    private PedidoEnviarParaProducaoUseCase pedidoEnviarParaProducaoUseCase;

    @Test
    void realizar_PedidoEnviadoComSucesso_RetornaPedidoResponseDTO() {
        PedidoRequestDTO pedidoRequestDTO = criarPedidoRequestDTO();
        PedidoResponseDTO pedidoResponseDTO = criarPedidoResponseDTO();

        when(pedidoGateway.enviarParaProducao(pedidoRequestDTO)).thenReturn(pedidoResponseDTO);

        PedidoResponseDTO resultado = pedidoEnviarParaProducaoUseCase.realizar(pedidoRequestDTO);

        assertEquals(pedidoResponseDTO, resultado);
    }

    @Test
    void realizar_FalhaAoEnviarPedido_GeraNegocioException() {
        PedidoRequestDTO pedidoRequestDTO = criarPedidoRequestDTO();

        when(pedidoGateway.enviarParaProducao(pedidoRequestDTO)).thenThrow(new RuntimeException("Simulando falha ao enviar pedido"));

        assertThrows(NegocioException.class, () -> pedidoEnviarParaProducaoUseCase.realizar(pedidoRequestDTO));
    }

    private PedidoRequestDTO criarPedidoRequestDTO() {
        return new PedidoRequestDTO();
    }

    private PedidoResponseDTO criarPedidoResponseDTO() {
        return new PedidoResponseDTO();
    }
}

package br.com.postech.pedidos.business.usecases.implementation.pagamento;

import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusPagamento;
import br.com.postech.pedidos.adapters.gateways.PagamentoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.business.usecases.implementation.pedido.PedidoDesfazerUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class PedidoDesfazerUseCaseTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private PedidoDesfazerUseCase pedidoDesfazerUseCase;

    @Test
    void realizar_DesfazPagamentoComSucesso_RetornaPagamentoDesfeito() {
        PedidoResponseDTO pagamentoRequest = criarPagamentoRequestDTO();

        PedidoResponseDTO resultado = pedidoDesfazerUseCase.realizar(pagamentoRequest);

        assertEquals(StatusPagamento.ROLLBACK, resultado.getStatus());
    }

    @Test
    void realizar_ExcecaoAoDesfazerPagamento_GeraNegocioException() {
        PedidoResponseDTO pagamentoRequest = criarPagamentoRequestDTO();
        doThrow(new RuntimeException("Simulando exceção ao desfazer pagamento")).when(pagamentoGateway).desfazerPagamento(any());
        assertThrows(NegocioException.class, () -> pedidoDesfazerUseCase.realizar(pagamentoRequest));
    }

    private PedidoResponseDTO criarPagamentoRequestDTO() {
        return new PedidoResponseDTO();
    }
}
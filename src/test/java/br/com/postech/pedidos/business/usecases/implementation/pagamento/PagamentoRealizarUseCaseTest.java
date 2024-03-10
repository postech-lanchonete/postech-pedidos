package br.com.postech.pedidos.business.usecases.implementation.pagamento;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.PagamentoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import br.com.postech.pedidos.core.enums.StatusPagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class PagamentoRealizarUseCaseTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private PagamentoRealizarUseCase pagamentoRealizarUseCase;

    @Test
    void realizar_PagamentoAprovado_RetornaPagamentoAprovado() {
        PagamentoRequestDTO pagamentoRequest = criarPagamentoRequestDTO();

        PagamentoResponseDTO resultado = pagamentoRealizarUseCase.realizar(pagamentoRequest);

        assertEquals(StatusPagamento.PENDENTE, resultado.getStatus());
    }

    @Test
    void realizar_ExcecaoAoRealizarPagamento_GeraNegocioException() {
        PagamentoRequestDTO pagamentoRequest = criarPagamentoRequestDTO();

        doThrow(new RuntimeException("Simulando exceção ao realizar pagamento")).when(pagamentoGateway).pagar(any());

        assertThrows(NegocioException.class, () -> pagamentoRealizarUseCase.realizar(pagamentoRequest));
    }

    private PagamentoRequestDTO criarPagamentoRequestDTO() {
        var dto = new PagamentoRequestDTO();
        var produtos = List.of(new ProdutoResponseDTO());
        dto.setPedido(new PedidoRequestDTO(1L, StatusPagamento.PENDENTE, produtos, 2L));
        return dto;
    }
}

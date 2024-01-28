package br.com.postech.pedidos.business.usecases.implementation.pagamento;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.request.PedidoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusPagamento;
import br.com.postech.pedidos.adapters.gateways.PagamentoGateway;
import br.com.postech.pedidos.business.exceptions.NegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoRealizarUseCaseTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private PagamentoRealizarUseCase pagamentoRealizarUseCase;

    @Test
    void realizar_PagamentoAprovado_RetornaPagamentoAprovado() {
        PagamentoRequestDTO pagamentoRequest = criarPagamentoRequestDTO();
        PagamentoResponseDTO pagamentoResponse = new PagamentoResponseDTO();
        pagamentoResponse.setStatus(StatusPagamento.APROVADO);

        when(pagamentoGateway.pagar(any())).thenReturn(pagamentoResponse);

        PagamentoResponseDTO resultado = pagamentoRealizarUseCase.realizar(pagamentoRequest);

        assertEquals(StatusPagamento.APROVADO, resultado.getStatus());
    }

    @Test
    void realizar_PagamentoNaoAprovado_GeraNegocioException() {
        PagamentoRequestDTO pagamentoRequest = criarPagamentoRequestDTO();

        when(pagamentoGateway.pagar(any())).thenReturn(new PagamentoResponseDTO());

        assertThrows(NegocioException.class, () -> pagamentoRealizarUseCase.realizar(pagamentoRequest));
    }

    @Test
    void realizar_ExcecaoAoRealizarPagamento_GeraNegocioException() {
        PagamentoRequestDTO pagamentoRequest = criarPagamentoRequestDTO();

        when(pagamentoGateway.pagar(any())).thenThrow(new RuntimeException("Simulando exceção ao realizar pagamento"));

        assertThrows(NegocioException.class, () -> pagamentoRealizarUseCase.realizar(pagamentoRequest));
    }

    private PagamentoRequestDTO criarPagamentoRequestDTO() {
        var dto = new PagamentoRequestDTO();
        var produtos = List.of(new ProdutoResponseDTO());
        var cliente = new ClienteResponseDTO();
        cliente.setCpf("123.321.123-00");
        dto.setPedido(new PedidoRequestDTO(produtos, cliente));
        return dto;
    }
}

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
public class PagamentoDesfazerUseCaseTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private PagamentoDesfazerUseCase pagamentoDesfazerUseCase;

    @Test
    void realizar_DesfazPagamentoComSucesso_RetornaPagamentoDesfeito() {
        PagamentoRequestDTO pagamentoRequest = criarPagamentoRequestDTO();
        PagamentoResponseDTO pagamentoResponse = new PagamentoResponseDTO();
        pagamentoResponse.setStatus(StatusPagamento.ROLLBACK);

        when(pagamentoGateway.desfazerPagamento(any())).thenReturn(pagamentoResponse);

        PagamentoResponseDTO resultado = pagamentoDesfazerUseCase.realizar(pagamentoRequest);

        assertEquals(StatusPagamento.ROLLBACK, resultado.getStatus());
    }

    @Test
    void realizar_ExcecaoAoDesfazerPagamento_GeraNegocioException() {
        PagamentoRequestDTO pagamentoRequest = criarPagamentoRequestDTO();
        when(pagamentoGateway.desfazerPagamento(any())).thenThrow(new RuntimeException("Simulando exceção ao desfazer pagamento"));
        assertThrows(NegocioException.class, () -> pagamentoDesfazerUseCase.realizar(pagamentoRequest));
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
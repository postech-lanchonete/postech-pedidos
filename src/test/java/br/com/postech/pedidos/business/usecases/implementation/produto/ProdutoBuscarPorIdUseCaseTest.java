package br.com.postech.pedidos.business.usecases.implementation.produto;

import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.adapters.gateways.ProdutoGateway;
import br.com.postech.pedidos.business.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProdutoBuscarPorIdUseCaseTest {

    @Mock
    private ProdutoGateway produtoGateway;

    @InjectMocks
    private ProdutoBuscarPorIdUseCase produtoBuscarPorIdUseCase;

    @Test
    void realizar_ProdutoEncontrado_RetornaProdutoResponseDTO() {
        Long idProduto = 1L;
        ProdutoResponseDTO produtoResponse = criarProdutoResponseDTO();

        when(produtoGateway.buscarPorId(idProduto)).thenReturn(produtoResponse);

        ProdutoResponseDTO resultado = produtoBuscarPorIdUseCase.realizar(idProduto);

        assertEquals(produtoResponse, resultado);
    }

    @Test
    void realizar_ExcecaoAoBuscarProduto_GeraNotFoundException() {
        Long idProduto = 3L;

        when(produtoGateway.buscarPorId(idProduto)).thenThrow(new RuntimeException("Simulando exceção ao buscar produto"));

        assertThrows(NotFoundException.class, () -> produtoBuscarPorIdUseCase.realizar(idProduto));
    }

    private ProdutoResponseDTO criarProdutoResponseDTO() {
        var dto = new ProdutoResponseDTO();
        dto.setNome("Hamburguer");
        dto.setPreco(BigDecimal.TEN);
        return dto;
    }
}

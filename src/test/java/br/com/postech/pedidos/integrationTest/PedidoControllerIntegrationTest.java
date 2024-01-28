package br.com.postech.pedidos.integrationTest;

import br.com.postech.pedidos.adapters.dto.CriacaoPedidoDTO;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PagamentoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.adapters.enums.StatusPagamento;
import br.com.postech.pedidos.adapters.gateways.ClienteGateway;
import br.com.postech.pedidos.adapters.gateways.PagamentoGateway;
import br.com.postech.pedidos.adapters.gateways.PedidoGateway;
import br.com.postech.pedidos.adapters.gateways.ProdutoGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PedidoControllerIntegrationTest {

    public static final String CPF = "123.123.123-00";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteGateway clienteGateway;
    @MockBean
    private PedidoGateway pedidoGateway;
    @MockBean
    private ProdutoGateway produtoGateway;
    @MockBean
    private PagamentoGateway pagamentoGateway;

    @Test
    void criar_DeveRetornar400_QuandoChamadaIncorretaSemProdutos() throws Exception {
        var criacaoPedidoDTO = new CriacaoPedidoDTO();
        criacaoPedidoDTO.setCpfCliente(CPF);

        mockMvc.perform(post("/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(criacaoPedidoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criar_DeveRetornar400_QuandoChamadaIncorretaSemCliente() throws Exception {
        var criacaoPedidoDTO = new CriacaoPedidoDTO();
        criacaoPedidoDTO.setIdsProdutos(List.of(12L, 21L));

        mockMvc.perform(post("/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(criacaoPedidoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criar_DeveRegistrarPedidoNoBanco_QuandoReceberDadosCorretos() throws Exception {
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setNome("Nome");
        when(clienteGateway.buscarPorCpf(any())).thenReturn(clienteResponseDTO);

        ProdutoResponseDTO produto1 = criarProdutoRequest("Hamburguer", BigDecimal.TEN);
        ProdutoResponseDTO produto2 = criarProdutoRequest("Salada", BigDecimal.TEN);
        when(produtoGateway.buscarPorId(12L)).thenReturn(produto1);
        when(produtoGateway.buscarPorId(21L)).thenReturn(produto2);

        var pagamento = new PagamentoResponseDTO();
        pagamento.setStatus(StatusPagamento.APROVADO);
        when(pagamentoGateway.pagar(any())).thenReturn(pagamento);

        var pedidoResponse = new PedidoResponseDTO();
        pedidoResponse.setId(1L);
        when(pedidoGateway.enviarParaProducao(any())).thenReturn(pedidoResponse);

        var criacaoPedidoDTO = new CriacaoPedidoDTO();
        criacaoPedidoDTO.setIdsProdutos(List.of(12L, 21L));
        criacaoPedidoDTO.setCpfCliente(CPF);

        mockMvc.perform(post("/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(criacaoPedidoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    private ProdutoResponseDTO criarProdutoRequest(String nome, BigDecimal preco) {
        ProdutoResponseDTO produto = new ProdutoResponseDTO();
        produto.setNome(nome);
        produto.setPreco(preco);
        return produto;
    }

    private static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

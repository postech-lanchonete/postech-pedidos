package br.com.postech.pedidos.adapters.adapter;

import br.com.postech.pedidos.adapters.dto.request.PagamentoRequestDTO;
import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.PedidoResponseDTO;
import br.com.postech.pedidos.adapters.dto.response.ProdutoResponseDTO;
import br.com.postech.pedidos.core.entities.Pedido;
import br.com.postech.pedidos.core.entities.Produto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoAdapter {
    @Mapping(target = "cliente.id", source = "idCliente")
    PedidoResponseDTO toDto(Pedido pedido);

    @Mapping(target = "id", source = "pedido.id")
    PedidoResponseDTO toDto(Pedido pedido, ClienteResponseDTO cliente);

    PagamentoRequestDTO toPedidoRequestDTO(Pedido pedido);

    @Mapping(target = "idCliente", source = "cliente.id")
    @Mapping(target = "produtos", expression = "java(mapProdutos(listaProdutos))")
    @Mapping(target = "status", constant = "PENDENTE")
    Pedido toEntity(ClienteResponseDTO cliente, List<ProdutoResponseDTO> listaProdutos);

    default List<Produto> mapProdutos(List<ProdutoResponseDTO> produtos) {
        return produtos.stream()
                .map(this::mapProduto)
                .toList();
    }

    @Mapping(target = "id", ignore = true)
    Produto mapProduto(ProdutoResponseDTO produtoResponseDTO);

    @AfterMapping
    default void setDefaultValues(@MappingTarget Pedido pedido) {
        pedido.setDataCriacao(LocalDateTime.now());
    }
}

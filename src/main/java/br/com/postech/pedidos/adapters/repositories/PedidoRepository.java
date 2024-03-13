package br.com.postech.pedidos.adapters.repositories;

import br.com.postech.pedidos.core.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
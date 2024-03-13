package br.com.postech.pedidos.core.entities;

import br.com.postech.pedidos.core.enums.StatusDoPedido;
import br.com.postech.pedidos.core.enums.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
@Entity
@Table(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCliente;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Produto> produtos;

    @Enumerated(EnumType.STRING)
    private StatusDoPedido status;

    private LocalDateTime dataCriacao;

    private StatusPagamento statusPagamento;
}
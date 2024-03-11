package br.com.postech.pedidos.core.enums;

import br.com.postech.pedidos.business.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusDoPedidoTest {

    @Test
    void encontrarEnumPorString_Existente() {
        String valor = "PENDENTE";

        StatusDoPedido status = StatusDoPedido.encontrarEnumPorString(valor);

        assertEquals(StatusDoPedido.PENDENTE, status);
    }

    @Test
    void encontrarEnumPorString_NaoExistente() {
        String valor = "INEXISTENTE";

        assertThrows(BadRequestException.class, () -> StatusDoPedido.encontrarEnumPorString(valor));
    }

}
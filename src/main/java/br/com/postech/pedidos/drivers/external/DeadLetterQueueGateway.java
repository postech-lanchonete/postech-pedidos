package br.com.postech.pedidos.drivers.external;

public interface DeadLetterQueueGateway {
    void enviar(String topico, String pagamentoJson);
}

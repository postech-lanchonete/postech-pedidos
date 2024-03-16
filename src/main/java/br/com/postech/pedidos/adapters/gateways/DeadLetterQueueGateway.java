package br.com.postech.pedidos.adapters.gateways;

public interface DeadLetterQueueGateway {
    void enviar(String topico, String pagamentoJson);
}

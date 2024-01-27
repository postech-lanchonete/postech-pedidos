package br.com.postech.pedidos.drivers.external.notificacao;

import br.com.postech.pedidos.adapters.dto.response.ClienteResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificacaoClienteApi implements NotificacaoClientePort {
    public void notificaCliente(ClienteResponseDTO cliente, String mensagem) {
        log.info("............. Conectando a plataforma de envio de mensagem .............");
        log.info("Esta é apenas uma simulação de envio de mensagem para o cliente {} com o e-mail {}",
                cliente.getNome(), cliente.getEmail());
        log.debug("Aviso de pedido aprovado enviado com sucesso para o cliente com cpf {}", cliente.getCpf());
    }

}

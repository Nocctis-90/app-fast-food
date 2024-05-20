package br.com.appfastfood.pedido.aplicacao.adaptadores;

import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.MensagemSNS;
import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.MessageHandler;
import br.com.appfastfood.pedido.usecase.portas.PedidoServico;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
public class CarrinhoEventosController  {

    private final PedidoServico pedidoServico;

    private final MessageHandler messageHandler;

    public CarrinhoEventosController(PedidoServico pedidoServico, MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.pedidoServico = pedidoServico;
    }

   @PostMapping("/carrinho-fechado")
    public void handleSnsCarrinhoFechado(@RequestBody String notification) {

        ObjectMapper objectMapper = new ObjectMapper();
        MensagemSNS snsMessage;
        try {
            snsMessage = objectMapper.readValue(notification, MensagemSNS.class);
            // Agora você pode verificar o tipo de mensagem e executar a ação apropriada
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing SNS message", e);
        }

       messageHandler.handleMessage(notification, pedidoServico::preparaPedido);

    }
}


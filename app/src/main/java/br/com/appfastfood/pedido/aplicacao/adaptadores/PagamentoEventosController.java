package br.com.appfastfood.pedido.aplicacao.adaptadores;

import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.MensagemSNS;
import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.MessageHandler;
import br.com.appfastfood.pedido.usecase.portas.CarrinhoServico;
import br.com.appfastfood.pedido.usecase.portas.PedidoServico;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

@RestController
public class PagamentoEventosController {

    private PedidoServico pedidoServico;

    private CarrinhoServico carrinhoServico;

    private MessageHandler messageHandler;
    public PagamentoEventosController(PedidoServico pedidoServico, CarrinhoServico carrinhoServico, MessageHandler messageHandler) {
        this.carrinhoServico = carrinhoServico;
        this.pedidoServico = pedidoServico;
        this.messageHandler = messageHandler;
    }

    @PostMapping("/pagamento-efetuado")
    public void handleSnsPagamentoEfetuadoMessage(@RequestBody String notification) {
        ObjectMapper objectMapper = new ObjectMapper();
        MensagemSNS snsMessage;
        try {
            snsMessage = objectMapper.readValue(notification, MensagemSNS.class);
            // Agora você pode verificar o tipo de mensagem e executar a ação apropriada
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing SNS message", e);
        }
        messageHandler.handleMessage(notification, carrinhoServico::fechaCarrinho);


    }

    @PostMapping("/pagamento-recusado")
    public void handleSnsPagamentoRecusadoMessage(@RequestBody String notification) {
        ObjectMapper objectMapper = new ObjectMapper();
        MensagemSNS snsMessage;
        try {
            snsMessage = objectMapper.readValue(notification, MensagemSNS.class);
            // Agora você pode verificar o tipo de mensagem e executar a ação apropriada
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing SNS message", e);
        }

        messageHandler.handleMessage(notification, pedidoServico::cancelaPedido);
    }
}

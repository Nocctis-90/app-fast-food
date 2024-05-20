package br.com.appfastfood.pedido.aplicacao.adaptadores;


import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.MensagemSNS;
import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.MessageHandler;
import br.com.appfastfood.pedido.usecase.portas.PagamentoServico;
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
public class PedidoEventosController {

        private final MessageHandler messageHandler;
        private final PagamentoServico pagamentoServico;

        private final PedidoServico pedidoServico;

        public PedidoEventosController(PagamentoServico pagamentoServico, PedidoServico pedidoServico, MessageHandler messageHandler) {
            this.pedidoServico = pedidoServico;
            this.pagamentoServico = pagamentoServico;
            this.messageHandler = messageHandler;
        }

        @PostMapping("/pedido-criado")
        public void handlePedidoCriado(@RequestBody String notification) {
            ObjectMapper objectMapper = new ObjectMapper();
            MensagemSNS snsMessage;
            try {
                snsMessage = objectMapper.readValue(notification, MensagemSNS.class);

                // Agora você pode verificar o tipo de mensagem e executar a ação apropriada
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error deserializing SNS message", e);
            }
            try {
                Thread.sleep(30000); // 30000 milliseconds = 30 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageHandler.handleMessage(notification, pagamentoServico::efetuaPagamento);

        }


    @PostMapping("/pedido-preparado")
    public void handlePedidoPreparadoSnsMessage(@RequestBody String notification) {
        ObjectMapper objectMapper = new ObjectMapper();
        MensagemSNS snsMessage;
        try {
            snsMessage = objectMapper.readValue(notification, MensagemSNS.class);
            // Agora você pode verificar o tipo de mensagem e executar a ação apropriada
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing SNS message", e);
        }
        try {
            Thread.sleep(30000); // 30000 milliseconds = 30 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        messageHandler.handleMessage(notification, pedidoServico::finalizaPedido);
    }

    @PostMapping("/pedido-finalizado")
    public void handlePedidoFinalizadoSnsMessage(@RequestBody String notification) {
        ObjectMapper objectMapper = new ObjectMapper();
        MensagemSNS snsMessage;
        try {
            snsMessage = objectMapper.readValue(notification, MensagemSNS.class);
            // Agora você pode verificar o tipo de mensagem e executar a ação apropriada
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing SNS message", e);
        }

        messageHandler.handleMessage(notification, pedidoServico::notificaCliente);
    }

}


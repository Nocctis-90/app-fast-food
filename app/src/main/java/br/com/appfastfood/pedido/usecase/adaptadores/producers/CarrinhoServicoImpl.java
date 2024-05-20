package br.com.appfastfood.pedido.usecase.adaptadores.producers;

import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.TopicHandler;
import br.com.appfastfood.pedido.infraestrutura.menssagem.requisicao.PedidoEventoRequisicao;
import br.com.appfastfood.pedido.usecase.portas.CarrinhoServico;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CarrinhoServicoImpl implements CarrinhoServico {

    private final TopicHandler snsTopic;
    private final ObjectMapper objectMapper;

    public CarrinhoServicoImpl(TopicHandler snsTopicHandler, ObjectMapper objectMapper) {
        this.snsTopic = snsTopicHandler;
        this.objectMapper = objectMapper;
    }
    @Override
    public void fechaCarrinho(String message) {
        try {
            PedidoEventoRequisicao dto = objectMapper.readValue(message, PedidoEventoRequisicao.class);
            snsTopic.publish(objectMapper.writeValueAsString(dto), "arn:aws:sns:us-east-1:101478099523:fecha-carrinho");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

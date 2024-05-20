package br.com.appfastfood.pedido.usecase.adaptadores.producers;


import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.TopicHandler;
import br.com.appfastfood.pedido.infraestrutura.menssagem.requisicao.PedidoEventoRequisicao;
import br.com.appfastfood.pedido.usecase.portas.PagamentoServico;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PagamentoServicoImpl implements PagamentoServico {

    private final TopicHandler snsTopic;

    private final ObjectMapper objectMapper;

    public PagamentoServicoImpl(TopicHandler snsTopicHandler, ObjectMapper objectMapper) {

        this.snsTopic = snsTopicHandler;
        this.objectMapper = objectMapper;
    }

    public void efetuaPagamento(String message)  {
        try {
            PedidoEventoRequisicao dto = objectMapper.readValue(message, PedidoEventoRequisicao.class);
            snsTopic.publish(message, "arn:aws:sns:us-east-1:101478099523:efetua-pagamento");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

package br.com.appfastfood.pedido.usecase.adaptadores.producers;

import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.PedidoRequisicao;
import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.TopicHandler;
import br.com.appfastfood.pedido.infraestrutura.menssagem.requisicao.PedidoEventoRequisicao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PedidoServicoImplTest {

    @Mock
    private TopicHandler snsTopic;

    @Mock
    private ObjectMapper objectMapper;

    private PedidoServicoImpl pedidoServico;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoServico = new PedidoServicoImpl(snsTopic, objectMapper);
    }

//    @Test
//    public void testCriar() throws JsonProcessingException {
//        PedidoRequisicao pedidoRequisicao = new PedidoRequisicao();
//        String pedidoJson = objectMapper.writeValueAsString(pedidoRequisicao);
//        when(objectMapper.writeValueAsString(pedidoRequisicao)).thenReturn(pedidoJson);
//
//        pedidoServico.criar(pedidoRequisicao);
//
//        verify(snsTopic, times(1)).publish(pedidoJson, "arn:aws:sns:us-east-1:000000000000:pedido-criado");
//    }

    @Test
    public void testPreparaPedido() throws JsonProcessingException {
        String pedido = "test message";
        PedidoEventoRequisicao pedidoEventoRequisicao = new PedidoEventoRequisicao();
        when(objectMapper.readValue(pedido, PedidoEventoRequisicao.class)).thenReturn(pedidoEventoRequisicao);

        pedidoServico.preparaPedido(pedido);

        verify(snsTopic, times(1)).publish(pedido, "arn:aws:sns:us-east-1:101478099523:prepara-pedido");
    }

    @Test
    public void testFinalizaPedido() throws JsonProcessingException {
        String pedido = "test message";
        PedidoEventoRequisicao pedidoEventoRequisicao = new PedidoEventoRequisicao();
        when(objectMapper.readValue(pedido, PedidoEventoRequisicao.class)).thenReturn(pedidoEventoRequisicao);

        pedidoServico.finalizaPedido(pedido);

        verify(snsTopic, times(1)).publish(pedido, "arn:aws:sns:us-east-1:101478099523:finaliza-pedido");
    }

    @Test
    public void testCancelaPedido() throws JsonProcessingException {
        String pedido = "test message";
        PedidoEventoRequisicao pedidoEventoRequisicao = new PedidoEventoRequisicao();
        when(objectMapper.readValue(pedido, PedidoEventoRequisicao.class)).thenReturn(pedidoEventoRequisicao);

        pedidoServico.cancelaPedido(pedido);

        verify(snsTopic, times(1)).publish(pedido, "arn:aws:sns:us-east-1:101478099523:cancela-pedido");
    }
}
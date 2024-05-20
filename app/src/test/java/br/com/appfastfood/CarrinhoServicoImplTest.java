package br.com.appfastfood.pedido.usecase.adaptadores.producers;

import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.TopicHandler;
import br.com.appfastfood.pedido.infraestrutura.menssagem.requisicao.PedidoEventoRequisicao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CarrinhoServicoImplTest {

    @Mock
    private TopicHandler snsTopic;

    @Mock
    private ObjectMapper objectMapper;

    private CarrinhoServicoImpl carrinhoServico;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        carrinhoServico = new CarrinhoServicoImpl(snsTopic, objectMapper);
    }

    @Test
    public void testFechaCarrinho() throws JsonProcessingException {
        String message = "test message";
        PedidoEventoRequisicao pedidoEventoRequisicao = new PedidoEventoRequisicao();
        when(objectMapper.readValue(message, PedidoEventoRequisicao.class)).thenReturn(pedidoEventoRequisicao);
        when(objectMapper.writeValueAsString(pedidoEventoRequisicao)).thenReturn(message);

        carrinhoServico.fechaCarrinho(message);

        verify(snsTopic, times(1)).publish(message, "arn:aws:sns:us-east-1:101478099523:fecha-carrinho");
    }
}
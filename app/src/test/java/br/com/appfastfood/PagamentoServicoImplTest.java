import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.TopicHandler;
import br.com.appfastfood.pedido.infraestrutura.menssagem.requisicao.PedidoEventoRequisicao;
import br.com.appfastfood.pedido.usecase.adaptadores.producers.PagamentoServicoImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PagamentoServicoImplTest {

    @Mock
    private TopicHandler snsTopic;

    @Mock
    private ObjectMapper objectMapper;

    private PagamentoServicoImpl pagamentoServico;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pagamentoServico = new PagamentoServicoImpl(snsTopic, objectMapper);
    }

    @Test
    public void testEfetuaPagamentoWithValidMessage() throws Exception {
        String message = "test message";
        PedidoEventoRequisicao pedidoEventoRequisicao = new PedidoEventoRequisicao();
        when(objectMapper.readValue(message, PedidoEventoRequisicao.class)).thenReturn(pedidoEventoRequisicao);

        pagamentoServico.efetuaPagamento(message);

        verify(snsTopic, times(1)).publish(message, "arn:aws:sns:us-east-1:101478099523:efetua-pagamento");
    }

    @Test
    public void testEfetuaPagamentoWithInvalidMessage() throws Exception {
        String message = "invalid message";
        when(objectMapper.readValue(message, PedidoEventoRequisicao.class)).thenThrow(JsonProcessingException.class);

        try {
            pagamentoServico.efetuaPagamento(message);
        } catch (RuntimeException e) {
            // Verificar se a exceção correta foi lançada
            assert(e.getCause() instanceof JsonProcessingException);
        }
    }

    @Test
    public void testEfetuaPagamentoWithNullMessage() throws Exception {
        String message = null;
        when(objectMapper.readValue(message, PedidoEventoRequisicao.class)).thenThrow(JsonProcessingException.class);

        try {
            pagamentoServico.efetuaPagamento(message);
        } catch (RuntimeException e) {
            // Verificar se a exceção correta foi lançada
            assert(e.getCause() instanceof JsonProcessingException);
        }
    }
}
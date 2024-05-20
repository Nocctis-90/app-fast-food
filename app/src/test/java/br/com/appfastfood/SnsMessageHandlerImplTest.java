package br.com.appfastfood.pedido.infraestrutura.menssagem.adaptadores;

import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.MensagemSNS;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class SnsMessageHandlerImplTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Consumer<String> function;

    private SnsMessageHandlerImpl snsMessageHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        snsMessageHandler = new SnsMessageHandlerImpl();
    }

    @Test
    public void testHandleMessageSubscriptionConfirmation() throws Exception {
        String message = "{\"Type\":\"SubscriptionConfirmation\",\"SubscribeURL\":\"http://example.com\"}";
        MensagemSNS snsMessage = new MensagemSNS();
        snsMessage.setType("SubscriptionConfirmation");
        snsMessage.setSubscribeURL("http://example.com");
        when(objectMapper.readValue(message, MensagemSNS.class)).thenReturn(snsMessage);

        snsMessageHandler.handleMessage(message, function);

        verify(function, never()).accept(anyString());
    }

    @Test
    public void testHandleMessageNotification() throws Exception {
        String message = "{\"Type\":\"Notification\", \"Message\":\"test message\"}";
        MensagemSNS snsMessage = new MensagemSNS();
        snsMessage.setType("Notification");
        snsMessage.setMessage("test message");
        when(objectMapper.readValue(message, MensagemSNS.class)).thenReturn(snsMessage);

        snsMessageHandler.handleMessage(message, function);

        verify(function, times(1)).accept("test message");
    }

    @Test
    public void testHandleMessageUnsubscribeConfirmation() throws Exception {
        String message = "{\"Type\":\"UnsubscribeConfirmation\"}";
        MensagemSNS snsMessage = new MensagemSNS();
        snsMessage.setType("UnsubscribeConfirmation");
        when(objectMapper.readValue(message, MensagemSNS.class)).thenReturn(snsMessage);

        snsMessageHandler.handleMessage(message, function);

        verify(function, never()).accept(anyString());
    }

    @Test
    public void testHandleMessageUnknownType() throws Exception {
        String message = "{\"Type\":\"UnknownType\"}";
        MensagemSNS snsMessage = new MensagemSNS();
        snsMessage.setType("UnknownType");
        when(objectMapper.readValue(message, MensagemSNS.class)).thenReturn(snsMessage);

        snsMessageHandler.handleMessage(message, function);

        verify(function, never()).accept(anyString());
    }
}
package br.com.appfastfood;

import br.com.appfastfood.pedido.infraestrutura.menssagem.adaptadores.SNSTopicHandlerImpl;
import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.TopicHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SNSTopicHandlerImplTest {

    @Mock
    private AmazonSNS snsClient;

    private TopicHandler topicHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        topicHandler = new SNSTopicHandlerImpl(snsClient);
    }

    @Test
    public void testPublishSuccess() {
        String message = "test message";
        String topicArn = "arn:aws:sns:us-east-1:000000000000:test-topic";
        PublishResult publishResult = new PublishResult().withMessageId("testMessageId");
        when(snsClient.publish(any(PublishRequest.class))).thenReturn(publishResult);

        topicHandler.publish(message, topicArn);

        verify(snsClient, times(1)).publish(any(PublishRequest.class));
    }

    @Test
    public void testPublishException() {
        String message = "test message";
        String topicArn = "arn:aws:sns:us-east-1:000000000000:test-topic";
        when(snsClient.publish(any(PublishRequest.class))).thenThrow(new RuntimeException("Test exception"));

        topicHandler.publish(message, topicArn);

        verify(snsClient, times(1)).publish(any(PublishRequest.class));
    }
}
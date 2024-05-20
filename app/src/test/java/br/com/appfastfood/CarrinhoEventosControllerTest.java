package br.com.appfastfood.pedido.aplicacao.adaptadores;

import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.MessageHandler;
import br.com.appfastfood.pedido.usecase.portas.PedidoServico;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.function.Consumer;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarrinhoEventosControllerTest {

    @Mock
    private PedidoServico pedidoServico;

    @Mock
    private MessageHandler messageHandler;


    @Captor
    private ArgumentCaptor<Consumer<String>> consumerCaptor;

    private CarrinhoEventosController carrinhoEventosController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        carrinhoEventosController = new CarrinhoEventosController(pedidoServico, messageHandler);
        mockMvc = MockMvcBuilders.standaloneSetup(carrinhoEventosController).build();
    }

    @Test
    public void testHandleSnsCarrinhoFechadoValidNotification() throws Exception {
        String validNotification = "{\"Type\":\"Notification\", \"Message\":\"test message\"}";

        mockMvc.perform(post("/carrinho-fechado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validNotification))
                .andExpect(status().isOk());

        verify(messageHandler, times(1)).handleMessage(eq(validNotification), consumerCaptor.capture());

        // Now you can make assertions about the captor
        Consumer<String> actualConsumer = consumerCaptor.getValue();
        // Use the consumer to test the expected behavior
    }

    @Test
    public void testHandleSnsCarrinhoFechadoUnknownType() throws Exception {
        String unknownTypeNotification = "{\"Type\":\"UnknownType\", \"Message\":\"test message\"}";

        mockMvc.perform(post("/carrinho-fechado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unknownTypeNotification))
                .andExpect(status().isOk());

        verify(messageHandler, times(1)).handleMessage(eq(unknownTypeNotification), consumerCaptor.capture());

        // Agora você pode fazer asserções sobre o captor
        Consumer<String> actualConsumer = consumerCaptor.getValue();
        // Use o consumidor para testar o comportamento esperado
    }

}
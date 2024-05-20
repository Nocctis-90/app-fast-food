package br.com.appfastfood.configuracoes;

import br.com.appfastfood.AppFastfoodApplication;
import br.com.appfastfood.configuracoes.logs.Log;
import br.com.appfastfood.configuracoes.logs.Log4jLog;
import br.com.appfastfood.pedido.infraestrutura.menssagem.adaptadores.SNSTopicHandlerImpl;
import br.com.appfastfood.pedido.infraestrutura.menssagem.adaptadores.SnsMessageHandlerImpl;
import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.MessageHandler;
import br.com.appfastfood.pedido.infraestrutura.menssagem.portas.TopicHandler;
import br.com.appfastfood.pedido.usecase.adaptadores.producers.CarrinhoServicoImpl;
import br.com.appfastfood.pedido.usecase.adaptadores.producers.PagamentoServicoImpl;
import br.com.appfastfood.pedido.usecase.adaptadores.producers.PedidoServicoImpl;
import br.com.appfastfood.pedido.usecase.portas.CarrinhoServico;
import br.com.appfastfood.pedido.usecase.portas.PagamentoServico;
import br.com.appfastfood.pedido.usecase.portas.PedidoServico;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sns.SnsClient;
import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
@ComponentScan(basePackageClasses = AppFastfoodApplication.class)
public class BeanConfiguration {

    @Bean
    public AmazonSNS amazonSNS() {
        // Configuração básica de um cliente AmazonSNS usando as credenciais padrão do SDK
        return AmazonSNSClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

    @Bean
    public MessageHandler messageHandler() {
        return new SnsMessageHandlerImpl();
    }
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    TopicHandler topicHandler(){
        return new SNSTopicHandlerImpl(amazonSNS());
    }

    @Bean
    PedidoServico pedidoServico(TopicHandler handler, ObjectMapper mapper) { return new PedidoServicoImpl(handler, mapper); }
    
    @Bean
    PagamentoServico pagamentoServico(TopicHandler handler, ObjectMapper mapper){
        return new PagamentoServicoImpl(handler, mapper);
    }

    @Bean
    CarrinhoServico carrinhoServico(TopicHandler handler, ObjectMapper mapper){
        return new CarrinhoServicoImpl(handler, mapper);
    }


    @Bean
    Log log(){
        return new Log4jLog(BeanConfiguration.class);
    }
}

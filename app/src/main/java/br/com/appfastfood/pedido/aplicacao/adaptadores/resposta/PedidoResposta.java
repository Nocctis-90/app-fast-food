package br.com.appfastfood.pedido.aplicacao.adaptadores.resposta;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;
@Builder()
@Getter

public class PedidoResposta {
    
    private Map<String, Long> produto;
    private String idCliente;
    private BigDecimal valorTotal;
    private String status;
    private String tempoEspera;
}

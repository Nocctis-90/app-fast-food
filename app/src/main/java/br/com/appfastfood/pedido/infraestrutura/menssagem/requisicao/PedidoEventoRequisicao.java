package br.com.appfastfood.pedido.infraestrutura.menssagem.requisicao;

import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.PedidoRequisicao;
import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.ProdutosReq;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.owasp.encoder.Encode;


import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder()
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoEventoRequisicao implements Serializable {

    @JsonProperty("produtos")
    private List<ProdutosEventoRequisicao> produtos;

    @JsonProperty("id_cliente")
    private String idCliente;

    @JsonProperty("valor_total")
    private Double valorTotal;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tempo_espera")
    private String tempoEspera;

    @JsonProperty("id_pedido")
    private String idPedido;

    @JsonProperty("status_pagamento")
    private String statusPagamento;

    public PedidoEventoRequisicao(List<ProdutosEventoRequisicao> produtos,
                            String idCliente,
                            Double valorTotal,
                            String status,
                            String tempoEspera, String idPedido, String statusPagamento) {
        this.idCliente = Encode.forHtml(idCliente);
        this.valorTotal = valorTotal;
        this.produtos = produtos;
        this.status = Encode.forHtml(status);
        this.tempoEspera = Encode.forHtml(tempoEspera);
        this.idPedido = Encode.forHtml(idPedido);
        this.statusPagamento = Encode.forHtml(statusPagamento);
    }

    public PedidoEventoRequisicao(){}

    public PedidoEventoRequisicao sanitizarEntrada(PedidoEventoRequisicao req) {
        return new PedidoEventoRequisicao(req.getProdutos(), req.getIdCliente(), req.getValorTotal(), req.getStatus(), req.getTempoEspera(), req.getIdPedido(), req.getStatusPagamento());
    }
}

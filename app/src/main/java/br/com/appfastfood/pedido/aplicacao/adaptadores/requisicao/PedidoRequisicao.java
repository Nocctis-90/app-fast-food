package br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao;

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
public class PedidoRequisicao implements Serializable {

    @JsonProperty("produtos")
    private List<ProdutosReq> produtos;

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

    public PedidoRequisicao(List<ProdutosReq> produtos,
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

    public PedidoRequisicao(){}

    public PedidoRequisicao sanitizarEntrada(PedidoRequisicao req) {
        return new PedidoRequisicao(req.getProdutos(), req.getIdCliente(), req.getValorTotal(), req.getStatus(), req.getTempoEspera(), req.getIdPedido(), req.getStatusPagamento());
    }
}

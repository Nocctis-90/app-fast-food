package br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutosReq implements Serializable {

    @JsonProperty("id_produto")
    private String idProduto;

    @JsonProperty("quantidade_produto")
    private String quantidadeProduto;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("preco")
    private Double preco;

    @JsonProperty("categoria")
    private String categoria;

    @JsonProperty("uriImagem")
    private String uriImagem;

    public ProdutosReq(){}

}

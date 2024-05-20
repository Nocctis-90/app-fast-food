package br.com.appfastfood.pedido.usecase.portas;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface PagamentoServico {
    void efetuaPagamento(String message);

}

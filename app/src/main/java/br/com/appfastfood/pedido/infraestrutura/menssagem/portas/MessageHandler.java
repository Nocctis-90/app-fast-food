package br.com.appfastfood.pedido.infraestrutura.menssagem.portas;

import java.util.function.Consumer;

public interface MessageHandler{

    void handleMessage(String message, Consumer<String> function);
}

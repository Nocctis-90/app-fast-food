package br.com.appfastfood.configuracoes.logs;

public interface Log {
    void sucesso(String message);
    void aviso(String message);
    void erro(String message);
}

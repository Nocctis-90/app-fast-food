package br.com.appfastfood.configuracoes.execption;

public class BadRequestException extends IllegalAccessError {
    public BadRequestException(String MESSAGER) {
        super(MESSAGER);
    }
}

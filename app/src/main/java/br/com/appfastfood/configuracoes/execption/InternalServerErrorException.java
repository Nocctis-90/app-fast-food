package br.com.appfastfood.configuracoes.execption;

public class InternalServerErrorException extends IllegalAccessError {
    public static String MESSAGER;
    public InternalServerErrorException() {
        super(MESSAGER);
    }
}

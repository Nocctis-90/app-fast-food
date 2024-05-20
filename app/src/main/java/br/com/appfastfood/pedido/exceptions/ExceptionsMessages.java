package br.com.appfastfood.pedido.exceptions;

public enum ExceptionsMessages {
    PEDIDO_NAO_ENCONTRADO("Pedido não encontrado!"),
    PAGAMENTO_NAO_FOI_APROVADO("Pagamento não foi aprovado."),
    PEDIDO_JA_FINALIZADO("Pedido já está finalizado! Não é possível alterar seu status."),
    STATUS_PAGAMENTO_NAO_ENCONTRADO("O status de pagamento informado não existe."),
    PAGAMENTO_RECUSADO("Pagamento recusado!"),
    STATUS_PEDIDO_NAO_PERMITIDO("Status não permitido!");

    private final String value;

    ExceptionsMessages(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package br.com.appfastfood.configuracoes.execption;

import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.RequisicaoExcecao;
import br.com.appfastfood.configuracoes.logs.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    private Log logger;

    public HandlerException(Log logger) {
        this.logger = logger;
    }
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> badRequestException(BadRequestException e) {
        RequisicaoExcecao jsonExcecao = new RequisicaoExcecao(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        logger.aviso(jsonExcecao.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonExcecao);
    }
    @ExceptionHandler(value = InternalServerErrorException.class)
    public ResponseEntity<Object> internalServerErrorException(InternalServerErrorException e) {
        RequisicaoExcecao jsonExcecao = new RequisicaoExcecao(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        logger.aviso(jsonExcecao.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonExcecao);
    }

}

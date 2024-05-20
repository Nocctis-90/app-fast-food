package br.com.appfastfood.configuracoes.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Log4jLog implements Log{

    private final Logger logger;

    public Log4jLog(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    @Override
    public void sucesso(String message) {
        logger.info(message);
    }

    @Override
    public void aviso(String message) {
        logger.warn(message);
    }

    @Override
    public void erro(String message) {
        logger.error(message);
    }
}

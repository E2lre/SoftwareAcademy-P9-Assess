package com.mediscreen.assess.proxies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FeignException extends RuntimeException{
    private static final Logger logger = LogManager.getLogger(FeignException.class);
    public FeignException(String message) {
        super(message);
        logger.error(message);
    }
}


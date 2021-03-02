package com.mediscreen.assess.controller.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssessIdNotFoundException extends Exception {
    private static final Logger logger = LogManager.getLogger(AssessIdNotFoundException.class);
    public AssessIdNotFoundException(String s) {
        super(s);
        logger.error(s);
    }
}


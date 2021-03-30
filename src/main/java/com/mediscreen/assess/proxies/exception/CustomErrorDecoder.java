package com.mediscreen.assess.proxies.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private static final Logger logger = LogManager.getLogger(CustomErrorDecoder.class);
    @Override
    public Exception decode(String invoqueur, Response reponse) {
        logger.error("invoquer ppal "+invoqueur +"reponse "+ reponse);
        if(reponse.status() == 404 ) {
            logger.error("Feign Error 404 : invoquer "+invoqueur +"reponse "+ reponse);
            return new FeignException(
                    "Feign error : Not Found "
            );
        }

        return defaultErrorDecoder.decode(invoqueur, reponse);
    }

}

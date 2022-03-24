package es.gonzo.springboot.league.app.exceptions.feign;

import feign.FeignException;
import feign.Request;

public class NotFoundException extends FeignException {

    public NotFoundException(int status, String message, Request request) {
        super(status, message, request);
    }
}

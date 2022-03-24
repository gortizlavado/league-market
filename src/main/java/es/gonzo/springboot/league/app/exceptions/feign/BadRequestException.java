package es.gonzo.springboot.league.app.exceptions.feign;

import feign.FeignException;
import feign.Request;

public class BadRequestException extends FeignException {

    public BadRequestException(int status, String message, Request request) {
        super(status, message, request);
    }
}

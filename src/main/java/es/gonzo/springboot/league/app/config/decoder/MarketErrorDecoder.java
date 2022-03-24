package es.gonzo.springboot.league.app.config.decoder;

import es.gonzo.springboot.league.app.exceptions.feign.BadRequestException;
import es.gonzo.springboot.league.app.exceptions.feign.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class MarketErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int httpStatus = response.status();
        switch (httpStatus) {
            case 400:
                return new BadRequestException(response.status(), methodKey, response.request());
            case 404:
                return new NotFoundException(response.status(), methodKey, response.request());
            default:
                return new RuntimeException("Generic error");
        }
    }
}

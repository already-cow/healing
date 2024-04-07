package misoya.healing.domain.common.exception;

import org.springframework.http.HttpStatus;

public class ApiInternalServerErrorException extends AbstractApiException{


    public ApiInternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public ApiInternalServerErrorException(int code, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
    }
}

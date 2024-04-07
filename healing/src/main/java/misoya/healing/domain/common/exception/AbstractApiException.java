package misoya.healing.domain.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractApiException extends RuntimeException{
    private final HttpStatus status;
    private final int code;

    public AbstractApiException(HttpStatus status, int code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }
}

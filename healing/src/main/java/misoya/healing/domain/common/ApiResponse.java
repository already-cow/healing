package misoya.healing.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import misoya.healing.domain.common.exception.ApiInternalServerErrorException;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {

    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS");
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", data);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, data);
    }

    public static ApiResponse<String> error(String message) {
        throw new ApiInternalServerErrorException(message);
    }

}

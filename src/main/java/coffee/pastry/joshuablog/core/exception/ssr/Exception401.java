package coffee.pastry.joshuablog.core.exception.ssr;

import org.springframework.http.HttpStatus;

import coffee.pastry.joshuablog.dto.ResponseDTO;
import lombok.Getter;

@Getter // 인증 안됨
public class Exception401 extends RuntimeException {

    public Exception401(String message) {
        super(message);
    }

    public ResponseDTO<?> body() {
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.UNAUTHORIZED, "unAuthorized", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}

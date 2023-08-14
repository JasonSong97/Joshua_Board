package coffee.pastry.joshuablog.core.exception.ssr;

import org.springframework.http.HttpStatus;

import coffee.pastry.joshuablog.dto.ResponseDTO;
import lombok.Getter;

@Getter
public class Exception500 extends RuntimeException {

    public Exception500(String message) {
        super(message);
    }

    public ResponseDTO<?> body() {
        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "serverError", getMessage());
        return responseDto;
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

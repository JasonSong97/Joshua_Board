package coffee.pastry.joshuablog.core.exception.csr;

import org.springframework.http.HttpStatus;

import coffee.pastry.joshuablog.dto.ResponseDTO;
import lombok.Getter;

@Getter // 인증 안됨
public class ExceptionApi401 extends RuntimeException {

     public ExceptionApi401(String message) {
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
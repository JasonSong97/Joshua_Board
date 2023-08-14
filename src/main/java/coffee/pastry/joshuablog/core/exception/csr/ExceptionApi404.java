package coffee.pastry.joshuablog.core.exception.csr;

import org.springframework.http.HttpStatus;

import coffee.pastry.joshuablog.dto.ResponseDTO;
import lombok.Getter;

@Getter
public class ExceptionApi404 extends RuntimeException {

     public ExceptionApi404(String message) {
          super(message);
     }

     public ResponseDTO<?> body() {
          ResponseDTO<String> responseDto = new ResponseDTO<>();
          responseDto.fail(HttpStatus.NOT_FOUND, "notFound", getMessage());
          return responseDto;
     }

     public HttpStatus status() {
          return HttpStatus.NOT_FOUND;
     }
}
package coffee.pastry.joshuablog.core.exception.csr;

import org.springframework.http.HttpStatus;

import coffee.pastry.joshuablog.dto.ResponseDTO;
import lombok.Getter;

@Getter // 권한 없음
public class ExceptionApi403 extends RuntimeException {

     public ExceptionApi403(String message) {
          super(message);
     }

     public ResponseDTO<?> body() {
          ResponseDTO<String> responseDto = new ResponseDTO<>();
          responseDto.fail(HttpStatus.FORBIDDEN, "forbidden", getMessage());
          return responseDto;
     }

     public HttpStatus status() {
          return HttpStatus.FORBIDDEN;
     }
}
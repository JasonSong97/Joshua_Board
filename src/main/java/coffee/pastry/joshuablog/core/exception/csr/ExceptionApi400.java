package coffee.pastry.joshuablog.core.exception.csr;

import org.springframework.http.HttpStatus;

import coffee.pastry.joshuablog.dto.ResponseDTO;
import coffee.pastry.joshuablog.dto.ValidDTO;
import lombok.Getter;

@Getter // 유효성 검사 실패, 잘못된 파라메터 요청
public class ExceptionApi400 extends RuntimeException {

     private String key;
     private String value;

     public ExceptionApi400(String key, String value) {
          super(value);
          this.key = key;
          this.value = value;
     }

     public ResponseDTO<?> body() {
          ResponseDTO<ValidDTO> responseDto = new ResponseDTO<>();
          ValidDTO validDTO = new ValidDTO(key, value);
          responseDto.fail(HttpStatus.BAD_REQUEST, "badRequest", validDTO);
          return responseDto;
     }

     public HttpStatus status() {
          return HttpStatus.BAD_REQUEST;
     }
}

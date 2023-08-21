package coffee.pastry.joshuablog.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateUtil { // 댓글 추가 check : yyyy-MM-dd HH:mm

     public static String toStringFormat(LocalDateTime localDateTime) {
          return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
     }
}

package coffee.pastry.joshuablog.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyParseUtil {

     public static String getThumbnail(String html) {
          String thumbnail;
          Document document = Jsoup.parse(html);
          Elements elements = document.select("img");

          if (elements.size() == 0) {
               thumbnail = "/images/python.png";
          } else {
               Element element = elements.get(0);
               thumbnail = element.attr("src");
          }
          return thumbnail;
     }
}

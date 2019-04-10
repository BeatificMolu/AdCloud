package lambda;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/9 12:45
 * @modifier:
 * @version: V1.0
 */
public class test {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.forEach((k, v) -> {
            System.out.println(k + "->" + v);
        });
    }

}

package com.pyp.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/3 23:26
 * @modifier:
 * @version: V1.0
 */
public class CommonUtils {
    public static <K, V> V getorCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String stringConcat(String... args) {
        if (args.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg);
            }
            return sb.toString();
        }
        return null;
    }
}

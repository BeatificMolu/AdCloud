package com.pyp.ad.utils;

import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

    /**
     * 根据特定的日期格式解析成date对象
     *
     * @param dateString
     * @return
     */
    public static Date parseStrinDate(String dateString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyy", Locale.US);
            return DateUtils.addHours(dateFormat.parse(dateString), -8);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

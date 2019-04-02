package com.pyp.ad.utils;

import com.pyp.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 20:25
 * @modifier:
 * @version: V1.0
 */
public class CommonUtils {
    private static String[] parsePatterns={"yyyy-MM-dd","yyyy/MM/dd","yyyy.MM.dd"};
    public static String MD5(String value){
        return DigestUtils.md5Hex(value).toUpperCase();
    }
    public static Date parseStringDate(String dateString) throws AdException {
        try{
           return DateUtils.parseDate(dateString,parsePatterns);
        }catch (Exception ex){
            throw new AdException(ex.getMessage());
        }
    }

}

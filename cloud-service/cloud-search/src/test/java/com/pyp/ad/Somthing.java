package com.pyp.ad;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 18:36
 * @modifier:
 * @version: V1.0
 */
public class Somthing<T> {
    public static <T> List<T> getList(T t) {
        System.out.println(t.toString());
        return Collections.singletonList(t);
    }

    private T id;


    public Somthing(T t) {
        System.out.println("实例化：" + this);
        this.id = t;
    }
    public static <T> Somthing<T> getInstance(T t){
        if(t instanceof Long && t.equals(0)){
            return null;
        }else{
         return new Somthing<T>(t);
        }
    }
    public static void main(String[] args) {
        Map<String, Somthing<Long>> map = new HashMap<>();
        GetIfNeed<Somthing, Long> need = Somthing::getInstance;
        Somthing s1 = new Somthing(1L);
        map.put("s1", s1);
        Somthing somthing=map.computeIfAbsent("s2", k -> need.getNeed(2L));
        Somthing somthing1=map.computeIfAbsent("s1", k -> need.getNeed(2L));
        System.out.println(1);
    }
}

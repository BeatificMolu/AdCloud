package com.pyp.ad;

import java.util.List;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 18:38
 * @modifier:
 * @version: V1.0
 */
@FunctionalInterface
public interface GetIfNeed<T,R> {
    T getNeed(R t);
}

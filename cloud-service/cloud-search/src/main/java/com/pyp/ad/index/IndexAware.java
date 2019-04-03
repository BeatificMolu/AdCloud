package com.pyp.ad.index;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/3 22:54
 * @modifier:
 * @version: V1.0
 */
public interface IndexAware<K, V> {
    /**
     * 根据key获取索引
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 添加索引
     *
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * 更新索引
     *
     * @param key
     * @param value
     */
    void update(K key, V value);

    /**
     * 删除索引
     *
     * @param key
     * @param value
     */
    void delete(K key, V value);
}

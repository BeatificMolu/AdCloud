package com.pyp.ad.repository;

import com.pyp.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/1 23:40
 * @modifier:
 * @version: V1.0
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {
    /**
     * 通过名称查找用户的创意
     *
     * @param name
     * @param userId
     * @return
     */
    Creative findByNameAndUserId(String name, Long userId);
}

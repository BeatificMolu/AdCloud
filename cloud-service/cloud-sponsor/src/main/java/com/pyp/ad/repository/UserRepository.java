package com.pyp.ad.repository;

import com.pyp.ad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/1 23:23
 * @modifier:
 * @version: V1.0
 */
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}

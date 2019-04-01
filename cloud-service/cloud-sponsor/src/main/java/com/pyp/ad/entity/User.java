package com.pyp.ad.entity;


import com.pyp.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/1 22:21
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "a_user")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "user_status", nullable = false)
    private Integer userStatus;
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public User(String username, String token) {
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getStatus();
        this.createTime = new Date();
        this.updateTime = createTime;
    }
}

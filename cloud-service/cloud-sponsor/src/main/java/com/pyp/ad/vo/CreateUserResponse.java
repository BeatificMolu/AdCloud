package com.pyp.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 20:14
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
    private Long userId;
    private String username;
    private String token;
    private Date createTime;
    private Date updateTime;
}

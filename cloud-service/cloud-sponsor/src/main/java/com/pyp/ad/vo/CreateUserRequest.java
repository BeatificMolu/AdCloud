package com.pyp.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 20:12
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String username;

    public boolean validate() {
        return StringUtils.isNotEmpty(username);
    }
}

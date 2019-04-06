package com.pyp.ad.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/7 2:15
 * @modifier:
 * @version: V1.0
 */
@Component
@ConfigurationProperties(prefix = "adconf.mysql")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinLogConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String binlogName;
    private Long position;
}

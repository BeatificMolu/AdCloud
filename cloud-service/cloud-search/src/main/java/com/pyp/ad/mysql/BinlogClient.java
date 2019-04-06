package com.pyp.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.pyp.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/7 2:16
 * @modifier:
 * @version: V1.0
 */
@Slf4j
@Component
public class BinlogClient {
    private BinaryLogClient client;
    private final BinLogConfig config;
    private final AggregationListener listener;

    @Autowired
    public BinlogClient(BinLogConfig config, AggregationListener listener) {
        this.listener = listener;
        this.config = config;
    }

    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                config.setBinlogName(config.getBinlogName());
                config.setPosition(config.getPosition());
            }
            client.registerEventListener(listener);
            try {
                log.info("connecting to mysql start");
                client.connect();
                ;
                log.info("connecting to mysql done");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }).start();
    }

    public void close() {
        try {
            if (client != null) {
                client.disconnect();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

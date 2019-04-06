package com.pyp.ad.index.runner;

import com.pyp.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/7 2:25
 * @modifier:
 * @version: V1.0
 */
@Component
@Slf4j
public class BinlogRunner implements CommandLineRunner {

    private final BinlogClient client;
    @Autowired
    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        client.connect();
    }
}

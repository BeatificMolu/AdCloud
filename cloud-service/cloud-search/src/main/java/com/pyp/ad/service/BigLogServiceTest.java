package com.pyp.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/6 13:56
 * @modifier:
 * @version: V1.0
 */
@Slf4j
public class BigLogServiceTest {
    public static void main(String[] args) throws Exception {
        BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "root");
        client.setBinlogFilename("LAPTOP-BACIR45L-bin.000001");
        client.registerEventListener(BigLogServiceTest::onEvent);
        client.connect();
    }

    private static void onEvent(Event event) {
        event.getHeader();
        EventData data = event.getData();
        if (data instanceof UpdateRowsEventData) {
            System.out.println("update");
            System.out.println(data.toString());
        } else if (data instanceof WriteRowsEventData) {
            System.out.println("write");
            System.out.println(data.toString());
        } else if (data instanceof DeleteRowsEventData) {
            System.out.println("delete");
            System.out.println(data.toString());
        }
    }
}

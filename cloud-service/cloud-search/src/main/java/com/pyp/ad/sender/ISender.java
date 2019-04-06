package com.pyp.ad.sender;

import com.pyp.ad.mysql.dto.MySqlRowData;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/7 1:18
 * @modifier:
 * @version: V1.0
 */
public interface ISender {
    void sender(MySqlRowData data);
}

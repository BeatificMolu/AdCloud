package com.pyp.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 22:37
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    /**
     * 设备编码
     */
    private String deviceCode;
    private String mac;
    private String ip;
    /**
     * 机型编码
     */
    private String model;
    /**
     * 分辨率尺寸
     */
    private String displaySize;
    /**
     * 屏幕尺寸
     */
    private String screenSize;
    /**
     * 设备序列号
     */
    private String serialName;
}

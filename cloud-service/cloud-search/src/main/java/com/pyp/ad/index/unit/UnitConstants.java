package com.pyp.ad.index.unit;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 23:05
 * @modifier:
 * @version: V1.0
 */
public class UnitConstants {
    public static class POSITION_TYPE {
        /**
         * 开屏广告
         */
        public static final int KAIPING = 1;
        /**
         * 贴片广告，好像是在看电影时播放的广告
         */
        public static final int TIEPIAN = 2;
        /**
         * 在中间插播
         */
        public static final int TIEPIAN_MIDDLE = 4;
        /**
         * 在用户暂停时插播
         */
        public static final int TIEPIAN_PAUSE = 8;
        /**
         * 好像时在开头时播放
         */
        public static final int TIEPIAN_POST = 16;
    }
}

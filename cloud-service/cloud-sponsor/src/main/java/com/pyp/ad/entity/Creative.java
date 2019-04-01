package com.pyp.ad.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:创意,其实就是构成广告的各种元素，比如图片，视频等等
 * @author: yy
 * @data: Created in  2019/4/1 23:00
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "a_creative")
@Entity
public class Creative {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * 创意的类型，比如图片，视频
     */
    @Column(name = "type", nullable = false)
    private Integer type;
    /**
     * 物料的类型，比如图片可是 bmp，JPEG格式等等
     */
    @Column(name = "material_type", nullable = false)
    private Integer materialType;
    @Column(name = "height", nullable = false)
    private Integer height;
    @Column(name = "width", nullable = false)
    private Integer width;
    /**
     * 物料大小
     */
    @Column(name = "size", nullable = false)
    private Long size;
    /**
     * 物料持续时长
     */
    @Column(name = "duration", nullable = false)
    private Integer duration;
    /**
     * 审核状态
     */
    @Column(name = "audit_status", nullable = false)
    private Integer auditStatus;
    /**
     * 存储的地址
     */
    @Column(name = "url", nullable = false)
    private String url;
    /**
     * 上传用户的id
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

}

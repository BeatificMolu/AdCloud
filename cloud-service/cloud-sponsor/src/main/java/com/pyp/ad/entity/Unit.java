package com.pyp.ad.entity;

import com.pyp.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/1 22:42
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "a_unit")
@Entity
public class Unit {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "plan_id", nullable = false)
    private Long planId;
    @Column(name = "unit_name", nullable = false)
    private String unitName;
    @Column(name = "unit_status", nullable = false)
    private Integer unitStatus;
    /**
     * 广告位类型（开屏，贴片，中贴...）
     */
    @Column(name = "position_type", nullable = false)
    private Integer positionType;
    /**
     * 预算
     */
    @Column(name = "budget", nullable = false)
    private Long budget;

    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public Unit(Long planId, String unitName, Integer positionType, Long budget) {
        this.budget = budget;
        this.unitName = unitName;
        this.planId = planId;
        this.positionType = positionType;
        this.unitStatus = CommonStatus.VALID.getStatus();
        this.createTime = new Date();
        this.updateTime = createTime;
    }
}

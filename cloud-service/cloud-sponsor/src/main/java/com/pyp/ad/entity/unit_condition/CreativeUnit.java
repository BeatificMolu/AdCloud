package com.pyp.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @description:广告单元和创意的关联关联表 多对多关系
 * @author: yy
 * @data: Created in  2019/4/1 23:10
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@Table(name = "creative_unit")
@Entity
public class CreativeUnit {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creative_id", nullable = false)
    private Long creativeId;
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    public CreativeUnit(Long creativeId, Long unitId) {
        this.creativeId = creativeId;
        this.unitId = unitId;
    }
}

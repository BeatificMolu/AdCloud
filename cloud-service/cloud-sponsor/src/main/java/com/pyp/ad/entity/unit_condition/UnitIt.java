package com.pyp.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @description:推广单元的限制，兴趣的限制
 * @author: yy
 * @data: Created in  2019/4/1 22:54
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unit_it")
@Entity
public class UnitIt {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unit_id", nullable = false)
    private Long unitId;
    /**
     * 用来标识兴趣或者兴趣的名称
     */
    @Column(name = "it_tag", nullable = false)
    private String itTag;

    public UnitIt(Long unitId, String itTag) {
        this.unitId = unitId;
        this.itTag = itTag;
    }
}

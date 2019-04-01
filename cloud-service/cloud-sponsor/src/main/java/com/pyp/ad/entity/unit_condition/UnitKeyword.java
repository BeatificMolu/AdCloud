package com.pyp.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @description:推广单元的限制，关键字限制
 * @author: yy
 * @data: Created in  2019/4/1 22:51
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unit_keyword")
@Entity
public class UnitKeyword {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unit_id", nullable = false)
    private Long unitId;
    @Column(name = "keyword", nullable = false)
    private String keyword;

    public UnitKeyword(Long unitId, String keyword) {
        this.unitId = unitId;
        this.keyword = keyword;
    }
}

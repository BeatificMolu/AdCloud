package com.pyp.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @description:推广单元的限制，地域的限制
 * @author: yy
 * @data: Created in  2019/4/1 22:54
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unit_district")
@Entity
public class UnitDistrict {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Column(name = "province", nullable = false)
    private String province;
    @Column(name = "city", nullable = false)
    private String city;

    public UnitDistrict(Long unitId, String province, String city) {
        this.unitId = unitId;
        this.province = province;
        this.city = city;
    }
}

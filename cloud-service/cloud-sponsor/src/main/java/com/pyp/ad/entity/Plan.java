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
 * @data: Created in  2019/4/1 22:35
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "a_plan")
@Entity
public class Plan {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "plan_name", nullable = false)
    private String planName;
    @Column(name = "plan_status", nullable = false)
    private Integer planStatus;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public Plan(Long userId, String planName, Date startDate, Date endDate) {
        this.userId = userId;
        this.planName = planName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.updateTime = new Date();
        this.planStatus = CommonStatus.VALID.getStatus();
        this.createTime = updateTime;
    }
}

package com.pyp.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/4 22:38
 * @modifier:
 * @version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeObject {
    //创意id
    private Long adId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String adUrl;

    public void update(CreativeObject creative) {
        if (creative.getAdId() != null) {
            this.adId = creative.getAdId();
        }
        if (creative.getName() != null) {
            this.name = creative.getName();
        }
        if (creative.getType() != null) {
            this.type = creative.getType();
        }
        if (creative.getMaterialType() != null) {
            this.materialType = creative.getMaterialType();
        }
        if (creative.getHeight() != null) {
            this.height = creative.getHeight();
        }
        if (creative.getWidth() != null) {
            this.width = creative.getWidth();
        }
        if (creative.getAuditStatus() != null) {
            this.auditStatus = creative.getAuditStatus();
        }
        if (creative.getAdUrl() != null) {
            this.adUrl = creative.getAdUrl();
        }

    }
}

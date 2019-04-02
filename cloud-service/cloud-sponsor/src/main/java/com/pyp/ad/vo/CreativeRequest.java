package com.pyp.ad.vo;

import com.pyp.ad.constant.CommonStatus;
import com.pyp.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/2 22:44
 * @modifier:
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeRequest {
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer width;
    private Integer height;
    private Integer duration;
    private Long size;
    private Long userId;
    private String url;

    public Creative convertToEntity() {
        Creative creative = new Creative();
        creative.setName(name);
        creative.setHeight(height);
        creative.setMaterialType(materialType);
        creative.setWidth(width);
        creative.setSize(size);
        creative.setUserId(userId);
        creative.setUrl(url);
        creative.setType(type);
        creative.setDuration(duration);
        creative.setAuditStatus(CommonStatus.VALID.getStatus());
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());
        return creative;
    }
    public boolean creativeValidate(){
        return userId!=null&& StringUtils.isNotEmpty(name);
    }
}

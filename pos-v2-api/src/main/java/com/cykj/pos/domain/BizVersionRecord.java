package com.cykj.pos.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.cykj.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

/**
 * 版本管理对象 biz_version_record
 *
 * @author weijianbo
 * @date 2021-02-06
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_version_record")
public class BizVersionRecord implements Serializable {

private static final long serialVersionUID=1L;


    /** 主键 */
    @TableId(value = "id")
    private Long id;

    /** 下载地址 */
    @Excel(name = "下载地址")
    private String url;

    /** 版本号 */
    @Excel(name = "版本号")
    private String code;

    /** 版本名称 */
    @Excel(name = "版本名称")
    private String name;

    /** 版本介绍 */
    @Excel(name = "版本介绍")
    private String descs;

    /** 创建者 */
    @Excel(name = "创建者")
    private String createBy;

    /** 上传时间 */
    @Excel(name = "上传时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 备选字段1 */
    @Excel(name = "备选字段1")
    private String var1;

    /** 备选字段2 */
    @Excel(name = "备选字段2")
    private String var2;

    /** 备选字段3 */
    @Excel(name = "备选字段3")
    private String var3;

    /** 备选字段4 */
    @Excel(name = "备选字段4")
    private String var4;

    /** 备选字段5 */
    @Excel(name = "备选字段5")
    private String var5;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}

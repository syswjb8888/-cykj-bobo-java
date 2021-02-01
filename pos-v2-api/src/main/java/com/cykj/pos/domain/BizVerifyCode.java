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
 * 短信验证码对象 biz_verify_code
 *
 * @author ningbingwu
 * @date 2021-01-18
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_verify_code")
public class BizVerifyCode implements Serializable {

private static final long serialVersionUID=1L;


    /** 验证码主键 */
    @TableId(value = "ver_id")
    private Long verId;

    /** 验证码 */
    @Excel(name = "验证码")
    private String verCode;

    /** 发送时间 */
    @Excel(name = "发送时间" , width = 30, dateFormat = "yyyy-MM-dd")
    private String verStarttime;

    /** 到期时间 */
    @Excel(name = "到期时间" , width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String verEndtime;

    /** 验证码状态 */
    @Excel(name = "验证码状态")
    private Integer verStatus;

    /** 手机号 */
    @Excel(name = "手机号")
    private String verMobile;

    /** 验证码用户 */
    @Excel(name = "验证码用户")
    private Long verUserId;

    /** $column.columnComment */
    @Excel(name = "验证码用户")
    private String var1;

    /** $column.columnComment */
    @Excel(name = "验证码用户")
    private String var2;

    /** $column.columnComment */
    @Excel(name = "验证码用户")
    private String var3;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}

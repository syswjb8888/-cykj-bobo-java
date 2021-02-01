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
 * 企业商户报件信息对象 biz_enterprise_info
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("biz_enterprise_info")
public class BizEnterpriseInfo implements Serializable {

private static final long serialVersionUID=1L;


    /** 商户主键 */
    @TableId(value = "ent_id")
    private Long entId;

    /** 商户主键 */
    @Excel(name = "商户主键")
    private Long merchId;

    /** 法人证件照正面 */
    @Excel(name = "法人证件照正面")
    private String legalPersonPositive;

    /** 法人证件照反面 */
    @Excel(name = "法人证件照反面")
    private String legalPersonBack;

    /** 法人姓名 */
    @Excel(name = "法人姓名")
    private String legalPersonName;

    /** 法人身份证件号码 */
    @Excel(name = "法人身份证件号码")
    private String legalPersonIdcard;

    /** 法人证件到期日 */
    @Excel(name = "法人证件到期日")
    private String legalPersonDuedate;

    /** 企业银行账号类型 */
    @Excel(name = "企业银行账号类型")
    private String entAccountType;

    /** 是否法人结算 */
    @Excel(name = "是否法人结算")
    private String legalPersonSettle;

    /** 开户地区 */
    @Excel(name = "开户地区")
    private String entAccountRegion;

    /** 开户账号 */
    @Excel(name = "开户账号")
    private String entAccount;

    /** 开户行 */
    @Excel(name = "开户行")
    private String entAccountBank;

    /** 开户人姓名 */
    @Excel(name = "开户人姓名")
    private String entAccountName;

    /** 银行预留手机号 */
    @Excel(name = "银行预留手机号")
    private String entAccountMobile;

    /** 结算卡照片 */
    @Excel(name = "结算卡照片")
    private String entAccountCardpic;

    /** 营业执照类型 */
    @Excel(name = "营业执照类型")
    private String entLicenseType;

    /** 营业执照编号 */
    @Excel(name = "营业执照编号")
    private String entLicenseCode;

    /** 营业执照到期日 */
    @Excel(name = "营业执照到期日")
    private String entLicenseDuedate;

    /** 营业执照照片正面 */
    @Excel(name = "营业执照照片正面")
    private String entLicensePositive;

    /** 营业执照照片背面 */
    @Excel(name = "营业执照照片背面")
    private String entLicenseBack;

    /** 经营场所一照片 */
    @Excel(name = "经营场所一照片")
    private String entPositionPic1;

    /** 经营场所二照片 */
    @Excel(name = "经营场所二照片")
    private String entPositionPic2;

    /** 经营场所三照片 */
    @Excel(name = "经营场所三照片")
    private String entPositionPic3;

    /** 经营场所四照片 */
    @Excel(name = "经营场所四照片")
    private String entPositionPic4;

    /** $column.columnComment */
    private Long createBy;

    /** $column.columnComment */
    private Date createTime;

    /** $column.columnComment */
    private Long updateBy;

    /** $column.columnComment */
    private Date updateTime;

    /** $column.columnComment */
    @Excel(name = "经营场所四照片")
    private String var1;

    /** $column.columnComment */
    @Excel(name = "经营场所四照片")
    private String var2;

    /** $column.columnComment */
    @Excel(name = "经营场所四照片")
    private String var3;

    /** $column.columnComment */
    @Excel(name = "经营场所四照片")
    private String var4;

    /** $column.columnComment */
    @Excel(name = "经营场所四照片")
    private String var5;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}

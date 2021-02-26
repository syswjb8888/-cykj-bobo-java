package com.cykj.pos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.profit.dto.*;

import java.util.List;

/**
 * 报件商户信息Service接口
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
public interface IBizMerchantService extends IService<BizMerchant> {

    /**
     * 查询列表
     */
    List<BizMerchant> queryList(BizMerchant bizMerchart);

    /**
     * 构建前端显示商户树
     * @param merchants
     * @return
     */
    public List<TreeSelect> buildMerchantTreeSelect(List<BizMerchant> merchants);
    /**
     * 构建商户树
     * @param merchants
     * @return
     */
    public List<BizMerchant> buildMerchantTree(List<BizMerchant> merchants);

    /**
     * 通过商户id获取商户信息
     * @param merId
     * @return
     */
    public BizMerchant getMerchantByMerchId(Long merId);

    /**
     *通过登录用户获取商户信息
     * @return
     */
    public BizMerchant getMerchantByUserId(Long loginUser);

    /**
     * 根据商户id获取用户id
     * @param merchId
     * @return
     */
    public Long getUserIdByMerchId(Long merchId);
    /**
     * 根据商户id获取子商户（含本级商户）
     * @param mechId
     * @return
     */
    public List<BizMerchant> getAllSubNodeAndIncludeSelf(Long mechId);

    /**
     *查询所有子商户（含本级）并可根据相关条件进行查询
     * @param merchId
     * @param merchant
     * @return
     */
    public List<BizMerchant> getAllSubNodeAndIncludeSelf(Long merchId, BizMerchant merchant);

    /**
     * 当月所有新增商户数（含伙伴和普通商户）
     * @param merchId
     * @return
     */
    public Integer getMonthlyNewMerchantCounts(Long merchId);

    /**
     * 当月所有新增普通商户数（即不包含子商户的商户）
     * @param merchId
     * @return
     */
    public Integer getMonthLyNewMerchantExcludeChild(Long merchId);

    /**
     * 产生商户编号
     * @param prefix
     * @return
     */
    public String generateMerchCode(String prefix);

    /**
     *
     * @param prefix
     * @param regionCode
     * @return
     */
    public String generateMerchCode(String prefix,String regionCode);
    /**
     * 获取子商户字典列表
     * @param parentId
     * @return
     */
    public List<MerchantDict> getChildMerchartDictList(Long parentId);

    /**
     * 获取子商户列表
     * @param merchantDTO 父商户用户id
     * @return
     */
    public List<BizMerchant> getChildMerchantList(MerchantDTO merchantDTO,int pageNo,int pageSize);

    /**
     *获取报件成功或未成功的子商户数
     * @param parentId
     * @param verifyStatus 0-报件未成功，1-报件成功
     * @param thisMonth "this"-当月，"last"-上月
     * @param nickName 商户用户名称
     * @return
     */
    public Integer getChildMerchantCounts(Long parentId,String verifyStatus,String thisMonth,String nickName);

    /**
     * 获取伙伴详情
     * @param userId
     * @return
     */
    public  PartnerDTO getPartnerDetail(Long userId);

    /**
     * 获取伙伴列表
     * @param merchantDTO
     * @return
     */
    public List<PartnerDTO> getPagedPartnerList(MerchantDTO merchantDTO);

    /**
     * 小微商户（伙伴）注册
     * @param microMerchantDTO
     */
    public void microMerchantRegister(MicroMerchantDTO microMerchantDTO);

    /**
     * 邀请伙伴扫码注册
     * @param partnerInviteDTO
     */
    public BizStatusContantEnum partnerRegister(PartnerInviteDTO partnerInviteDTO);

    /**
     * 获商户银行卡信息
     * @param bankCardDTO
     * @return
     */
    public BankCardDTO getBankCardInfo(BankCardDTO bankCardDTO);

    /**
     * 银行卡变更
     * @param bankCardDTO
     * @return
     */
    public BankCardDTO alterBankCardInfo(BankCardDTO bankCardDTO);

    /**
     * 添加商户交易记录
     * @param merchTransDTO
     */
    public void addMerchantTransaction(BizMerchTransDTO merchTransDTO);

    /**
     * 撤消商户交易记录
     * @param merchTransDTO
     */
    public void cancelMerchantTransaction(BizCancelTransDTO merchTransDTO);
    /**
     * 退货商户交易记录
     * @param merchTransDTO
     */
    public void returnMerchantTransaction(BizReturnTransDTO merchTransDTO);
    /**
     *  商户下共计商户数量
     * @param merchId
     * @return
     */
    Integer getMonthlyTotalMerchantByMerId(Long merchId);

    /**
     * 当前月新增商户数
     * @param merchId
     * @return
     */
    Integer getMonthlyMyMerchantCounts(Long merchId);

    /**
     * 商户下面所有的子商户和合作伙伴
     * @param merchId
     * @return
     */
    Integer getTtotalMerchAndPartnerCounts(Long merchId);

    /**
     * 查询商户的所有伙伴信息 魏建波
     * @param merchantDTO
     * @return
     */
    List<MerchantDict> selectPagedPartnerList(MerchantDTO merchantDTO);

    /**
     * 本月新增伙伴 魏建波
     * @param merchId
     * @return
     */
    Integer getMonthlyNewPartnerCount(Long merchId);
    /**
     * 本月新增商户 魏建波
     * @param merchId
     * @return
     */
    Integer getMonthlyNewMerchCounts(Long merchId);

    /**
     * 所有商户 魏建波
     * @param merchantId
     * @return
     */
    Integer getTotalNewMerchCounts(Long merchantId);

    /**
     * 根据商户ID查询post激活数量
     * @param merchantId
     * @param month
     * @return
     */
    Integer getActivePostMachineCounts(Long merchantId, String month);

    /**
     * 获得用户下的所有商户  魏建波
     * @param merchId
     * @return
     */
    Integer getTotalMerchantByMerId(Long merchId);

    /**
     * 获得直接父合作伙伴和服务中心  魏建波
     * @param userId
     * @return
     */
    List<BizMerchant> getParentMerchByUserId(Long userId);

    /**
     * 获得首页数据  魏建波
     * @return
     */
    HomePageDataDTO getHomePageDataDTO(Long merchId,Long userId);

    /**
     * 获得本月数据信息
     * @param merchId
     * @param userId
     * @return
     */
    TransAmountDataDTO getTransAmountDataDTO(Long merchId, Long userId);
}

package com.cykj.pos.service;

import com.cykj.pos.domain.BizMerchTransactions;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商户交易记录Service接口
 *
 * @author ningbingwu
 * @date 2021-01-29
 */
public interface IBizMerchTransactionsService extends IService<BizMerchTransactions> {

    /**
     * 查询列表
     */
    List<BizMerchTransactions> queryList(BizMerchTransactions bizMerchTransactions);

    /**
     * 检索商户交易记录-所有的
     * @param merchId
     * @return
     */
    public List<BizMerchTransactions> getMonthlyTransRecordsByMerchId(Long merchId);

    /**
     * 检索商户交易记录-所有的
     * @param caculatePeriod 计算周期
     * @param merchId
     * @return
     */
    public List<BizMerchTransactions> getMonthlyTransRecordsByMerchId(String caculatePeriod,Long merchId);
    /**
     * 检索商户交易记录-分页的
     * @param merchId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<BizMerchTransactions> getMonthlyTransRecordsByMerchId(Long merchId, int pageNo, int pageSize);

    /**
     * 检索商户交易记录-分页的
     * @param caculatePeriod 计算周期
     * @param merchId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<BizMerchTransactions> getMonthlyTransRecordsByMerchId(String caculatePeriod,Long merchId, int pageNo, int pageSize);

    /**
     * 获取商户月交易金额
     * @param caculatePeriod 计算周期
     * @param merchId
     * @return
     */
    public BigDecimal getMonthlyTransAmountByMerchId(String caculatePeriod, Long merchId);
    /**
     * 获取商户月交易金额
     * @param merchId
     * @return
     */
    public BigDecimal getMonthlyTransAmountByMerchId(Long merchId);
    /**
     *商户本月或上月交易额
     * @param merchId
     * @param month 本月或上月
     * @return
     */
    public BigDecimal getMonthlyTransAmountByMerchId(Long merchId,String month);

    /**
     * 小薇商户 交易额个人业绩
     * @param merchId
     * @return
     */
    BigDecimal getMonthlyMerchantTransAmountByMerchId(Long merchId);

}

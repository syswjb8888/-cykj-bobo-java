package com.cykj.pos.service.impl;

import com.cykj.common.annotation.DataSource;
import com.cykj.common.enums.DataSourceType;
import com.cykj.pos.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizMerchTransactionsMapper;
import com.cykj.pos.domain.BizMerchTransactions;
import com.cykj.pos.service.IBizMerchTransactionsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商户交易记录Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-29
 */
@Service
public class BizMerchTransactionsServiceImpl extends ServiceImpl<BizMerchTransactionsMapper, BizMerchTransactions> implements IBizMerchTransactionsService {

    private static final String DATE_FORMATTER = "yyyy-MM";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BizMerchTransactions> queryList(BizMerchTransactions bizMerchTransactions) {
        LambdaQueryWrapper<BizMerchTransactions> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(bizMerchTransactions.getInnerMerchCode())){
            lqw.eq(BizMerchTransactions::getInnerMerchCode ,bizMerchTransactions.getInnerMerchCode());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getMerchFlagCode())){
            lqw.eq(BizMerchTransactions::getMerchFlagCode ,bizMerchTransactions.getMerchFlagCode());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getOrderId())){
            lqw.eq(BizMerchTransactions::getOrderId ,bizMerchTransactions.getOrderId());
        }
        if (bizMerchTransactions.getTransAmount() != null){
            lqw.eq(BizMerchTransactions::getTransAmount ,bizMerchTransactions.getTransAmount());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getIdTxn())){
            lqw.eq(BizMerchTransactions::getIdTxn ,bizMerchTransactions.getIdTxn());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getProductCode())){
            lqw.eq(BizMerchTransactions::getProductCode ,bizMerchTransactions.getProductCode());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getBankAuthCode())){
            lqw.eq(BizMerchTransactions::getBankAuthCode ,bizMerchTransactions.getBankAuthCode());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getDiscountRateFlag())){
            lqw.eq(BizMerchTransactions::getDiscountRateFlag ,bizMerchTransactions.getDiscountRateFlag());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getStatus())){
            lqw.eq(BizMerchTransactions::getStatus ,bizMerchTransactions.getStatus());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getErrorCode())){
            lqw.eq(BizMerchTransactions::getErrorCode ,bizMerchTransactions.getErrorCode());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getErrorMsg())){
            lqw.eq(BizMerchTransactions::getErrorMsg ,bizMerchTransactions.getErrorMsg());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getTransDate())){
            lqw.eq(BizMerchTransactions::getTransDate ,bizMerchTransactions.getTransDate());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getServiceEntryMode())){
            lqw.eq(BizMerchTransactions::getServiceEntryMode ,bizMerchTransactions.getServiceEntryMode());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getCardType())){
            lqw.eq(BizMerchTransactions::getCardType ,bizMerchTransactions.getCardType());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getPosCode())){
            lqw.eq(BizMerchTransactions::getPosCode ,bizMerchTransactions.getPosCode());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getDeviceType())){
            lqw.eq(BizMerchTransactions::getDeviceType ,bizMerchTransactions.getDeviceType());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getUserName())){
            lqw.like(BizMerchTransactions::getUserName ,bizMerchTransactions.getUserName());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getPhoneNo())){
            lqw.eq(BizMerchTransactions::getPhoneNo ,bizMerchTransactions.getPhoneNo());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getStoreCachierFlag())){
            lqw.eq(BizMerchTransactions::getStoreCachierFlag ,bizMerchTransactions.getStoreCachierFlag());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getReceiptType())){
            lqw.eq(BizMerchTransactions::getReceiptType ,bizMerchTransactions.getReceiptType());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getS0PackageFlag())){
            lqw.eq(BizMerchTransactions::getS0PackageFlag ,bizMerchTransactions.getS0PackageFlag());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getSecondOrgId())){
            lqw.eq(BizMerchTransactions::getSecondOrgId ,bizMerchTransactions.getSecondOrgId());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getDirectlyOrgId())){
            lqw.eq(BizMerchTransactions::getDirectlyOrgId ,bizMerchTransactions.getDirectlyOrgId());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getSpecialMode())){
            lqw.eq(BizMerchTransactions::getSpecialMode ,bizMerchTransactions.getSpecialMode());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getEsimFlag())){
            lqw.eq(BizMerchTransactions::getEsimFlag ,bizMerchTransactions.getEsimFlag());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getIdCardNo())){
            lqw.eq(BizMerchTransactions::getIdCardNo ,bizMerchTransactions.getIdCardNo());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getAuthMerchId())){
            lqw.eq(BizMerchTransactions::getAuthMerchId ,bizMerchTransactions.getAuthMerchId());
        }
        if (StringUtils.isNotBlank(bizMerchTransactions.getAuthTerminalId())){
            lqw.eq(BizMerchTransactions::getAuthTerminalId ,bizMerchTransactions.getAuthTerminalId());
        }
        return this.list(lqw);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMerchTransactions> getMonthlyTransRecordsByMerchId(Long merchId){
        String formatedDate = DateUtils.getCaculateYearAndMonth("last",DATE_FORMATTER);
        String sql = "select * from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where FIND_IN_SET(p.merch_id,findMerchSubNode(?))) and t.trans_date like  CONCAT(?,'%')";
        return jdbcTemplate.query(sql,new Object[]{merchId,formatedDate},
                new BeanPropertyRowMapper<>(BizMerchTransactions.class));
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMerchTransactions> getMonthlyTransRecordsByMerchId(String caculatePeriod,Long merchId){
        String sql = "select * from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where FIND_IN_SET(p.merch_id,findMerchSubNode(?))) and t.trans_date like  CONCAT(?,'%')";
        return jdbcTemplate.query(sql,new Object[]{merchId,caculatePeriod},
                new BeanPropertyRowMapper<>(BizMerchTransactions.class));
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMerchTransactions> getMonthlyTransRecordsByMerchId(String caculatePeriod,Long merchId, int pageNo, int pageSize){
        int start = (pageNo-1)*pageSize;
        String sql = "select * from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where FIND_IN_SET(p.merch_id,findMerchSubNode(?))) and t.trans_date like  CONCAT(?,'%') LIMIT ?,?";
        return jdbcTemplate.query(sql,new Object[]{merchId,caculatePeriod,start,pageSize},
                new BeanPropertyRowMapper<>(BizMerchTransactions.class));
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMerchTransactions> getMonthlyTransRecordsByMerchId(Long merchId, int pageNo, int pageSize){
        int start = (pageNo-1)*pageSize;
        String formatedDate = DateUtils.getCaculateYearAndMonth("last",DATE_FORMATTER);
        String sql = "select * from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where FIND_IN_SET(p.merch_id,findMerchSubNode(?))) and t.trans_date like  CONCAT(?,'%') LIMIT ?,?";
        return jdbcTemplate.query(sql,new Object[]{merchId,formatedDate,start,pageSize},
                new BeanPropertyRowMapper<>(BizMerchTransactions.class));
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public BigDecimal getMonthlyTransAmountByMerchId(String caculatePeriod, Long merchId){
        String sql = "select sum(trans_amount) from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where FIND_IN_SET(p.merch_id,findMerchSubNode(?))) and t.trans_date like CONCAT(?,'%')";
        BigDecimal transAmount = jdbcTemplate.queryForObject(sql,new Object[]{merchId,caculatePeriod},BigDecimal.class);
        return transAmount == null?BigDecimal.ZERO:transAmount;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public BigDecimal getMonthlyTransAmountByMerchId(Long merchId){ // 获取当前月份
        String formatedDate = DateUtils.getCaculateYearAndMonth("",DATE_FORMATTER);
        String sql = "select sum(trans_amount) from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where p.pos_activate_status=2 and FIND_IN_SET(p.merch_id,findMerchSubNode(?))) and t.error_msg='交易成功' and t.trans_date like CONCAT(?,'%')";
        BigDecimal transAmount = jdbcTemplate.queryForObject(sql,new Object[]{merchId,formatedDate},BigDecimal.class);
        return transAmount == null?BigDecimal.ZERO:transAmount;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public BigDecimal getMonthlyTransAmountByMerchId(Long merchId,String month){ // 获取某个月份的销售总金额
        String formatedDate = DateUtils.getCaculateYearAndMonth(month,DATE_FORMATTER);
        String sql = "select sum(trans_amount) from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where p.pos_activate_status=2 and FIND_IN_SET(p.merch_id,findMerchSubNode(?))) and t.error_msg='交易成功' and t.trans_date like CONCAT(?,'%')";
        BigDecimal transAmount = jdbcTemplate.queryForObject(sql,new Object[]{merchId,formatedDate},BigDecimal.class);
        return transAmount == null?BigDecimal.ZERO:transAmount;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public BigDecimal getMonthlyMerchantTransAmountByMerchId(Long merchId) {
        String formatedDate = DateUtils.getCaculateYearAndMonth("this",DATE_FORMATTER);
        String sql = "select sum(trans_amount) from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where p.merch_id=?) and t.error_msg='交易成功' and t.trans_date like CONCAT(?,'%')";
        BigDecimal transAmount = jdbcTemplate.queryForObject(sql,new Object[]{merchId,formatedDate},BigDecimal.class);
        return transAmount == null?BigDecimal.ZERO:transAmount;
    }

    @Override
    public BigDecimal getTotalTransAmountByMerchId(Long merchantId) {
        /*
        String formatedDate = DateUtils.getCaculateYearAndMonth("",DATE_FORMATTER);
        String sql = "select sum(trans_amount) from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where FIND_IN_SET(p.merch_id,findMerchSubNode(?))) and t.trans_date like CONCAT(?,'%')";
        BigDecimal transAmount = jdbcTemplate.queryForObject(sql,new Object[]{merchId,formatedDate},BigDecimal.class);
        return transAmount == null?BigDecimal.ZERO:transAmount;
         */
        String sql = "select sum(trans_amount) from biz_merch_transactions t where t.pos_code in (select p.pos_code from biz_pos_machine p " +
                "where p.merch_id=?)";
        BigDecimal transAmount = jdbcTemplate.queryForObject(sql,new Object[]{merchantId},BigDecimal.class);
        return transAmount == null?BigDecimal.ZERO:transAmount;
    }

}

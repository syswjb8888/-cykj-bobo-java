package com.cykj.pos.service.impl;

import com.cykj.common.annotation.DataSource;
import com.cykj.common.enums.DataSourceType;
import com.cykj.common.utils.SecurityUtils;
import com.cykj.pos.profit.dto.WalletDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizWalletMapper;
import com.cykj.pos.domain.BizWallet;
import com.cykj.pos.service.IBizWalletService;

import java.util.List;
import java.util.Map;

/**
 * 我的钱包Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-27
 */
@Service
public class BizWalletServiceImpl extends ServiceImpl<BizWalletMapper, BizWallet> implements IBizWalletService {

    @Override
    public List<BizWallet> queryList(BizWallet bizWallet) {
        LambdaQueryWrapper<BizWallet> lqw = Wrappers.lambdaQuery();
        if (bizWallet.getUserId() != null){
            lqw.eq(BizWallet::getUserId ,bizWallet.getUserId());
        }
        if (bizWallet.getProfitAmount() != null){
            lqw.eq(BizWallet::getProfitAmount ,bizWallet.getProfitAmount());
        }
        if (bizWallet.getProfitTaxAmount() != null){
            lqw.eq(BizWallet::getProfitTaxAmount ,bizWallet.getProfitTaxAmount());
        }
        if (bizWallet.getRewardAmount() != null){
            lqw.eq(BizWallet::getRewardAmount ,bizWallet.getRewardAmount());
        }
        if (bizWallet.getRewordTaxAmount() != null){
            lqw.eq(BizWallet::getRewordTaxAmount ,bizWallet.getRewordTaxAmount());
        }
        if (bizWallet.getWalletAmount() != null){
            lqw.eq(BizWallet::getWalletAmount ,bizWallet.getWalletAmount());
        }
        if (StringUtils.isNotBlank(bizWallet.getCashoutStatus())){
            lqw.eq(BizWallet::getCashoutStatus ,bizWallet.getCashoutStatus());
        }
        if (StringUtils.isNotBlank(bizWallet.getPayPassword())){
            lqw.eq(BizWallet::getPayPassword ,bizWallet.getPayPassword());
        }
        if (StringUtils.isNotBlank(bizWallet.getVar1())){
            lqw.eq(BizWallet::getVar1 ,bizWallet.getVar1());
        }
        if (StringUtils.isNotBlank(bizWallet.getVar2())){
            lqw.eq(BizWallet::getVar2 ,bizWallet.getVar2());
        }
        if (StringUtils.isNotBlank(bizWallet.getVar3())){
            lqw.eq(BizWallet::getVar3 ,bizWallet.getVar3());
        }
        if (StringUtils.isNotBlank(bizWallet.getVar4())){
            lqw.eq(BizWallet::getVar4 ,bizWallet.getVar4());
        }
        if (StringUtils.isNotBlank(bizWallet.getVar5())){
            lqw.eq(BizWallet::getVar5 ,bizWallet.getVar5());
        }
        return this.list(lqw);
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public BizWallet getMyWallet(WalletDTO walletDTO){
        LambdaQueryWrapper<BizWallet> lqw = Wrappers.lambdaQuery();
        if (walletDTO.getUserId() != null){
            lqw.eq(BizWallet::getUserId ,walletDTO.getUserId());
        }
        return this.getOne(lqw);
    }
    @Override
    public void setPayPassword(WalletDTO walletDTO){
       BizWallet wallet = this.getMyWallet(walletDTO);
       //TODO：这里还需要加一层密码密文解密功能，即前端传输过来的是密码密文（加密算法协商解决），解密后保存到本地数据库
       wallet.setPayPassword(SecurityUtils.encryptPassword(walletDTO.getPayPassword()));
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public BizWallet getMyWalletByUserId(Long userId) {
        LambdaQueryWrapper<BizWallet> wallQuery = Wrappers.lambdaQuery();
        wallQuery.eq(BizWallet::getUserId ,userId);
        BizWallet wallet =  this.getOne(wallQuery);
        return wallet;
    }
}

package com.cykj.pos.service;

import com.cykj.pos.domain.BizWallet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cykj.pos.profit.dto.WalletDTO;

import java.util.List;

/**
 * 我的钱包Service接口
 *
 * @author ningbingwu
 * @date 2021-01-27
 */
public interface IBizWalletService extends IService<BizWallet> {

    /**
     * 查询列表
     */
    List<BizWallet> queryList(BizWallet bizWallet);

    /**
     * 获取我的钱包账户信息
     * @param walletDTO
     * @return
     */
    public BizWallet getMyWallet(WalletDTO walletDTO);

    public void setPayPassword(WalletDTO walletDTO);
}

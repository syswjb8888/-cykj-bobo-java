package com.cykj.pos.service;

import com.cykj.pos.domain.BizMerchBill;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 账单信息Service接口
 *
 * @author weijianbo
 * @date 2021-02-06
 */
public interface IBizMerchBillService extends IService<BizMerchBill> {

    /**
     * 查询列表
     */
    List<BizMerchBill> queryList(BizMerchBill bizMerchBill);
}

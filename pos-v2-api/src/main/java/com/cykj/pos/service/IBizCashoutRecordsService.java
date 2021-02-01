package com.cykj.pos.service;

import com.cykj.pos.domain.BizCashoutRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 提现记录Service接口
 *
 * @author ningbingwu
 * @date 2021-01-27
 */
public interface IBizCashoutRecordsService extends IService<BizCashoutRecords> {

    /**
     * 查询列表
     */
    List<BizCashoutRecords> queryList(BizCashoutRecords bizCashoutRecords);
}

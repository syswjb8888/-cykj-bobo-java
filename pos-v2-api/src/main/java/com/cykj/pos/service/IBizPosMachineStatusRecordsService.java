package com.cykj.pos.service;

import com.cykj.pos.domain.BizPosMachineStatusRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 终端设备状态记录Service接口
 *
 * @author weijianbo
 * @date 2021-02-02
 */
public interface IBizPosMachineStatusRecordsService extends IService<BizPosMachineStatusRecords> {

    /**
     * 查询列表
     */
    List<BizPosMachineStatusRecords> queryList(BizPosMachineStatusRecords bizPosMachineStatusRecords);

    /**
     * 根据商户编号查询设备记录信息 魏建波
     * @param merchantId
     * @return
     */
    BizPosMachineStatusRecords getPosMachineStatusRecordsByMerchantId(String merchantId);
}

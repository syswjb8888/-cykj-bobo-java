package com.cykj.pos.service;

import com.cykj.pos.domain.BizAllocAdjRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cykj.pos.profit.dto.OperationRecordsDTO;
import com.cykj.pos.profit.dto.PosTerminalDTO;

import java.util.List;
import java.util.Map;

/**
 * 划拔回调记录Service接口
 *
 * @author ningbingwu
 * @date 2021-01-15
 */
public interface IBizAllocAdjRecordsService extends IService<BizAllocAdjRecords> {

    /**
     * 查询列表
     */
    List<BizAllocAdjRecords> queryList(BizAllocAdjRecords bizAllocAdjRecords);

    /**
     * 查询当年划拔回调记录概要信息（操作人id，操作人名称，操作时间，机器台数）
     * @param terminalDTO
     * @return
     */
    public List<OperationRecordsDTO> getCurrentYearBriefOperationRecordsList(PosTerminalDTO terminalDTO);
    /**
     * 条件查询划拔回调记录（带分页）
     * @param terminalDTO
     * @return
     */
    public List<BizAllocAdjRecords> getPageOperationRecordsList(PosTerminalDTO terminalDTO);

    /**
     * 条件查询划拔回调记录总数
     * @param terminalDTO
     * @return
     */
    public Integer geOperationRecordsCounts(PosTerminalDTO terminalDTO);
}

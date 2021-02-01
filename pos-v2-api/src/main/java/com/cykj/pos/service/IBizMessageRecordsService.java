package com.cykj.pos.service;

import com.cykj.pos.domain.BizMessageRecords;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 离用户消息Service接口
 *
 * @author ningbingwu
 * @date 2021-01-18
 */
public interface IBizMessageRecordsService extends IService<BizMessageRecords> {

    /**
     * 查询列表
     */
    List<BizMessageRecords> queryList(BizMessageRecords bizMessageRecords);
}

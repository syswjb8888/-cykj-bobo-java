package com.cykj.pos.service;

import com.cykj.pos.domain.BizVersionRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 版本管理Service接口
 *
 * @author weijianbo
 * @date 2021-02-06
 */
public interface IBizVersionRecordService extends IService<BizVersionRecord> {

    /**
     * 查询列表
     */
    List<BizVersionRecord> queryList(BizVersionRecord bizVersionRecord);

    /**
     * 获得最新的版本信息
     * @return
     */
    BizVersionRecord getNewsVersionInfo();
}

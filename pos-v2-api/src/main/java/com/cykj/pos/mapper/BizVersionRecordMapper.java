package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizVersionRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 版本管理Mapper接口
 *
 * @author weijianbo
 * @date 2021-02-06
 */
public interface BizVersionRecordMapper extends BaseMapper<BizVersionRecord> {
    /**
     * 获得最新的版本信息
     * @return
     */
    BizVersionRecord getNewsVersionInfo();
}

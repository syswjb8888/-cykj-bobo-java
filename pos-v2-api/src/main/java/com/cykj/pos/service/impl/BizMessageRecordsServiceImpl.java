package com.cykj.pos.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizMessageRecordsMapper;
import com.cykj.pos.domain.BizMessageRecords;
import com.cykj.pos.service.IBizMessageRecordsService;

import java.util.List;

/**
 * 离用户消息Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-21
 */
@Service
public class BizMessageRecordsServiceImpl extends ServiceImpl<BizMessageRecordsMapper, BizMessageRecords> implements IBizMessageRecordsService {

    @Override
    public List<BizMessageRecords> queryList(BizMessageRecords bizMessageRecords) {
        LambdaQueryWrapper<BizMessageRecords> lqw = Wrappers.lambdaQuery();
        if (bizMessageRecords.getMsgUserId() != null){
            lqw.eq(BizMessageRecords::getMsgUserId ,bizMessageRecords.getMsgUserId());
        }
        if (StringUtils.isNotBlank(bizMessageRecords.getMsgContent())){
            lqw.eq(BizMessageRecords::getMsgContent ,bizMessageRecords.getMsgContent());
        }
        if (bizMessageRecords.getMsgStatus() != null){
            lqw.eq(BizMessageRecords::getMsgStatus ,bizMessageRecords.getMsgStatus());
        }
        return this.list(lqw);
    }
}

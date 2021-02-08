package com.cykj.pos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizVersionRecordMapper;
import com.cykj.pos.domain.BizVersionRecord;
import com.cykj.pos.service.IBizVersionRecordService;

import java.util.List;
import java.util.Map;

/**
 * 版本管理Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-06
 */
@Service
public class BizVersionRecordServiceImpl extends ServiceImpl<BizVersionRecordMapper, BizVersionRecord> implements IBizVersionRecordService {

    @Autowired
    BizVersionRecordMapper bizVersionRecordMapper;
    @Override
    public List<BizVersionRecord> queryList(BizVersionRecord bizVersionRecord) {
        LambdaQueryWrapper<BizVersionRecord> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(bizVersionRecord.getUrl())){
            lqw.eq(BizVersionRecord::getUrl ,bizVersionRecord.getUrl());
        }
        if (StringUtils.isNotBlank(bizVersionRecord.getCode())){
            lqw.eq(BizVersionRecord::getCode ,bizVersionRecord.getCode());
        }
        if (StringUtils.isNotBlank(bizVersionRecord.getName())){
            lqw.like(BizVersionRecord::getName ,bizVersionRecord.getName());
        }
        if (StringUtils.isNotBlank(bizVersionRecord.getDescs())){
            lqw.eq(BizVersionRecord::getDescs ,bizVersionRecord.getDescs());
        }
        if (StringUtils.isNotBlank(bizVersionRecord.getCreateBy())){
            lqw.eq(BizVersionRecord::getCreateBy ,bizVersionRecord.getCreateBy());
        }
        if (bizVersionRecord.getCreateTime() != null){
            lqw.eq(BizVersionRecord::getCreateTime ,bizVersionRecord.getCreateTime());
        }
        if (StringUtils.isNotBlank(bizVersionRecord.getVar1())){
            lqw.eq(BizVersionRecord::getVar1 ,bizVersionRecord.getVar1());
        }
        if (StringUtils.isNotBlank(bizVersionRecord.getVar2())){
            lqw.eq(BizVersionRecord::getVar2 ,bizVersionRecord.getVar2());
        }
        if (StringUtils.isNotBlank(bizVersionRecord.getVar3())){
            lqw.eq(BizVersionRecord::getVar3 ,bizVersionRecord.getVar3());
        }
        if (StringUtils.isNotBlank(bizVersionRecord.getVar4())){
            lqw.eq(BizVersionRecord::getVar4 ,bizVersionRecord.getVar4());
        }
        if (StringUtils.isNotBlank(bizVersionRecord.getVar5())){
            lqw.eq(BizVersionRecord::getVar5 ,bizVersionRecord.getVar5());
        }
        return this.list(lqw);
    }

    @Override
    public BizVersionRecord getNewsVersionInfo() {
        return bizVersionRecordMapper.getNewsVersionInfo();
    }
}

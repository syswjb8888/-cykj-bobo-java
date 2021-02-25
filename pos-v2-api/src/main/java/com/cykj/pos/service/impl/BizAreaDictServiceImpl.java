package com.cykj.pos.service.impl;

import com.cykj.pos.profit.dto.SysDictTypeDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizAreaDictMapper;
import com.cykj.pos.domain.BizAreaDict;
import com.cykj.pos.service.IBizAreaDictService;

import java.util.List;
import java.util.Map;

/**
 * 地区管理Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-23
 */
@Service
public class BizAreaDictServiceImpl extends ServiceImpl<BizAreaDictMapper, BizAreaDict> implements IBizAreaDictService {

    @Override
    public List<BizAreaDict> queryList(BizAreaDict bizAreaDict) {
        LambdaQueryWrapper<BizAreaDict> lqw = Wrappers.lambdaQuery();
        if (bizAreaDict.getParentId() != null){
            lqw.eq(BizAreaDict::getParentId ,bizAreaDict.getParentId());
        }
        if (StringUtils.isNotBlank(bizAreaDict.getCname())){
            lqw.like(BizAreaDict::getCname ,bizAreaDict.getCname());
        }
        if (bizAreaDict.getCtype() != null){
            lqw.eq(BizAreaDict::getCtype ,bizAreaDict.getCtype());
        }
        return this.list(lqw);
    }

    @Override
    public List<BizAreaDict> getAreaDictDataListByPid(SysDictTypeDTO dictTypeDTO) {
        LambdaQueryWrapper<BizAreaDict> lqw = Wrappers.lambdaQuery();
        if(dictTypeDTO.getDictType()!=null && !"".equals(dictTypeDTO.getDictType())){
            lqw.eq(BizAreaDict::getCtype,dictTypeDTO.getDictType());
        }
        if(dictTypeDTO.getDictLevelCode()!=null && !"".equals(dictTypeDTO.getDictLevelCode())){
            lqw.eq(BizAreaDict::getParentId ,dictTypeDTO.getDictLevelCode());
        }
        lqw.orderByAsc(BizAreaDict::getId);
        return this.list(lqw);
    }
}

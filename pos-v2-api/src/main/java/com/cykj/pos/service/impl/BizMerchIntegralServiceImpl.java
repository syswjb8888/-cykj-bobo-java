package com.cykj.pos.service.impl;

import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.dto.IntegralDTO;
import com.cykj.pos.domain.dto.IntegralDetailDTO;
import com.cykj.pos.mapper.BizMerchantMapper;
import com.cykj.pos.service.IBizMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizMerchIntegralMapper;
import com.cykj.pos.domain.BizMerchIntegral;
import com.cykj.pos.service.IBizMerchIntegralService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分管理Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-19
 */
@Service
public class BizMerchIntegralServiceImpl extends ServiceImpl<BizMerchIntegralMapper, BizMerchIntegral> implements IBizMerchIntegralService {
    @Autowired
    IBizMerchantService merchantService;
    @Autowired
    BizMerchIntegralMapper merchIntegralMapper;
    @Override
    public List<BizMerchIntegral> queryList(BizMerchIntegral bizMerchIntegral) {
        LambdaQueryWrapper<BizMerchIntegral> lqw = Wrappers.lambdaQuery();
        if (bizMerchIntegral.getMerchId() != null){
            lqw.eq(BizMerchIntegral::getMerchId ,bizMerchIntegral.getMerchId());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getPosCode())){
            lqw.eq(BizMerchIntegral::getPosCode ,bizMerchIntegral.getPosCode());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getIntegralType())){
            lqw.eq(BizMerchIntegral::getIntegralType ,bizMerchIntegral.getIntegralType());
        }
        if (bizMerchIntegral.getValue() != null){
            lqw.eq(BizMerchIntegral::getValue ,bizMerchIntegral.getValue());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getTransType())){
            lqw.eq(BizMerchIntegral::getTransType ,bizMerchIntegral.getTransType());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar1())){
            lqw.eq(BizMerchIntegral::getVar1 ,bizMerchIntegral.getVar1());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar2())){
            lqw.eq(BizMerchIntegral::getVar2 ,bizMerchIntegral.getVar2());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar3())){
            lqw.eq(BizMerchIntegral::getVar3 ,bizMerchIntegral.getVar3());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar4())){
            lqw.eq(BizMerchIntegral::getVar4 ,bizMerchIntegral.getVar4());
        }
        if (StringUtils.isNotBlank(bizMerchIntegral.getVar5())){
            lqw.eq(BizMerchIntegral::getVar5 ,bizMerchIntegral.getVar5());
        }
        return this.list(lqw);
    }

    @Override
    public List getIntegralList(IntegralDTO integralDTO) {
        List integralDetailMapList = new ArrayList<>();
        Integer pageNo = integralDTO.getPageNo();
        Integer pageSize = integralDTO.getPageSize();
        if(pageNo!=null && pageNo!=-1 && pageSize!=null && pageSize!=-1){
            Integer start = (pageNo-1)*pageSize;
            integralDTO.setStart(start);
        }else{
            integralDTO.setStart(-1);
        }
        // 先按照月份  进行分组  分页
        List<String> monthList = merchIntegralMapper.selectMonthListByUserIdAndTranType(integralDTO);
        for(String month:monthList){
            Map<String,Object> integralDetailMap = new HashMap<>();
            integralDTO.setMonthly(month);
            List<IntegralDetailDTO> integralDetailDTOList = merchIntegralMapper.selectIntegralList(integralDTO);
            // 设置第几个月
            integralDetailMap.put("monthly",month);
            // 设置内容值
            integralDetailMap.put("detail",integralDetailDTOList);
            integralDetailMapList.add(integralDetailMap);
        }
        return integralDetailMapList;
    }
}

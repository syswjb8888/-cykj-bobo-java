package com.cykj.pos.service.impl;

import com.cykj.common.annotation.DataSource;
import com.cykj.common.enums.DataSourceType;
import com.cykj.pos.profit.dto.OperationRecordsDTO;
import com.cykj.pos.profit.dto.PosTerminalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizAllocAdjRecordsMapper;
import com.cykj.pos.domain.BizAllocAdjRecords;
import com.cykj.pos.service.IBizAllocAdjRecordsService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 划拔回调记录Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-15
 */
@Service
public class BizAllocAdjRecordsServiceImpl extends ServiceImpl<BizAllocAdjRecordsMapper, BizAllocAdjRecords> implements IBizAllocAdjRecordsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BizAllocAdjRecordsMapper allocAdjRecordsMapper;

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizAllocAdjRecords> queryList(BizAllocAdjRecords bizAllocAdjRecords) {
        LambdaQueryWrapper<BizAllocAdjRecords> lqw = Wrappers.lambdaQuery();
        if (bizAllocAdjRecords.getPosId() != null){
            lqw.eq(BizAllocAdjRecords::getPosId ,bizAllocAdjRecords.getPosId());
        }
        if (StringUtils.isNotBlank(bizAllocAdjRecords.getPosCode())){
            lqw.eq(BizAllocAdjRecords::getPosCode ,bizAllocAdjRecords.getPosCode());
        }
        if (bizAllocAdjRecords.getOldUserId() != null){
            lqw.eq(BizAllocAdjRecords::getOldUserId,bizAllocAdjRecords.getOldUserId());
        }
        if (bizAllocAdjRecords.getNewUserId() != null){
            lqw.eq(BizAllocAdjRecords::getNewUserId,bizAllocAdjRecords.getNewUserId());
        }
        if (bizAllocAdjRecords.getOperator() != null){
            lqw.eq(BizAllocAdjRecords::getOperator ,bizAllocAdjRecords.getOperator());
        }
        if (bizAllocAdjRecords.getOperateTime() != null){
            lqw.eq(BizAllocAdjRecords::getOperateTime ,bizAllocAdjRecords.getOperateTime());
        }
        if (bizAllocAdjRecords.getOperateType() != null){
            lqw.eq(BizAllocAdjRecords::getOperateType ,bizAllocAdjRecords.getOperateType());
        }
        if (StringUtils.isNotBlank(bizAllocAdjRecords.getVar1())){
            lqw.eq(BizAllocAdjRecords::getVar1 ,bizAllocAdjRecords.getVar1());
        }
        if (StringUtils.isNotBlank(bizAllocAdjRecords.getVar2())){
            lqw.eq(BizAllocAdjRecords::getVar2 ,bizAllocAdjRecords.getVar2());
        }
        if (StringUtils.isNotBlank(bizAllocAdjRecords.getVar3())){
            lqw.eq(BizAllocAdjRecords::getVar3 ,bizAllocAdjRecords.getVar3());
        }
        if (StringUtils.isNotBlank(bizAllocAdjRecords.getVar4())){
            lqw.eq(BizAllocAdjRecords::getVar4 ,bizAllocAdjRecords.getVar4());
        }
        if (StringUtils.isNotBlank(bizAllocAdjRecords.getVar5())){
            lqw.eq(BizAllocAdjRecords::getVar5 ,bizAllocAdjRecords.getVar5());
        }
        return this.list(lqw);
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<OperationRecordsDTO> getCurrentYearBriefOperationRecordsList(PosTerminalDTO terminalDTO){
        Long userId = terminalDTO.getUserId();
        Integer operType = terminalDTO.getOperType();
        Integer pageNo = terminalDTO.getPageNo();
        Integer pageSize = terminalDTO.getPageSize();

        String operateTime = terminalDTO.getOperateTime();
        List<Object> paramList = new ArrayList<>(5);
        paramList.add(operType);
        paramList.add(userId);
        StringBuilder sqlBuilder = new StringBuilder("select count(1) as posCounts,rec.operator,o.nick_name as operatorName," +
                "u.nick_name as allocName,s.nick_name as adjustName," +
                "GROUP_CONCAT(rec.pos_code SEPARATOR ',') as posCodes," +
                "DATE_FORMAT(rec.operate_time,'%Y-%m-%d %H:%i:%s') as operateTime " +
                "from biz_alloc_adj_records rec " +
                "left join sys_user u on u.user_id=rec.new_user_id " +
                "left join sys_user s on s.user_id=rec.old_user_id " +
                "left join sys_user o on o.user_id=rec.operator where " +
                "rec.operate_type=? and  rec.operator=? ");

        //如果添加操作时间查询条件的话则添加操作时间参数
        if(StringUtils.isNotBlank(operateTime)){
           paramList.add(operateTime);
           sqlBuilder.append("and rec.operate_time like CONCAT(?,'%')");
        }

        sqlBuilder.append( " group by rec.operator, DATE_FORMAT(rec.operate_time,'%Y-%m-%d %H:%i:%s') order by rec.operate_time desc");

        //如果需要分页查询的话则添加分页参数
        if(pageNo !=-1 && pageSize !=-1){
            Integer start = (pageNo - 1) * pageSize;
            paramList.add(start);
            paramList.add(pageSize);
            sqlBuilder.append(" LIMIT ?,?");
        }
        Object[] params = paramList.toArray();
        return jdbcTemplate.query(sqlBuilder.toString(),params,new BeanPropertyRowMapper<>(OperationRecordsDTO.class));
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizAllocAdjRecords> getPageOperationRecordsList(PosTerminalDTO terminalDTO){
        Long userId = terminalDTO.getUserId();
        Integer operType = terminalDTO.getOperType();
        Integer pageNo = terminalDTO.getPageNo();
        Integer pageSize = terminalDTO.getPageSize();
        long start = (pageNo - 1) * pageSize;
        Object[] params = new Object[]{userId,userId,userId,operType};
        String sql = "select * from biz_alloc_adj_records where old_user_id=? or new_user_id=? or operator=? and operate_type=?";
        if(-1 != pageNo && -1 != pageSize){
            params = new Object[]{userId,userId,userId,operType,start,pageSize};
            sql = "select * from biz_alloc_adj_records where old_user_id=? or new_user_id=? or operator=? and operate_type=? LIMIT ?,?";
        }
        return jdbcTemplate.query(sql,params,new BeanPropertyRowMapper<BizAllocAdjRecords>(BizAllocAdjRecords.class));
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer geOperationRecordsCounts(PosTerminalDTO terminalDTO){
        Long userId = terminalDTO.getUserId();
        Integer operType = terminalDTO.getOperType();
        Object[] params = new Object[]{userId,userId,userId,operType};
        String sql = "select count(1) from biz_alloc_adj_records where old_user_id=? or new_user_id=? or operator=? and operate_type=?";
        return jdbcTemplate.queryForObject(sql,params,Integer.class);
    }

    @Override
    public List<String> getAdjRecordsListByOrderId(Long orderId) {
        return allocAdjRecordsMapper.selectAdjRecordsListByOrderId(orderId);
    }
}

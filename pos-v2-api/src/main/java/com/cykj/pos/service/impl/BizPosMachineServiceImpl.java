package com.cykj.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cykj.common.annotation.DataSource;
import com.cykj.common.config.RuoYiConfig;
import com.cykj.common.core.domain.entity.SysDictData;
import com.cykj.common.core.domain.entity.SysDictType;
import com.cykj.common.enums.DataSourceType;
import com.cykj.pos.domain.BizAllocAdjRecords;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.BizPosMachine;
import com.cykj.pos.mapper.BizPosMachineMapper;
import com.cykj.pos.profit.dto.*;
import com.cykj.pos.service.IBizAllocAdjRecordsService;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.service.IBizPosMachineService;
import com.cykj.pos.util.ListUtils;
import com.cykj.system.service.ISysDictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 终端设备信息Service业务层处理
 *
 * @author ruoyi
 * @date 2021-01-11
 */
@Service
public class BizPosMachineServiceImpl extends ServiceImpl<BizPosMachineMapper, BizPosMachine> implements IBizPosMachineService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private  IBizPosMachineService iBizPosMachineService;

    @Autowired
    private IBizAllocAdjRecordsService iBizAllocAdjRecordsService;

    @Autowired
    private IBizMerchantService iBizMerchantService;

    @Autowired
    private ISysDictTypeService iSysDictTypeService;

    @Autowired
    private RuoYiConfig config;

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizPosMachine> queryList(BizPosMachine bizPosMachine) {
        LambdaQueryWrapper<BizPosMachine> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(bizPosMachine.getPosName())){
            lqw.like(BizPosMachine::getPosName ,bizPosMachine.getPosName());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getPosCode())){
            lqw.eq(BizPosMachine::getPosCode ,bizPosMachine.getPosCode());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getPosType())){
            lqw.eq(BizPosMachine::getPosType ,bizPosMachine.getPosType());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getPosActivateCode())){
            lqw.eq(BizPosMachine::getPosActivateCode ,bizPosMachine.getPosActivateCode());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getPosActivateStatus())){
            lqw.eq(BizPosMachine::getPosActivateStatus ,bizPosMachine.getPosActivateStatus());
        }
        if (bizPosMachine.getPosBindTime() != null){
            lqw.eq(BizPosMachine::getPosBindTime ,bizPosMachine.getPosBindTime());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getPosActivity())){
            lqw.eq(BizPosMachine::getPosActivity ,bizPosMachine.getPosActivity());
        }
        if (bizPosMachine.getPosDeposit() != null){
            lqw.eq(BizPosMachine::getPosDeposit ,bizPosMachine.getPosDeposit());
        }
        if (bizPosMachine.getPosCashback() != null){
            lqw.eq(BizPosMachine::getPosCashback ,bizPosMachine.getPosCashback());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getPosModel())){
            lqw.eq(BizPosMachine::getPosModel ,bizPosMachine.getPosModel());
        }
        if (bizPosMachine.getMerchId() != null){
            lqw.eq(BizPosMachine::getMerchId ,bizPosMachine.getMerchId());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getVar1())){
            lqw.eq(BizPosMachine::getVar1 ,bizPosMachine.getVar1());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getVar2())){
            lqw.eq(BizPosMachine::getVar2 ,bizPosMachine.getVar2());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getVar3())){
            lqw.eq(BizPosMachine::getVar3 ,bizPosMachine.getVar3());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getVar4())){
            lqw.eq(BizPosMachine::getVar4 ,bizPosMachine.getVar4());
        }
        if (StringUtils.isNotBlank(bizPosMachine.getVar5())){
            lqw.eq(BizPosMachine::getVar5 ,bizPosMachine.getVar5());
        }
        return this.list(lqw);
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizPosMachine> getMachinesOfAllMerchantsWithQueryConditions(List<BizMerchant> merchants,BizPosMachine machine){
        List<BizPosMachine> machineList = new ArrayList<>();
        String posType = machine.getPosType();
        String posName = machine.getPosName();
        String posSNCode = machine.getPosCode();
        String posStatus = machine.getPosActivateStatus();

        List<Object> paramList = new ArrayList<>();
        StringBuilder sets = new StringBuilder();
        for(BizMerchant merchant: merchants){
            sets.append(merchant.getMerchId()).append(",");
        }
        String merchIdSets = sets.substring(0,sets.lastIndexOf(","));
        paramList.add(merchIdSets);

        StringBuilder sqlBuilder = new StringBuilder("select * from biz_pos_machine where  FIND_IN_SET(merch_id,?)");

        if(StringUtils.isNotBlank(posType)){
            paramList.add(posType);
            sqlBuilder.append(" and pos_type=?");
        }
        if(StringUtils.isNotBlank(posStatus)){
            paramList.add(posStatus);
            sqlBuilder.append(" and pos_activate_status=?");
        }
        if(StringUtils.isNotBlank(posSNCode)){
            paramList.add(posSNCode);
            sqlBuilder.append(" and pos_code=?");
        }
        if(StringUtils.isNotBlank(posName)){
            paramList.add("%"+posName+"%");
            sqlBuilder.append(" and pos_name like ?");
        }
        Object[] params = paramList.toArray();
        return jdbcTemplate.query(sqlBuilder.toString(),params,new BeanPropertyRowMapper<BizPosMachine>(BizPosMachine.class));
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizPosMachine> getPosMachineByMerchId(Long merchId){
        LambdaQueryWrapper<BizPosMachine> posQuery = Wrappers.lambdaQuery();
        posQuery.eq(BizPosMachine::getMerchId ,merchId);
        return this.list(posQuery);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizPosMachine> getPosMachinesByMerchId(Long merchId){
        String sql = "select * from biz_pos_machine where FIND_IN_SET(merch_id,findMerchSubNode(?))";
        return jdbcTemplate.query(sql,new Object[]{merchId},
                new BeanPropertyRowMapper<BizPosMachine>(BizPosMachine.class));
    }
    private  String queryCondtions(List<Object> paramList, PosTerminalDTO terminalVo,int pageNo,int pageSize){
        StringBuilder conditions = new StringBuilder(" ");
        String posName = terminalVo.getPosName();
        String posSNCode = terminalVo.getPosCode();
        String posType = terminalVo.getPosType();
        String posStatus =terminalVo.getPosActivateStatus();
        String posMachineType = terminalVo.getPosMachineType();
        if(StringUtils.isNotBlank(posType)){
            paramList.add(posType);
            conditions.append(" and find_in_set(pos_type,?)");
        }
        if(StringUtils.isNotBlank(posStatus)){
            paramList.add(posStatus);
            conditions.append(" and pos_activate_status=?");
        }
        if(StringUtils.isNotBlank(posSNCode)){
            paramList.add(posSNCode);
            conditions.append(" and pos_code like CONCAT(?,'%')");
        }
        if(StringUtils.isNotBlank(posMachineType)){
            paramList.add(posMachineType);
            conditions.append(" and find_in_set(var1,?)");
        }
        if(StringUtils.isNotBlank(posName)){
            paramList.add(posName);
            conditions.append(" and pos_name like CONCAT(?,'%')");
        }
        long start = (pageNo -1) * pageSize;
        if(pageNo != -1 && pageSize != -1){
            paramList.add(start);
            paramList.add(pageSize);
            conditions.append("LIMIT ?,?");
        }
        return conditions.toString();
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizPosMachine> getPosMachinesByMerchId(Long merchId, PosTerminalDTO terminalVo){
        List<Object> paramList = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("select * from biz_pos_machine where FIND_IN_SET(merch_id,findMerchSubNode(?))");
        paramList.add(merchId);
        sqlBuilder.append(this.queryCondtions(paramList,terminalVo,-1,-1));
        Object[] params = paramList.toArray();
        return jdbcTemplate.query(sqlBuilder.toString(),params, new BeanPropertyRowMapper<BizPosMachine>(BizPosMachine.class));
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizPosMachine> getPagePosMachinesByMerchId(Long merchId, PosTerminalDTO terminalVo,int pageNo,int pageSize){
        List<Object> paramList = new ArrayList<>();
        long start = (pageNo - 1)* pageSize;
        Integer operType = terminalVo.getOperType();
        StringBuilder sqlBuilder = null;
        if(operType != null){
            if(1 == operType){
                //仅能划拔本级终端
                sqlBuilder = new StringBuilder("select * from biz_pos_machine where merch_id=?");
            }
            if(2 == operType){
                //仅能回调下拉下级终端
                sqlBuilder = new StringBuilder("select * from biz_pos_machine where merch_id in (select merch_id from biz_merchant where parent_id=?)");
            }
        }else{
            sqlBuilder = new StringBuilder("select * from biz_pos_machine where FIND_IN_SET(merch_id,findMerchSubNode(?))");
        }
        paramList.add(merchId);
        sqlBuilder.append(this.queryCondtions(paramList,terminalVo,pageNo,pageSize));
        Object[] params = paramList.toArray();
        return jdbcTemplate.query(sqlBuilder.toString(),params, new BeanPropertyRowMapper<BizPosMachine>(BizPosMachine.class));
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizPosMachine> getPosMachinesByInteval(PosTerminalDTO terminalDTO){
        BizMerchant merchant = iBizMerchantService.getMerchantByUserId(terminalDTO.getUserId());
        Long merchantId = merchant.getMerchId();
        LambdaQueryWrapper<BizPosMachine> posQuery = Wrappers.lambdaQuery();
        posQuery.eq(BizPosMachine::getMerchId ,merchantId);
        posQuery.eq(BizPosMachine::getPosActivateStatus,terminalDTO.getPosActivateStatus());
        posQuery.between(BizPosMachine::getPosCode,terminalDTO.getPosCodeStart(),terminalDTO.getPosCodeEnd());
        return this.list(posQuery);
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public Long getPosMachineCountsByMerchId(Long merchId){
        String sql = "select count(1) from biz_pos_machine where FIND_IN_SET(merch_id,findMerchSubNode(?))";
        return jdbcTemplate.queryForObject(sql,new Object[]{merchId},Long.class);
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public Long getPosMachineActivatedCountsByMerchId(Long merchId){
        String sql = "select count(1) from biz_pos_machine where FIND_IN_SET(merch_id,findMerchSubNode(?)) and pos_activate_status='1'";
        return jdbcTemplate.queryForObject(sql,new Object[]{merchId},Long.class);
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getPosMachineActivatedCountsByMerchId(Long merchId,String thisMonth){
        LocalDate localDate = LocalDate.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")).substring(0,6);
        if("last".equals(thisMonth)){
            LocalDate newDate = LocalDate.now();
            newDate.minusMonths(1);
            formatedDate = newDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")).substring(0,6);
        }
        String sql = "select count(1) from biz_pos_machine where FIND_IN_SET(merch_id,findMerchSubNode(?)) and pos_activate_status='1' and pos_bind_time like ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{merchId,"'"+formatedDate+"%'"},Integer.class);
    }
    @Override
    @Transactional
    public Integer doTheOperations(List<Long> posIdList, Long merchantId, Long loginUser,Integer operType){
        List<BizPosMachine> posList = iBizPosMachineService.listByIds(posIdList);
        List<BizAllocAdjRecords> recordList = new ArrayList<>();
        LocalDateTime localDate = LocalDateTime.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        for(BizPosMachine pos:posList){
            if("1".equals(pos.getPosActivateStatus()))continue;
            //划拔操作记录
            BizAllocAdjRecords record = new BizAllocAdjRecords();
            BizMerchant oldMerchant = iBizMerchantService.getById(pos.getMerchId());
            BizMerchant newMerchant = iBizMerchantService.getById(merchantId);
            record.setPosId(pos.getPosId());
            record.setOldUserId(oldMerchant.getUserId());
            record.setNewUserId(newMerchant.getUserId());
            record.setPosCode(pos.getPosCode());
            record.setOperator(loginUser);
            record.setOperateTime(formatedDate);
            record.setOperateType(operType);
            recordList.add(record);

            pos.setVar2(newMerchant.getMerchCode());
            pos.setMerchId(merchantId);
        }
        iBizPosMachineService.updateBatchById(posList);
        iBizAllocAdjRecordsService.saveBatch(recordList);
        return posIdList.size();
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<Map<String,Object>> getPosFilterConditions(){
        List<Map<String,Object>> dictList = new ArrayList<>();
        List<String> typeList = config.getPosQueryFilterConditions();
        for(String type:typeList){
            Map<String,Object> dict = new HashMap<>();
            SysDictType dictType = iSysDictTypeService.selectDictTypeByType(type);
            List<SysDictData> dictData = iSysDictTypeService.selectDictDataByType(type);
            dict.put("dictType",dictType);
            dict.put("dictData",dictData);
            dictList.add(dict);
        }
        return dictList;
    }

    @Override
    public BizPosMachine getPosMachineBySnCode(String snCode){
        LambdaQueryWrapper<BizPosMachine> posQuery = Wrappers.lambdaQuery();
        posQuery.eq(BizPosMachine::getPosCode ,snCode);
        return this.getOne(posQuery);
    }

    @Override
    public void posMachineActivate(TerminalActivateDTO terminalActivateDTO) {

    }

    @Override
    public void posMachineBind(TerminalBindDTO terminalBindDTO) {

    }

    @Transactional
    public void posMachineActivate(OrdinaryMerchantDTO ordinaryMerchantDTO){
        String snCode = ordinaryMerchantDTO.getSnCode();
//        String userName = ordinaryMerchantDTO.getMerchName();
//        String mobile = ordinaryMerchantDTO.getMarkedMobile();
        BizPosMachine machine = this.getPosMachineBySnCode(snCode);
//        Long parentMerchId = machine.getMerchId();
//        Long parentUserId = this.getUserIdByMerchId(parentMerchId);
//        //1.生成禁用状态的用户信息
//        SysUser user = new SysUser();
//        user.setUserName(mobile);
//        user.setStatus("1");//默认禁用
//        user.setNickName(userName);
//        user.setInviteUserId(parentUserId);
//        user.setPassword(SecurityUtils.encryptPassword("123456"));
//        sysUserService.insertUser(user);
//        //2.新增普通商户
//        BizMerchant merchant = new BizMerchant();
//        merchant.setMerchCode(this.generateMerchCode("CY"));
//        merchant.setMerchName(userName);
//        this.save(merchant);
        //3.更新机具状态为激活
        machine.setPosActivateStatus("1");
        this.updateById(machine);
    }
}

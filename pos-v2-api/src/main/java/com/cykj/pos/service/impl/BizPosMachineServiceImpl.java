package com.cykj.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cykj.common.annotation.DataSource;
import com.cykj.common.config.RuoYiConfig;
import com.cykj.common.constant.Constants;
import com.cykj.common.core.domain.entity.SysDictData;
import com.cykj.common.core.domain.entity.SysDictType;
import com.cykj.common.enums.DataSourceType;
import com.cykj.pos.domain.*;
import com.cykj.pos.domain.dto.MachineTransferDTO;
import com.cykj.pos.mapper.BizMerchantMapper;
import com.cykj.pos.mapper.BizPosMachineMapper;
import com.cykj.pos.profit.dto.*;
import com.cykj.pos.service.*;
import com.cykj.pos.util.*;
import com.cykj.pos.websocket.server.WebSocketServer;
import com.cykj.system.service.ISysDictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private IBizPosMachineStatusRecordsService bizPosMachineStatusRecordsService;

    @Autowired
    IBizMerchBillService bizMerchBillService;

    @Autowired
    IBizWalletService walletService;

    @Autowired
    IBizMerchIntegralService merchIntegralService;

    @Autowired
    IBizPosPolicyService posPolicyService;

    @Autowired
    BizPosMachineMapper posMachineMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private IBizMerchOrderService merchOrderService;

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
        // 加排序 order by pos_code asc
        conditions.append(" order by pos_code asc");
        long start = (pageNo -1) * pageSize;
        if(pageNo != -1 && pageSize != -1){
            paramList.add(start);
            paramList.add(pageSize);
            conditions.append(" LIMIT ?,?");
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
    public List<MachineTransferDTO> getPagePosMachinesByMerchId(Long merchId, PosTerminalDTO terminalVo,int pageNo,int pageSize){
        List<Object> paramList = new ArrayList<>();
        long start = (pageNo - 1)* pageSize;
        Integer operType = terminalVo.getOperType();
        StringBuilder sqlBuilder = null;
        if(operType != null){
            if(1 == operType){
                //仅能划拔本级终端
                sqlBuilder = new StringBuilder("SELECT * FROM biz_pos_machine WHERE pos_activate_status=0 AND merch_id=? AND " +
                        "pos_code NOT IN (SELECT sn_code FROM biz_pos_machine_status_records) ");
            }
            if(2 == operType){
                //仅能回调下拉下级终端
                sqlBuilder = new StringBuilder("select * from biz_pos_machine where pos_activate_status=0 AND merch_id in (select merch_id from biz_merchant where parent_id=?) ");
            }
        }else{
            // sqlBuilder = new StringBuilder("select * from biz_pos_machine where FIND_IN_SET(merch_id,findMerchSubNode(?))");
            sqlBuilder = new StringBuilder("select m.*,r.records_type recordsType from biz_pos_machine m LEFT JOIN biz_pos_machine_status_records r ON m.pos_code=r.sn_code WHERE merch_id=? ");
        }
        paramList.add(merchId);
        sqlBuilder.append(this.queryCondtions(paramList,terminalVo,pageNo,pageSize));
        Object[] params = paramList.toArray();
        return jdbcTemplate.query(sqlBuilder.toString(),params, new BeanPropertyRowMapper<MachineTransferDTO>(MachineTransferDTO.class));
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
        String sql = "select count(1) from biz_pos_machine where merch_id=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{merchId},Long.class);
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public Long getPosMachineActivatedCountsByMerchId(Long merchId){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT count(*) FROM biz_pos_machine_status_records r ");
        sql.append("WHERE r.records_type='2' and r.sn_code IN ");
        sql.append(" (SELECT pos_code FROM biz_pos_machine WHERE merch_id=?) ");
        return jdbcTemplate.queryForObject(sql.toString(),new Object[]{merchId},Long.class);
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public Long getPosMachineNotActivatedAndBindCountsByMerchId(Long merchId){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) from biz_pos_machine where merch_id=? and pos_code not in  ");
        sql.append("( select sn_code from biz_pos_machine_status_records )");
        return jdbcTemplate.queryForObject(sql.toString(),new Object[]{merchId},Long.class);
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
        String sql = "select count(1) from biz_pos_machine where FIND_IN_SET(merch_id,findMerchSubNode(?)) and pos_activate_status='2' and pos_bind_time like ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{merchId,"'"+formatedDate+"%'"},Integer.class);
    }
    @Override
    @Transactional
    public Integer doTheOperations(List<Long> posIdList, Long merchantId, Long loginUser,Integer operType,Long orderId){
        // 生成订单  改变订单状态
        BizMerchOrder merchOrder = null;
        if(orderId!=null && orderId!=0){
            //  改变订单状态
            merchOrder = merchOrderService.getById(orderId);
            merchOrder.setStatus(1);
            merchOrderService.saveOrUpdate(merchOrder);
        }
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
            // 如果是积分兑换  需要存储订单编号
            if(merchOrder!=null){
                record.setVar1(merchOrder.getOrderNo());
            }
            recordList.add(record);
            pos.setVar2(newMerchant.getMerchCode());
            pos.setMerchId(merchantId);
        }
        iBizPosMachineService.updateBatchById(posList);
        iBizAllocAdjRecordsService.saveBatch(recordList);

        // 发消息
        BizMessageRecords messageRecords = new BizMessageRecords();
        // 发消息   1- 入库
        String rukuMess = "您的机具已经下发，共"+posIdList.size()+"台机具，请注意查收";
        messageRecords.setMsgContent(rukuMess);// 消息内容
        messageRecords.setMsgType(1); // 消息类型  入库
        messageRecords.setReadStatus(0);// 消息未读
        messageRecords.setAdviceType(1); // 业务消息
        BizMerchant merchant = iBizMerchantService.getMerchantByMerchId(merchantId);
        webSocketServer.sendInfo(merchant.getUserId(), messageRecords);// 发送消息
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
    @Transactional
    public void posMachineActivate(TerminalActivateDTO terminalActivateDTO) {
        // 根据merchant_id获得绑定的记录信息
        String merchantId = terminalActivateDTO.getMerchantId();
        BizPosMachineStatusRecords posMachineStatusRecords = bizPosMachineStatusRecordsService.getPosMachineStatusRecordsByMerchantId(merchantId);
        // BizPosMachineStatusRecords posMachineStatusRecords = new BizPosMachineStatusRecords();
        // posMachineStatusRecords.setPhoneNo(terminalActivateDTO.getPhoneNo());
        // posMachineStatusRecords.setTerminalId(terminalActivateDTO.getTerminalId());
        // posMachineStatusRecords.setDeviceType(terminalActivateDTO.getDeviceType());
        // posMachineStatusRecords.setSnCode(terminalActivateDTO.getSnCode());
        // posMachineStatusRecords.setDirectlyOrgId(terminalActivateDTO.getDirectlyOrgID());
        //posMachineStatusRecords.setMerchantId(terminalActivateDTO.getMerchantId());
        posMachineStatusRecords.setIsActivated(terminalActivateDTO.getIsActivated());
        posMachineStatusRecords.setReceiptType(terminalActivateDTO.getReceiptType());
        posMachineStatusRecords.setName(terminalActivateDTO.getName());
        posMachineStatusRecords.setActiveTime(terminalActivateDTO.getActiveTime());
        posMachineStatusRecords.setIdTxn(terminalActivateDTO.getIdTxn());
        posMachineStatusRecords.setSecondOrgId(terminalActivateDTO.getSecondOrgID());
        posMachineStatusRecords.setOrderId(terminalActivateDTO.getOrderId());
        posMachineStatusRecords.setPolicyId(terminalActivateDTO.getPolicyId()); // 政策编号
        // -------------------自己扩展内容--------------------------
        LocalDateTime localDateTime = LocalDateTime.now();
        String receiptDate = DateUtils.localeDateTime2String(localDateTime, Constants.DATETIME_FORMATTER);
        posMachineStatusRecords.setReceiptDate(receiptDate); // 设置接收时间
        posMachineStatusRecords.setRecordsType("2"); //该记录是设备激活操作记录
        // 保存设备状态记录
        bizPosMachineStatusRecordsService.saveOrUpdate(posMachineStatusRecords);
        // 获得政策信息  重要就是这块
        BizPosPolicy posPolicy = posPolicyService.getPosPolicyByPolicyId(terminalActivateDTO.getPolicyId());
        Double returnMoney = 120.00; // 默认返现金额
        Double returnTaxation = 0.09;// 默认税点
        Long returnIntegral = 120L; // 通用积分
        // 获得政策中的返现金额
        if(posPolicy!=null){
            returnMoney = posPolicy.getReturnMoney().doubleValue();
            returnTaxation = posPolicy.getTaxation().doubleValue();
            returnIntegral = posPolicy.getReturnIntegral();
        }
        //向商户账单表中插入数据
        //--------------------- 插入账单  返现 1--------------------------
        // 通过设备号获得商户信息
        BizPosMachine posMachine = getPosMachineBySnCode(terminalActivateDTO.getSnCode());
        // 更新pos机器状态
        posMachine.setPosActivateStatus("2");//状态为2是激活
        saveOrUpdate(posMachine);//更新pos机
        BizMerchBill merchBill = new BizMerchBill();
        Long merId = posMachine.getMerchId();
        BizMerchant merchant = iBizMerchantService.getMerchantByMerchId(merId);
        merchBill.setMerchId(posMachine.getMerchId());//POS机器的主人
        merchBill.setMerchName(terminalActivateDTO.getName()); // 名称
        merchBill.setPosType(posMachine.getPosType()); // 设备类型
        merchBill.setPosCode(posMachine.getPosCode()); // 设备编号
        merchBill.setBillType("1"); // 账单类型  返现
        BigDecimal amount = new BigDecimal(returnMoney*(1-returnTaxation));
        merchBill.setAmount(amount.setScale(2,BigDecimal.ROUND_HALF_UP));// 返现金额  四射侮辱
        merchBill.setPolicyId(terminalActivateDTO.getPolicyId());//正常id  默认设置成1001
        merchBill.setBillDate(DateUtils.localeDateTime2String(localDateTime, Constants.DATETIME_FORMATTER)); // 账单日期
        BigDecimal taxation = new BigDecimal(returnMoney*returnTaxation);
        merchBill.setTaxation(taxation.setScale(2,BigDecimal.ROUND_HALF_UP)); // 税点
        // 保存账单
        bizMerchBillService.saveOrUpdate(merchBill);
        Long merchId = posMachine.getMerchId();
        // 获得商户
        BizMerchant bizMerchant = iBizMerchantService.getMerchantByMerchId(merId);
        //向商户积分表中插入数据
        //--------------------- 插入积分明细   激活机具--------------------------
        BizMerchIntegral merchIntegral = new BizMerchIntegral();
        merchIntegral.setMerchId(merchId);
        merchIntegral.setPosCode(posMachine.getPosCode());
        merchIntegral.setIntegralType("激活机具");
        merchIntegral.setValue(returnIntegral);
        merchIntegral.setTransType("1");//收入  支出2
        // merchIntegral.setOrderId(UUID.randomUUID().toString().replaceAll("-","")); //订单编号
        merchIntegralService.saveOrUpdate(merchIntegral);
        //---------------------  更新钱包   ----------------------------
        // 1-通过user_id获取钱包
        // 获得用户id
        Long userId = bizMerchant.getUserId();
        BizWallet wallet = walletService.getMyWalletByUserId(userId);
        // 更新数据
        String secProfitAmount = wallet.getProfitAmount();//获得结算账户余额
        String secRewardAmount = wallet.getRewardAmount();// 获得奖励数据
        String secIntegral = wallet.getIntegral(); // 通用积分
        String key = wallet.getSecretKey();// 获得key
        // 解密数据
        String profitAmountStr = DESHelperUtil.decrypt(key,secProfitAmount);
        String rewardAmountStr = DESHelperUtil.decrypt(key,secRewardAmount);
        String integralStr = DESHelperUtil.decrypt(key,secIntegral);
        // 转换成BigDecimal类型
        BigDecimal profitAmount = new BigDecimal(profitAmountStr);
        BigDecimal rewardAmount = new BigDecimal(rewardAmountStr);
        profitAmount = profitAmount.add(BigDecimal.valueOf(returnMoney*(1-returnTaxation)));
        BigDecimal walletAmount = profitAmount.add(rewardAmount);
        Long integral = Long.parseLong(integralStr);
        integral = integral+returnIntegral;// 通用积分
        // 加密
        String profitAmountMoneyStr = DESHelperUtil.encrypt(key, BigDecimalUtil.getString(profitAmount));
        String rewardAmountMoneyStr = DESHelperUtil.encrypt(key, BigDecimalUtil.getString(rewardAmount));
        String walletAmountMoneyStr = DESHelperUtil.encrypt(key, BigDecimalUtil.getString(walletAmount));
        String integralMoneyStr = DESHelperUtil.encrypt(key,String.valueOf(integral));
        wallet.setProfitAmount(profitAmountMoneyStr);
        wallet.setRewardAmount(rewardAmountMoneyStr);
        wallet.setWalletAmount(walletAmountMoneyStr);
        wallet.setIntegral(integralMoneyStr);
        wallet.setSecretKey(key);
        // 保存钱包信息
        walletService.saveOrUpdate(wallet);
        // 发消息
        String fanXianMess = "返现："+returnMoney*(1-returnTaxation)+"元";
        BizMessageRecords fanXianMessageRecords = new BizMessageRecords();
        fanXianMessageRecords.setMsgContent(fanXianMess);// 消息内容
        fanXianMessageRecords.setMsgType(2); // 消息类型  返现
        fanXianMessageRecords.setReadStatus(0); // 消息未读
        fanXianMessageRecords.setAdviceType(1); // 业务消息
        webSocketServer.sendInfo(userId,fanXianMessageRecords);// 发送消息

        String jiFenMess = "返积分："+returnIntegral;
        BizMessageRecords jiFenMessageRecords = new BizMessageRecords();
        jiFenMessageRecords.setMsgContent(jiFenMess);// 消息内容
        jiFenMessageRecords.setMsgType(8); // 消息类型  返积分
        jiFenMessageRecords.setReadStatus(0);// 消息未读
        jiFenMessageRecords.setAdviceType(1); // 业务消息
        webSocketServer.sendInfo(userId,jiFenMessageRecords);// 发送消息
    }

    @Override
    @Transactional
    public void posMachineBind(TerminalBindDTO terminalBindDTO) {
        BizPosMachineStatusRecords posMachineStatusRecords = new BizPosMachineStatusRecords();
        // ----------快钱接口提供数据--------------
        posMachineStatusRecords.setSnCode(terminalBindDTO.getSnCode()); // 设备SN码
        posMachineStatusRecords.setMerchantId(terminalBindDTO.getMerchantId()); // 商户标识
        posMachineStatusRecords.setTerminalId(terminalBindDTO.getTerminalId()); // 商户终端ID
        posMachineStatusRecords.setDeviceType(terminalBindDTO.getDeviceType()); // 设备类型
        posMachineStatusRecords.setReceiptType(terminalBindDTO.getReceiptType()); // 产品名称
        posMachineStatusRecords.setDirectlyOrgId(terminalBindDTO.getDirectlyOrgID()); // 直属机构号
        posMachineStatusRecords.setPhoneNo(terminalBindDTO.getPhoneNo()); // 手机号
        posMachineStatusRecords.setIdCardNo(terminalBindDTO.getIdCardNo());// 身份号
        posMachineStatusRecords.setTime(terminalBindDTO.getTime()); // 绑定时间
        posMachineStatusRecords.setStatus(terminalBindDTO.getStatus()); // 绑定状态
        // ---------------------------------------
        // -----------------下面是待接收的内容----------------------
        posMachineStatusRecords.setSecondOrgId(terminalBindDTO.getSecondOrgID());// 二级机构号
        posMachineStatusRecords.setName(terminalBindDTO.getName()); // 姓名
        // -------------------自己扩展内容--------------------------
        LocalDateTime localDateTime = LocalDateTime.now();
        String receiptDate = DateUtils.localeDateTime2String(localDateTime, Constants.DATETIME_FORMATTER);
        posMachineStatusRecords.setReceiptDate(receiptDate); // 设置接收时间
        posMachineStatusRecords.setRecordsType("1"); //该记录是设备绑卡操作记录
        bizPosMachineStatusRecordsService.saveOrUpdate(posMachineStatusRecords);
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

    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getPosMachineAllCountsByMerchId(Long merchantId) {
        return posMachineMapper.getPosMachineAllCountsByMerchId(merchantId);
    }
}

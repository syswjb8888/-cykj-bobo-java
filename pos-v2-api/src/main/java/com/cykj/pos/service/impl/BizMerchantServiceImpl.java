package com.cykj.pos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cykj.common.annotation.DataSource;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.common.core.text.Convert;
import com.cykj.common.enums.DataSourceType;
import com.cykj.common.utils.SecurityUtils;
import com.cykj.pos.domain.BizMerchTransactions;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.BizMicroInfo;
import com.cykj.pos.domain.BizWallet;
import com.cykj.pos.domain.dto.HomePageDTO;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.mapper.BizMerchantMapper;
import com.cykj.pos.profit.dto.*;
import com.cykj.pos.service.*;
import com.cykj.pos.util.BigDecimalUtil;
import com.cykj.pos.util.DESHelperUtil;
import com.cykj.pos.util.DateUtils;
import com.cykj.pos.util.VerifyCodeUtils;
import com.cykj.system.service.ISysDictDataService;
import com.cykj.system.service.ISysUserService;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 报件商户信息Service业务层处理
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@Service
public class BizMerchantServiceImpl extends ServiceImpl<BizMerchantMapper, BizMerchant> implements IBizMerchantService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IBizMerchTransactionsService transRecordsService;

    @Autowired
    private IBizPosMachineService  posMachineService;

    @Autowired
    private IBizMicroInfoService microInfoService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private IBizWalletService walletService;

    @Autowired
    private ISMSService smsService;

    @Autowired
    private IBizMicroInfoService bizMicroInfoService;

    @Autowired
    private IBizMerchTransactionsService bizMerchTransactionsService;

    @Autowired
    private  IBizMerchantService merchantService;

    @Autowired
    BizMerchantMapper bizMerchantMapper;

    @Autowired
    IBizAreaDictService areaDictService;

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMerchant> queryList(BizMerchant bizMerchart) {

        LambdaQueryWrapper<BizMerchant> lqw = Wrappers.lambdaQuery();
        if (bizMerchart.getUserId() != null) {
            lqw.eq(BizMerchant::getUserId, bizMerchart.getUserId());
        }
        if (bizMerchart.getParentId() != null) {
            lqw.eq(BizMerchant::getParentId, bizMerchart.getParentId());
        }
        if (StringUtils.isNotBlank(bizMerchart.getMerchCode())) {
            lqw.eq(BizMerchant::getMerchCode, bizMerchart.getMerchCode());
        }
        if (StringUtils.isNotBlank(bizMerchart.getMerchName())) {
            lqw.like(BizMerchant::getMerchName, bizMerchart.getMerchName());
        }
        if (StringUtils.isNotBlank(bizMerchart.getMerchAbbr())) {
            lqw.eq(BizMerchant::getMerchAbbr, bizMerchart.getMerchAbbr());
        }
        if (StringUtils.isNotBlank(bizMerchart.getMerchRegion())) {
            lqw.eq(BizMerchant::getMerchRegion, bizMerchart.getMerchRegion());
        }
        if (StringUtils.isNotBlank(bizMerchart.getMerchAddr())) {
            lqw.eq(BizMerchant::getMerchAddr, bizMerchart.getMerchAddr());
        }
        if (StringUtils.isNotBlank(bizMerchart.getMerchBizScope())) {
            lqw.eq(BizMerchant::getMerchBizScope, bizMerchart.getMerchBizScope());
        }
        if (StringUtils.isNotBlank(bizMerchart.getMerchType())) {
            lqw.eq(BizMerchant::getMerchType, bizMerchart.getMerchType());
        }
        if (StringUtils.isNotBlank(bizMerchart.getVerifyStatus())) {
            lqw.eq(BizMerchant::getVerifyStatus, bizMerchart.getVerifyStatus());
        }
        if (StringUtils.isNotBlank(bizMerchart.getVerifyResult())) {
            lqw.eq(BizMerchant::getVerifyResult, bizMerchart.getVerifyResult());
        }
        if (StringUtils.isNotBlank(bizMerchart.getVar1())) {
            lqw.eq(BizMerchant::getVar1, bizMerchart.getVar1());
        }
        if (StringUtils.isNotBlank(bizMerchart.getVar2())) {
            lqw.eq(BizMerchant::getVar2, bizMerchart.getVar2());
        }
        if (StringUtils.isNotBlank(bizMerchart.getVar3())) {
            lqw.eq(BizMerchant::getVar3, bizMerchart.getVar3());
        }
        if (StringUtils.isNotBlank(bizMerchart.getVar4())) {
            lqw.eq(BizMerchant::getVar4, bizMerchart.getVar4());
        }
        if (StringUtils.isNotBlank(bizMerchart.getVar5())) {
            lqw.eq(BizMerchant::getVar5, bizMerchart.getVar5());
        }
        return this.list(lqw);
    }

    @Override
    public List<TreeSelect> buildMerchantTreeSelect(List<BizMerchant> merchants) {
        List<BizMerchant> merchantTree = this.buildMerchantTree(merchants);
        return merchantTree.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<BizMerchant> buildMerchantTree(List<BizMerchant> merchants) {
        List<BizMerchant> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (BizMerchant merchant : merchants) {
            tempList.add(merchant.getMerchId());
        }
        for (Iterator<BizMerchant> iterator = merchants.iterator(); iterator.hasNext(); ) {
            BizMerchant merchant = iterator.next();
            if (!tempList.contains(merchant.getMerchId())) {
                recursionFunc(merchants, merchant);
                returnList.add(merchant);
            }
        }

        if (returnList.isEmpty()) return merchants;

        return returnList;
    }

    private void recursionFunc(List<BizMerchant> list, BizMerchant t) {
        List<BizMerchant> childList = getChildList(list, t);
        t.setChildren(childList);
        for (BizMerchant tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFunc(list, tChild);
            }
        }
    }

    private List<BizMerchant> getChildList(List<BizMerchant> list, BizMerchant t) {
        List<BizMerchant> tList = new ArrayList<>();
        Iterator<BizMerchant> it = list.iterator();
        while (it.hasNext()) {
            BizMerchant merchant = it.next();
            Long parentId = merchant.getParentId();
            Long tId = t.getMerchId();
            if (parentId != null && parentId == tId) {
                tList.add(merchant);
            }
        }
        return tList;
    }

    private boolean hasChild(List<BizMerchant> list, BizMerchant t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public BizMerchant getMerchantByMerchId(Long merId) {
        LambdaQueryWrapper<BizMerchant> merchantQuery = Wrappers.lambdaQuery();
        merchantQuery.eq(BizMerchant::getMerchId, merId);
        BizMerchant merchant = this.getOne(merchantQuery);
        return merchant;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public BizMerchant getMerchantByUserId(Long loginUser) {
        LambdaQueryWrapper<BizMerchant> merchantQuery = Wrappers.lambdaQuery();
        merchantQuery.eq(BizMerchant::getUserId, loginUser);
        BizMerchant merchant = this.getOne(merchantQuery);
        return merchant;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public Long getUserIdByMerchId(Long merchId){
        String sql = "select user_id from biz_merchant where merch_id=? limit 1";
        return jdbcTemplate.queryForObject(sql,new Object[]{merchId},Long.class);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMerchant> getAllSubNodeAndIncludeSelf(Long merchId) {
        String sql = "SELECT  * FROM biz_merchant WHERE FIND_IN_SET(merch_id,findMerchSubNode(?))";
        return jdbcTemplate.query(sql, new Object[]{merchId}, new BeanPropertyRowMapper<BizMerchant>(BizMerchant.class));
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMerchant> getAllSubNodeAndIncludeSelf(Long merchId, BizMerchant merchant) {
        String merchantName = merchant.getMerchName();
        String merchantAbbr = merchant.getMerchAbbr();
        String merchantCode = merchant.getMerchCode();
        String merchantRegion = merchant.getMerchRegion();
        List<Object> paramList = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT  * FROM biz_merchant WHERE FIND_IN_SET(merch_id,findMerchSubNode(?)) ");

        paramList.add(merchId);

        if (StringUtils.isNotBlank(merchantName)) {
            paramList.add("%" + merchantName + "%");
            sqlBuilder.append(" and merch_name like ? ");
        }
        if (StringUtils.isNotBlank(merchantAbbr)) {
            paramList.add("%" + merchantAbbr + "%");
            sqlBuilder.append(" and merch_abbr like ? ");
        }
        if (StringUtils.isNotBlank(merchantCode)) {
            paramList.add(merchantCode);
            sqlBuilder.append(" and merch_code=? ");
        }
        if (StringUtils.isNotBlank(merchantRegion)) {
            paramList.add(merchantRegion);
            sqlBuilder.append(" and merch_region=? ");
        }
        Object[] params = paramList.toArray();

        return jdbcTemplate.query(sqlBuilder.toString(), params, new BeanPropertyRowMapper<BizMerchant>(BizMerchant.class));
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getMonthlyNewMerchantCounts(Long merchId) {
        LocalDate localDate = LocalDate.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).substring(0, 7);
        String sql = "SELECT  count(*) FROM biz_merchant WHERE FIND_IN_SET(merch_id,findMerchSubNode(?)) and create_time like ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{merchId, formatedDate}, Integer.class);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getMonthLyNewMerchantExcludeChild(Long merchId) {
        LocalDate localDate = LocalDate.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).substring(0, 7);
        String sql = "SELECT  merch_id FROM biz_merchant WHERE FIND_IN_SET(merch_id,findMerchSubNode(?)) and create_time like ?";
        List<Long> merchIds = jdbcTemplate.queryForList(sql, new Object[]{merchId, formatedDate}, Long.class);
        String queryLeafNode = "select count(1) from biz_merchant where parent_id=? and create_time like ?";
        Integer leafCounts = 0;
        for (Long merchantId : merchIds) {
            Integer count = jdbcTemplate.queryForObject(queryLeafNode, new Object[]{merchantId, formatedDate}, Integer.class);
            if (count < 1) {
                leafCounts++;
            } else {
                continue;
            }
        }
        return leafCounts;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
    public String generateMerchCode(String prefix){
        Integer seqNo = this.getGenerateSequence(prefix);
        String suffixCode =  String.format("%09d", seqNo);
        String certificationNo = prefix + suffixCode;
        return certificationNo;
    }
    @Override
    @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
    public String generateMerchCode(String prefix,String regionCode){
        Integer seqNo = this.getGenerateSequence(prefix);
        String suffixCode =  String.format("%09d", seqNo);
        String certificationNo = prefix + regionCode + suffixCode;
        return certificationNo;
    }
    private  Integer  getGenerateSequence(String seqName){
        Integer param2Value = (Integer) jdbcTemplate.execute(
                new CallableStatementCreator() {
                    public CallableStatement createCallableStatement(Connection con) throws SQLException {
                        String storedProc = "{call generateSequence(?,?)}";// 调用的sql
                        CallableStatement cs = con.prepareCall(storedProc);
                        cs.setString(1, seqName);// 设置输入参数的值
                        cs.registerOutParameter(2, Types.INTEGER);// 注册输出参数的类型
                        return cs;
                    }
                }, new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                        cs.execute();
                        return cs.getInt(2);// 获取输出参数的值
                    }
                });
        return param2Value;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<MerchantDict> getChildMerchartDictList(Long parentId){
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setUserId(parentId);
        List<BizMerchant> merchants = this.getChildMerchantList(merchantDTO,-1,-1);
        List<MerchantDict> dicts = new ArrayList<>(merchants.size());
        for(int i=0;i< merchants.size();i++){
            Long userId = merchants.get(i).getUserId();
            SysUser user = sysUserService.selectUserById(userId);

            MerchantDict dict = new MerchantDict();
            dict.setId(i);
            dict.setDictValue(merchants.get(i).getMerchId());
            dict.setDictLabel(merchants.get(i).getMerchName());
            dict.setUserId(userId);
            dict.setMobile(user.getPhonenumber());
            // 添加头像
            dict.setPortrait(user.getPortrait());
            dicts.add(dict);
        }
        return dicts;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMerchant> getChildMerchantList(MerchantDTO merchantDTO,int pageNo,int pageSize){
        BizMerchant parentMerchant = this.getMerchantByUserId(merchantDTO.getUserId());
        Long parentUserId = parentMerchant.getUserId();
        LambdaQueryWrapper<BizMerchant> merchantQuery = Wrappers.lambdaQuery();
        BizMerchant merchant = this.getMerchantByUserId(parentUserId);
        merchantQuery.eq(BizMerchant::getParentId ,merchant.getMerchId());
        String nickName = merchantDTO.getNickName();
        String verifyStatus = merchantDTO.getVerifyStatus();
        if(StringUtils.isNotBlank(nickName)){
            merchantQuery.likeRight(BizMerchant::getMerchName,nickName);
        }
        /*if(StringUtils.isNotBlank(verifyStatus)){
            merchantQuery.eq(BizMerchant::getVerifyStatus,verifyStatus);
        }*/
        if(pageNo == -1 || pageSize == -1) return this.list(merchantQuery);
        merchantQuery.last("LIMIT "+(pageNo -1)*pageSize+","+pageSize);
        return this.list(merchantQuery);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getChildMerchantCounts(Long userId,String verifyStatus,String thisMonth,String nickName){
        BizMerchant merchant = this.getMerchantByUserId(userId);
        Long parentId = merchant.getMerchId();
        LambdaQueryWrapper<BizMerchant> merchantQuery = Wrappers.lambdaQuery();
        merchantQuery.eq(BizMerchant::getParentId ,parentId);
       /* if(StringUtils.isNotBlank(verifyStatus)){
            merchantQuery.eq(BizMerchant::getVerifyStatus ,verifyStatus);
        }*/
        if(StringUtils.isNotBlank(thisMonth)){
            merchantQuery.likeRight(BizMerchant::getCreateTime ,thisMonth);
        }
        if(StringUtils.isNotBlank(nickName)){
            merchantQuery.like(BizMerchant::getMerchName,nickName);
        }
        return this.count(merchantQuery);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public  PartnerDTO getPartnerDetail(Long userId){
        PartnerDTO partnerDTO = new PartnerDTO();
        BizMerchant merchant = this.getMerchantByUserId(userId);
        Long merchantId = merchant.getMerchId();
        SysUser user = sysUserService.selectUserById(userId);

        // BigDecimal thisMonthAmount = transRecordsService.getMonthlyTransAmountByMerchId(merchantId,"this");
        //本月交易额
        BigDecimal thisMonthAmount = transRecordsService.getMonthlyTransAmountByMerchId(merchantId);
        //上月交易额
        BigDecimal lastMonthAmount = transRecordsService.getMonthlyTransAmountByMerchId(merchantId,"last");
        //本月激活post机数量
        Integer thisActivePostMachine = this.getActivePostMachineCounts(merchantId,"this");
        //上月激活post机数量
        Integer lastActivePostMachine = this.getActivePostMachineCounts(merchantId,"last");

        //POST机器总数
        Integer count = posMachineService.getPosMachineAllCountsByMerchId(merchantId);
        //已激活POST机器
        Integer lastPosCounts = this.getActivePostMachineCounts(merchantId,null);
        //未激活POST机器
        Integer thisPosCounts = count-lastPosCounts;
        partnerDTO.setPartnerName(user.getNickName());
        partnerDTO.setPartnerMobile(user.getPhonenumber());
        partnerDTO.setTeamTransAmount(thisMonthAmount);
        partnerDTO.setLastMonthTeamTransAmount(lastMonthAmount);
        partnerDTO.setTeamActiveCounts(thisActivePostMachine);
        partnerDTO.setLastMonthTeamActiveCounts(lastActivePostMachine);
        partnerDTO.setTeamActiveMachines(thisPosCounts);
        partnerDTO.setLastMonthteamActiveMacines(lastPosCounts);
        partnerDTO.setParnterId(userId);
        // 加入头像
        partnerDTO.setPortrait(user.getPortrait());
        partnerDTO.setRegisterTime(DateUtils.Date2String(user.getCreateTime(),"YYYY-MM-dd HH:mm:ss"));
        return partnerDTO;
    }
    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<PartnerDTO> getPagedPartnerList(MerchantDTO merchantDTO){
        List<PartnerDTO> partners = new ArrayList<>();
        int pageNo = merchantDTO.getPageNo();
        int pageSize = merchantDTO.getPageSize();
        List<BizMerchant> merchants = this.getChildMerchantList(merchantDTO,pageNo,pageSize);
        for(BizMerchant merchant:merchants){
            PartnerDTO partnerDTO = new PartnerDTO();
            Long merchantId = merchant.getMerchId();
            Long uId = merchant.getUserId();
            SysUser user = sysUserService.selectUserById(uId);
            // BigDecimal thisMonthAmount = transRecordsService.getTotalTransAmountByMerchId(merchantId); //商户所有交易额
            BigDecimal thisMonthAmount = transRecordsService.getMonthlyTransAmountByMerchId(merchantId);
            Integer counts = merchantService.getTotalNewMerchCounts(merchantId);// 团队激活数

            partnerDTO.setPartnerName(user.getNickName());
            partnerDTO.setPartnerMobile(user.getPhonenumber());
            partnerDTO.setTeamTransAmount(thisMonthAmount);
            partnerDTO.setTeamActiveCounts(counts);
            partnerDTO.setParnterId(user.getUserId());
            // 添加头像
            partnerDTO.setPortrait(user.getPortrait());
            partners.add(partnerDTO);
        }
        return partners;
    }

    @Override
    @Transactional
    public void microMerchantRegister(MicroMerchantDTO microMerchantDTO){
        Long userId = microMerchantDTO.getUserId();

        SysUser user = sysUserService.selectUserById(userId);
        Long inviteUserId = user.getInviteUserId();

        BizMerchant parentMerchant = this.getMerchantByUserId(inviteUserId);

        //商户入驻过则更新，未入驻过则新增
        BizMerchant merchant = this.getMerchantByUserId(userId);
        if(merchant == null){
            merchant = new BizMerchant();
        }

        merchant.setParentId(parentMerchant.getMerchId());
        merchant.setUserId(userId);
        merchant.setMerchCode(this.generateMerchCode("CY"));

        merchant.setMerchName(microMerchantDTO.getMerchName());
        merchant.setMerchType(microMerchantDTO.getMerchType());

        String scope = microMerchantDTO.getMerchBizScope();
        merchant.setMerchBizScope(StringUtils.isNotBlank(scope)?scope:"");

        String abbr = microMerchantDTO.getMerchAbbr();
        merchant.setMerchAbbr(StringUtils.isNotBlank(abbr)?abbr:"");

        String addr = microMerchantDTO.getMerchAddr();
        merchant.setMerchAddr(StringUtils.isNotBlank(addr)?addr:"");

        //省市区县编码
        String proviceCode = microMerchantDTO.getMerchProvince();
        String cityCode = microMerchantDTO.getMerchCity();
        String countyCode = microMerchantDTO.getMerchCounty();

        merchant.setVar1(StringUtils.isNotBlank(proviceCode)?proviceCode:"");
        merchant.setVar2(StringUtils.isNotBlank(cityCode)?cityCode:"");
        merchant.setVar3(StringUtils.isNotBlank(countyCode)?countyCode:"");
        String provinceName = "";
        if(StringUtils.isNotBlank(proviceCode)){
            provinceName = areaDictService.getById(Integer.parseInt(proviceCode)).getCname();
        }
        String cityName = "";
        if(StringUtils.isNotBlank(cityCode)){
            cityName = areaDictService.getById(Integer.parseInt(cityCode)).getCname();
        }
        String countyName = "";
        if(StringUtils.isNotBlank(countyCode)){
            countyName = areaDictService.getById(Integer.parseInt(countyCode)).getCname();
        }
        if(StringUtils.isNotBlank(provinceName) || StringUtils.isNotBlank(cityName) || StringUtils.isNotBlank(countyName)){
            merchant.setMerchRegion(provinceName+cityName+countyName);
        }

        Boolean success = this.saveOrUpdate(merchant);
        if(!success) return;

        Long merchId = merchant.getMerchId();

        if(merchId == null) return;

        BizMicroInfo microInfo = bizMicroInfoService.getBizMicroInfoByMerchId(merchId);
        if(microInfo == null){
            microInfo = new BizMicroInfo();
        }

        microInfo.setMerchId(merchId);

        microInfo.setMerchBank(microMerchantDTO.getMerchBank());
        microInfo.setMerchBankCard(microMerchantDTO.getMerchBankCard());
        microInfo.setMerchBankCardno(microMerchantDTO.getMerchBankCardno());

        //省市编码
        String bankProvinceCode = microMerchantDTO.getMerchProvince();
        String bankCityCode = microMerchantDTO.getMerchBankCity();

        //扩展字段存编码
        microInfo.setVar1(bankProvinceCode);
        microInfo.setVar2(bankCityCode);
        //存省市汉字
        microInfo.setMerchBankCity(provinceName+cityName);

        microInfo.setMerchBankMobile(microMerchantDTO.getMerchBankMobile());
        microInfo.setMerchIdcard(microMerchantDTO.getMerchIdcard());
        microInfo.setMerchIdcardName(microMerchantDTO.getMerchIdcardName());

        String duedata = microMerchantDTO.getMerchIdcardDuedate();
        microInfo.setMerchIdcardDuedate(StringUtils.isNotBlank(duedata)?duedata:"");

        microInfo.setMerchIdcardPositive(microMerchantDTO.getMerchIdcardPositive());
        microInfo.setMerchIdcardBack(microMerchantDTO.getMerchIdcardBack());

        String idcardHand = microMerchantDTO.getMerchIdcardHand();
        microInfo.setMerchIdcardHand(StringUtils.isNotBlank(idcardHand)?idcardHand:"");

        microInfoService.saveOrUpdate(microInfo);

        user.setNickName(microMerchantDTO.getMerchIdcardName());
        sysUserService.updateUserProfile(user);
    }

    @Override
    @Transactional
    public BizStatusContantEnum partnerRegister(PartnerInviteDTO partnerInviteDTO){
        String invitorCode = partnerInviteDTO.getInvitorUserId();
        if(!StringUtils.isNotBlank(invitorCode)) return BizStatusContantEnum.PARTNER_IS_NULL;
        String loginAccount = partnerInviteDTO.getMobile();
        if(!StringUtils.isNotBlank(loginAccount)) return BizStatusContantEnum.MOBILE_IS_NULL;

        String password = partnerInviteDTO.getPassword();
        if(!StringUtils.isNotBlank(password)) return BizStatusContantEnum.PASSWORD_IS_NULL;
//      String password = VerifyCodeUtils.getVerifyCode();
        //邀请人是否存在
        LambdaQueryWrapper<BizMerchant> lqw = Wrappers.lambdaQuery();
        lqw.eq(BizMerchant::getMerchCode, invitorCode);
        BizMerchant merchant = this.getOne(lqw);
        SysUser inviteUser = sysUserService.selectUserById(merchant.getUserId());
        if(inviteUser == null){
            return BizStatusContantEnum.PARTNER_REGISTER_INVITOR_ISNOTEXIST;
        }
        //判断是否已注册
        SysUser user = sysUserService.selectUserByUserName(loginAccount);
        if(user != null){
            return BizStatusContantEnum.PARTNER_REGISTER_FINISHED;
        }
        SysUser partnerUser = new SysUser();
        partnerUser.setUserName(loginAccount);
        //TODO：这里还需要加一层密码密文解密功能，即前端传输过来的是密码密文（加密算法协商解决），解密后保存到本地数据库
        partnerUser.setPassword(SecurityUtils.encryptPassword(password));
        partnerUser.setInviteUserId(inviteUser.getUserId());
        partnerUser.setRoleIds(new Long[]{102L});//默认角色-伙伴商户
        partnerUser.setPhonenumber(loginAccount);
        partnerUser.setDeptId(204L);//默认部门-合作伙伴
        partnerUser.setStatus("0");//伙伴默认启用
        partnerUser.setCreateBy(String.valueOf(inviteUser.getUserId()));
        int counts = sysUserService.insertUser(partnerUser);
        if(1 == counts){
            //伙伴注册成功后接口会自动将伙伴用户主键返回，利用该主键即可同时给新伙伴创建我的钱包账户
            BizWallet wallet = new BizWallet();
            wallet.setUserId(partnerUser.getUserId());
            //发送用户密码
            //smsService.sendVerifyCode(loginAccount,password,"partner register");
            String amount_money = BigDecimalUtil.getString(0.0); // 结算账户总额
            String secretKey = DESHelperUtil.getSecretKey();
            String secretMoney = DESHelperUtil.encrypt(secretKey,amount_money);
            wallet.setProfitAmount(secretMoney);
            wallet.setRewardAmount(secretMoney);
            wallet.setWalletAmount(secretMoney);
            wallet.setSecretKey(secretKey);
            // 通用积分  活动积分
            Long integral = 0L;
            String secretIntegral = DESHelperUtil.encrypt(secretKey,String.valueOf(integral));
            // 设置积分
            wallet.setIntegral(secretIntegral); // 通用积分
            wallet.setActivityIntegral(secretIntegral);// 活动积分
            walletService.save(wallet);
            return BizStatusContantEnum.PARTNER_REGISTER_SUCCESS;
        }
        return BizStatusContantEnum.PARTNER_REGISTER_FAIL;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public BankCardDTO getBankCardInfo(BankCardDTO bankCardDTO){
        BizMerchant merchant = this.getMerchantByUserId(bankCardDTO.getUserId());
        BizMicroInfo micro = microInfoService.getBizMicroInfoByMerchId(merchant.getMerchId());
        bankCardDTO.setIdCardName(micro.getMerchIdcardName());
        bankCardDTO.setIdCard(micro.getMerchIdcard());
        bankCardDTO.setBankCardNo(micro.getMerchBankCardno());
        bankCardDTO.setBankName(micro.getMerchBank());
        bankCardDTO.setBankCity(micro.getMerchBankCity());
        bankCardDTO.setBankReservedMobile(micro.getMerchBankMobile());
        // 银行卡照片url
        bankCardDTO.setBankCardImg(micro.getMerchBankCard());
        return bankCardDTO;
    }

    @Override
    @Transactional
    public BankCardDTO alterBankCardInfo(BankCardDTO bankCardDTO){
        BizMerchant merchant = this.getMerchantByUserId(bankCardDTO.getUserId());
        BizMicroInfo micro = microInfoService.getBizMicroInfoByMerchId(merchant.getMerchId());

        //micro.setMerchIdcardName(bankCardDTO.getIdCardName());
        //micro.setMerchIdcard(bankCardDTO.getIdCard());
        micro.setMerchBankCardno(bankCardDTO.getBankCardNo());
        micro.setMerchBank(bankCardDTO.getBankName());
        micro.setMerchBankCity(bankCardDTO.getBankCity());
        //micro.setMerchBankMobile(bankCardDTO.getBankReservedMobile());
        // 银行卡照片
        micro.setMerchBankCard(bankCardDTO.getBankCardImg());

        microInfoService.saveOrUpdate(micro);

        return bankCardDTO;
    }

    @Override
    @Transactional
    public void addMerchantTransaction(BizMerchTransDTO merchTransDTO) {
        BizMerchTransactions merchTransaction = new BizMerchTransactions();
        // ------------------------快钱支付接口提供字段-----------------------------
        merchTransaction.setTradeType(merchTransDTO.getTradeType()); // 交易类型
        merchTransaction.setBankAuthCode(merchTransDTO.getAuthCode()); // 银行授权码
        merchTransaction.setServiceEntryMode(merchTransDTO.getServiceEntryMode()); //输入模式
        merchTransaction.setReceiptType(merchTransDTO.getReceiptType()); // 产品类型
        merchTransaction.setStatus(merchTransDTO.getStatus());  // 交易状态
        merchTransaction.setDiscountRateFlag(merchTransDTO.getDiscountRateFlag());  // 优惠汇率
        merchTransaction.setIdTxn(merchTransDTO.getIdTxn()); // 交易编号
        merchTransaction.setErrorMsg(merchTransDTO.getErrorMsg()); // 错误信息
        merchTransaction.setCardType(merchTransDTO.getCardType()); // 卡类型
        merchTransaction.setSecondOrgId(merchTransDTO.getSecondOrgID()); // 二级机构号
        merchTransaction.setPhoneNo(merchTransDTO.getPhoneNo()); // 手机号
        merchTransaction.setTransAmount(Convert.toBigDecimal(merchTransDTO.getAmt())); // 交易金额
        //merchTransaction.setTransAmount(new BigDecimal(merchTransDTO.getAmt()));
        merchTransaction.setDeviceType(merchTransDTO.getDeviceType()); // 设备类型
        merchTransaction.setErrorCode(merchTransDTO.getErrorCode()); // 错误码
        merchTransaction.setPosCode(merchTransDTO.getSnCode());// 设备sn号
        merchTransaction.setUserName(merchTransDTO.getUserName()); // 商户实名姓名
        merchTransaction.setMerchFlagCode(merchTransDTO.getMerchantId());// 商户标识
        merchTransaction.setDirectlyOrgId(merchTransDTO.getDirectlyOrgID()); // 直属机构号
        merchTransaction.setOrderId(merchTransDTO.getOrderId()); // 外部订单号
        merchTransaction.setTransDate(merchTransDTO.getTxnTime());// 交易时间
        // -------------------------------------------------------------------------
        // -----------------------------未来可开发字段-------------------------------
        merchTransaction.setProductCode(merchTransDTO.getProductCode()); // 产品码
        merchTransaction.setIsStoreCashier(merchTransDTO.getIsStoreCachier()); // 店面收银交易标识
        merchTransaction.setS0PackageFlag(merchTransDTO.getS0PackageFlag()); //S0手续费套餐生效标识
        merchTransaction.setSpecialMode(merchTransDTO.getSpecialMode()); // 一机双模标识
        merchTransaction.setEsimFlag(merchTransDTO.getEsimFlg()); //e-sim卡服务费标识
        merchTransaction.setIdCardNo(merchTransDTO.getIdCardNo()); //用户实名身份证号
        merchTransaction.setAuthMerchantId(merchTransDTO.getAuthMerchantId()); //授权商户编号
        merchTransaction.setAuthTerminalId(merchTransDTO.getAuthTerminalId()); //授权终端编号
        merchTransaction.setTransactionStatus("1"); // 消费交易
        // 保存到数据库即可
        bizMerchTransactionsService.saveOrUpdate(merchTransaction);
    }

    @Override
    @Transactional
    public void cancelMerchantTransaction(BizCancelTransDTO merchTransDTO) {
        BizMerchTransactions merchTransaction = new BizMerchTransactions();
        // ------------------------快钱支付接口提供字段-----------------------------
        BigDecimal transAmount = Convert.toBigDecimal(merchTransDTO.getAmt());
        merchTransaction.setTransAmount(transAmount.negate());//值变为负值  -600.00
        merchTransaction.setOriginOutTraceNo(merchTransDTO.getOriginOutTraceNo()); // 原交易外部订单号
        merchTransaction.setIdTxn(merchTransDTO.getIdTxn()); // 交易编号
        merchTransaction.setMerchFlagCode(merchTransDTO.getMerchantId());  //商户标识
        merchTransaction.setOrderId(merchTransDTO.getOutTraceNo()); //外部订单号
        merchTransaction.setTransDate(merchTransDTO.getTxnTime()); //交易请求时间
        // -------------------------------------------------------------------------
        // -----------------------------未来可开发字段-------------------------------
        merchTransaction.setSecondOrgId(merchTransDTO.getSecondOrgID()); //二级机构号
        merchTransaction.setDirectlyOrgId(merchTransDTO.getDirectlyOrgID()); //直属机构号
        merchTransaction.setTransactionStatus("2"); // 撤销交易
        // 保存撤销记录信息
        bizMerchTransactionsService.saveOrUpdate(merchTransaction);
    }

    @Override
    @Transactional
    public void returnMerchantTransaction(BizReturnTransDTO merchTransDTO) {
        BizMerchTransactions merchTransaction = new BizMerchTransactions();
        // ------------------------快钱支付接口提供字段-----------------------------
        BigDecimal transAmount = Convert.toBigDecimal(merchTransDTO.getAmt());
        merchTransaction.setTransAmount(transAmount.negate());//值变为负值  -600.00
        merchTransaction.setOriginOutTraceNo(merchTransDTO.getOriginOutTraceNo()); // 原交易外部订单号
        merchTransaction.setIdTxn(merchTransDTO.getIdTxn()); // 交易编号
        merchTransaction.setMerchFlagCode(merchTransDTO.getMerchantId());  //商户标识
        merchTransaction.setOrderId(merchTransDTO.getOutTraceNo()); //外部订单号
        merchTransaction.setTransDate(merchTransDTO.getTxnTime()); //交易请求时间
        // -------------------------------------------------------------------------
        // -----------------------------未来可开发字段-------------------------------
        merchTransaction.setSecondOrgId(merchTransDTO.getSecondOrgID()); //二级机构号
        merchTransaction.setDirectlyOrgId(merchTransDTO.getDirectlyOrgID()); //直属机构号
        merchTransaction.setTransactionStatus("3"); // 退货交易
        // 保存撤销记录信息
        bizMerchTransactionsService.saveOrUpdate(merchTransaction);
    }

    /**
     * 商户下没有孩子得商户  就是我们需要的商户
     *
     * @param merchId
     * @return
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getMonthlyTotalMerchantByMerId(Long merchId) {
        // 拿到商户下的所有商户和合作伙伴
        String sql = "SELECT  merch_id FROM biz_merchant WHERE FIND_IN_SET(merch_id,findMerchSubNode(?))";
        List<Long> merchIds = jdbcTemplate.queryForList(sql, new Object[]{merchId}, Long.class);
        // 获得商户下的所有子商户
        String queryLeafNode = "select count(1) from biz_merchant where parent_id=?";
        Integer leafCounts = 0;
        for (Long merchantId : merchIds) {
            Integer count = jdbcTemplate.queryForObject(queryLeafNode, new Object[]{merchantId}, Integer.class);
            // 如果没有子节点  那么就说明是普通商户  散户
            if (count < 1) {
                leafCounts++;
            } else {
                continue;
            }
        }
        return leafCounts;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getMonthlyMyMerchantCounts(Long merchId) {
        // 拿到自己的孩子
        LocalDate localDate = LocalDate.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).substring(0, 7);
        String sql = "SELECT  merch_id FROM biz_merchant WHERE parent_id=? and create_time like ?";
        List<Long> merchIds = jdbcTemplate.queryForList(sql, new Object[]{merchId, formatedDate}, Long.class);
        // 获得孩子的孩子
        String queryLeafNode = "select count(1) from biz_merchant where parent_id=? and create_time like ?";
        Integer leafCounts = 0;
        for (Long merchantId : merchIds) {
            Integer count = jdbcTemplate.queryForObject(queryLeafNode, new Object[]{merchantId, formatedDate}, Integer.class);
            if (count < 1) {
                leafCounts++;
            } else {
                continue;
            }
        }
        return leafCounts;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getTtotalMerchAndPartnerCounts(Long merchId) {
        String sql = "SELECT  count(*) FROM biz_merchant WHERE FIND_IN_SET(merch_id,findMerchSubNode(?))";
        return jdbcTemplate.queryForObject(sql, new Object[]{merchId}, Integer.class)-1;
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<MerchantDict> selectPagedPartnerList(MerchantDTO merchantDTO) {
        Integer pageNo = merchantDTO.getPageNo();
        Integer pageSize = merchantDTO.getPageSize();
        if(pageNo !=null && !"".equals(pageNo) && pageSize!=null && !"".equals(pageSize)){
            Integer start = (merchantDTO.getPageNo()-1)*merchantDTO.getPageSize();
            // 页的开始值
            merchantDTO.setStart(start);
        }
        return bizMerchantMapper.selectPagedPartnerList(merchantDTO);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getMonthlyNewPartnerCount(Long merchId) {
        LocalDate localDate = LocalDate.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).substring(0, 7);
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setMerchId(merchId);
        homePageDTO.setYearMonth(formatedDate);
        return bizMerchantMapper.getMonthlyNewPartnerCount(homePageDTO);
    }

    @Override
    public Integer getMonthlyNewMerchCounts(Long merchId) {
        LocalDate localDate = LocalDate.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).substring(0, 7);
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setMerchId(merchId);
        homePageDTO.setYearMonth(formatedDate);
        return bizMerchantMapper.getMonthlyNewMerchCounts(homePageDTO);
    }

    @Override
    public Integer getTotalNewMerchCounts(Long merchantId) {
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setMerchId(merchantId);
        return bizMerchantMapper.getMonthlyNewMerchCounts(homePageDTO);
    }

    @Override
    public Integer getActivePostMachineCounts(Long merchantId, String month) {
        // 设置一下月
        String formatedDate = DateUtils.getCaculateYearAndMonth(month,"yyyy-MM");
        if(month==null || "".equals(month)){
            formatedDate = null;
        }
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setMerchId(merchantId);
        homePageDTO.setYearMonth(formatedDate);
        return bizMerchantMapper.getMonthlyNewMerchCounts(homePageDTO);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public Integer getTotalMerchantByMerId(Long merchId) {
        String sql = "SELECT COUNT(*) FROM biz_pos_machine_status_records r "
                +"WHERE r.records_type='2' AND r.sn_code IN "+
                "(SELECT pos_code FROM biz_pos_machine WHERE merch_id=?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{merchId}, Integer.class);
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<BizMerchant> getParentMerchByUserId(Long userId) {
        return bizMerchantMapper.selectParentMerchByUserId(userId);
    }

    @Override
    public HomePageDataDTO getHomePageDataDTO(Long merchId,Long userId) {
        String formatedDate = DateUtils.getCaculateYearAndMonth("","yyyy-MM");
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setMerchId(merchId);
        homePageDTO.setYearMonth(formatedDate);
        homePageDTO.setUserId(userId);
        return bizMerchantMapper.selectHomePageDataDTO(homePageDTO);
    }

    @Override
    public TransAmountDataDTO getTransAmountDataDTO(Long merchId, Long userId) {
        String formatedDate = DateUtils.getCaculateYearAndMonth("this","yyyy-MM");
        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setMerchId(merchId);
        homePageDTO.setYearMonth(formatedDate);
        homePageDTO.setUserId(userId);
        TransAmountDataDTO transAmountDataDTO = bizMerchantMapper.selectTransAmountDataDTO(homePageDTO);
        // 计算一下伙伴业绩   总业绩-个人业绩
        transAmountDataDTO.setPartnerTransAmount(transAmountDataDTO.getMonthlyTransAmount().subtract(transAmountDataDTO.getMerchantTransAmount()));
        return transAmountDataDTO;
    }
}

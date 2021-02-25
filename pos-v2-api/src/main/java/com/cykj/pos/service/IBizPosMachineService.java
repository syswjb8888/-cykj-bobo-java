package com.cykj.pos.service;

import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.BizPosMachine;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cykj.pos.domain.dto.MachineTransferDTO;
import com.cykj.pos.profit.dto.*;

import java.util.List;
import java.util.Map;

/**
 * 终端设备信息Service接口
 *
 * @author ruoyi
 * @date 2021-01-11
 */
public interface IBizPosMachineService extends IService<BizPosMachine> {

    /**
     * 查询列表
     */
    List<BizPosMachine> queryList(BizPosMachine bizPosMachine);

    /**
     * 查询所有商户终端机，并带条件查询
     * @param merchants
     * @param machine
     * @return
     */
    public List<BizPosMachine> getMachinesOfAllMerchantsWithQueryConditions(List<BizMerchant> merchants, BizPosMachine machine);

    /**
     * 根据商户id仅获取其本级所有的终端机器（含激活与未激活）
     * @param merchId
     * @return
     */
    public List<BizPosMachine> getPosMachineByMerchId(Long merchId);

    /**
     * 根据商户id获取本级和其下级所有的终端机器（含激活与未激活）
     * @param merchId
     * @return
     */
    public List<BizPosMachine> getPosMachinesByMerchId(Long merchId);

    /**
     * 根据商户id获取本级和其下级所有的终端机器（含激活与未激活）并根据筛选条件进行查询
     * @param merchId
     * @param terminalVo
     * @return
     */
    public List<BizPosMachine> getPosMachinesByMerchId(Long merchId, PosTerminalDTO terminalVo);

    /**
     * 根据商户id获取本级和其下级所有的终端机器（含激活与未激活）并根据筛选条件进行查询（带分页查询）
     * @param merchId
     * @param terminalVo
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<MachineTransferDTO> getPagePosMachinesByMerchId(Long merchId, PosTerminalDTO terminalVo, int pageNo, int pageSize);
    /**
     * 区间查询商户下的POS终端机
     * @param terminalDTO
     * @return
     */
    public List<BizPosMachine> getPosMachinesByInteval(PosTerminalDTO terminalDTO);
    /**
     * 获取商户总的终端数（含子商户终端数-激活与未激活终端数）
     * @param merchId
     * @return
     */
    public Long getPosMachineCountsByMerchId(Long merchId);

    /**
     * 获取商户已激活终端数（含子商户终端数-激活终端数）
     * @param merchId
     * @return
     */
    public Long getPosMachineActivatedCountsByMerchId(Long merchId);

    /**
     * 获取商户当月或上月已激活终端数（含子商户终端数-激活终端数）
     * @param merchId
     * @param thisMonth
     * @return
     */
    public Integer getPosMachineActivatedCountsByMerchId(Long merchId,String thisMonth);
    /**
     * 进行划拔回调操作并做相关操作记录
     * @param posIdList
     * @param merchantId
     * @param loginUser
     * @param operType
     * @return 操作成功终端数量
     */
    public Integer doTheOperations(List<Long> posIdList, Long merchantId, Long loginUser,Integer operType,Long orderId);

    /**
     * 筛选条件获取
     * @return
     */
    public List<Map<String,Object>> getPosFilterConditions();

    /**
     * 通过SN系列号获取机具信息
     * @param snCode
     * @return
     */
    public BizPosMachine getPosMachineBySnCode(String snCode);

    /**
     * 商户pos激活时，供第三方接口调用
     * @param terminalActivateDTO
     */
    public void posMachineActivate(TerminalActivateDTO terminalActivateDTO);

    /**
     * 商户POS绑定，供第三方接口调用
     * @param terminalBindDTO
     */
    public void posMachineBind(TerminalBindDTO terminalBindDTO);

    /**
     * 获取商户总得POS机器数量
      * @param merchantId
     * @return
     */
    Integer getPosMachineAllCountsByMerchId(Long merchantId);

    /**
     * 获得商户没有绑定和激活的POS机器数量
     * @param merchId
     * @return
     */
    public Long getPosMachineNotActivatedAndBindCountsByMerchId(Long merchId);
}

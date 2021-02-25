package com.cykj.web.controller.posbiz;

import com.cykj.common.annotation.Log;
import com.cykj.common.core.controller.BaseController;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.model.LoginUser;
import com.cykj.common.core.page.TableDataInfo;
import com.cykj.common.enums.BusinessType;
import com.cykj.common.utils.ServletUtils;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.framework.web.service.TokenService;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.BizPosMachine;
import com.cykj.pos.profit.dto.MerchantDTO;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.service.IBizPosMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 终端设备信息Controller
 *
 * @author cykj
 * @date 2021-01-11
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/terminal/posmachine" )
public class BizPosMachineController extends BaseController {

    private final IBizPosMachineService iBizPosMachineService;

    private final IBizMerchantService iBizMerchantService;

    private final TokenService tokenService;

    /**
     * 查询终端设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('terminal:posmachine:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizPosMachine bizPosMachine) {
        startPage();
        Long parentId = 1L;

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        BizMerchant parentMerchant = iBizMerchantService.getMerchantByUserId(loginUser.getUser().getUserId());
        if(parentMerchant != null){
            if(bizPosMachine.getMerchId() != null) {
                parentId = bizPosMachine.getMerchId();
            }else{
                parentId = parentMerchant.getMerchId();
            }
        }else if(bizPosMachine.getMerchId() != null) {
            parentId = bizPosMachine.getMerchId();
        }

        List<BizMerchant> merchants = iBizMerchantService.getAllSubNodeAndIncludeSelf(parentId);
        List<BizPosMachine> list = iBizPosMachineService.getMachinesOfAllMerchantsWithQueryConditions(merchants,bizPosMachine);
        return getDataTable(list);
    }

    /**
     * 导出终端设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('terminal:posmachine:export')" )
    @Log(title = "终端设备信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizPosMachine bizPosMachine) {
        List<BizPosMachine> list = iBizPosMachineService.queryList(bizPosMachine);
        ExcelUtil<BizPosMachine> util = new ExcelUtil<BizPosMachine>(BizPosMachine.class);
        return util.exportExcel(list, "posmachine" );
    }

    /**
     * 获取终端设备信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('terminal:posmachine:query')" )
    @GetMapping(value = "/{posId}" )
    public AjaxResult getInfo(@PathVariable("posId" ) Long posId) {
        return AjaxResult.success(iBizPosMachineService.getById(posId));
    }

    /**
     * 新增终端设备信息
     */
    @PreAuthorize("@ss.hasPermi('terminal:posmachine:add')" )
    @Log(title = "终端设备信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPosMachine bizPosMachine) {
        return toAjax(iBizPosMachineService.save(bizPosMachine) ? 1 : 0);
    }

    /**
     * 修改终端设备信息
     */
    @PreAuthorize("@ss.hasPermi('terminal:posmachine:edit')" )
    @Log(title = "终端设备信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPosMachine bizPosMachine) {
        return toAjax(iBizPosMachineService.updateById(bizPosMachine) ? 1 : 0);
    }

    /**
     * 删除终端设备信息
     */
    @PreAuthorize("@ss.hasPermi('terminal:posmachine:remove')" )
    @Log(title = "终端设备信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{posIds}" )
    public AjaxResult remove(@PathVariable Long[] posIds) {
        return toAjax(iBizPosMachineService.removeByIds(Arrays.asList(posIds)) ? 1 : 0);
    }

    @PreAuthorize("@ss.hasPermi('terminal:posmachine:allocate')" )
    @Log(title = "终端设备操作" , businessType = BusinessType.TERMINAL_ALLOCATE)
    @PostMapping("/allocation" )
    public AjaxResult posMachineAllcate(@RequestBody MerchantDTO merchantDTO) {
        AjaxResult ajax = AjaxResult.success("划拔成功");
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        List<Long> posIdList = merchantDTO.getPosIds();
        Long merchId = merchantDTO.getMerchId();
        iBizPosMachineService.doTheOperations(posIdList,merchId,loginUser.getUser().getUserId(),1,null);
        return ajax;
    }
    @PreAuthorize("@ss.hasPermi('terminal:posmachine:adjust')" )
    @Log(title = "终端设备操作" , businessType = BusinessType.TERMINAL_ADJUST)
    @PostMapping("/adjustion" )
    public AjaxResult posMachineAdjust(@RequestBody MerchantDTO merchantDTO) {
        AjaxResult ajax = AjaxResult.success("回调成功");
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        BizMerchant parentMerchant = iBizMerchantService.getMerchantByUserId(loginUser.getUser().getUserId());
        Long merchId = 1L;
        if(parentMerchant != null){
            merchId = parentMerchant.getMerchId();
        }
        iBizPosMachineService.doTheOperations(merchantDTO.getPosIds(),merchId,loginUser.getUser().getUserId(),2,null);
        return ajax;
    }
}

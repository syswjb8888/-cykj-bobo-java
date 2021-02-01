package com.cykj.web.controller.posbiz;

import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cykj.common.annotation.Log;
import com.cykj.common.core.controller.BaseController;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.enums.BusinessType;
import com.cykj.pos.domain.BizMicroInfo;
import com.cykj.pos.service.IBizMicroInfoService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 报件小微商户信息Controller
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/merchant/micro" )
public class BizMicroInfoController extends BaseController {

    private final IBizMicroInfoService iBizMicroInfoService;

    /**
     * 查询报件小微商户信息列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:micro:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizMicroInfo bizMicroInfo) {
        startPage();
        List<BizMicroInfo> list = iBizMicroInfoService.queryList(bizMicroInfo);
        return getDataTable(list);
    }

    /**
     * 导出报件小微商户信息列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:micro:export')" )
    @Log(title = "报件小微商户信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizMicroInfo bizMicroInfo) {
        List<BizMicroInfo> list = iBizMicroInfoService.queryList(bizMicroInfo);
        ExcelUtil<BizMicroInfo> util = new ExcelUtil<BizMicroInfo>(BizMicroInfo.class);
        return util.exportExcel(list, "micro" );
    }

    /**
     * 获取报件小微商户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:micro:query')" )
    @GetMapping(value = "/{microId}" )
    public AjaxResult getInfo(@PathVariable("microId" ) Long microId) {
        return AjaxResult.success(iBizMicroInfoService.getById(microId));
    }

    /**
     * 新增报件小微商户信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:micro:add')" )
    @Log(title = "报件小微商户信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizMicroInfo bizMicroInfo) {
        return toAjax(iBizMicroInfoService.save(bizMicroInfo) ? 1 : 0);
    }

    /**
     * 修改报件小微商户信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:micro:edit')" )
    @Log(title = "报件小微商户信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizMicroInfo bizMicroInfo) {
        return toAjax(iBizMicroInfoService.updateById(bizMicroInfo) ? 1 : 0);
    }

    /**
     * 删除报件小微商户信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:micro:remove')" )
    @Log(title = "报件小微商户信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{microIds}" )
    public AjaxResult remove(@PathVariable Long[] microIds) {
        return toAjax(iBizMicroInfoService.removeByIds(Arrays.asList(microIds)) ? 1 : 0);
    }
}

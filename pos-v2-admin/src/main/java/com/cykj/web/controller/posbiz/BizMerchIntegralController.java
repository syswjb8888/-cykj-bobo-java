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
import com.cykj.pos.domain.BizMerchIntegral;
import com.cykj.pos.service.IBizMerchIntegralService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 积分管理Controller
 *
 * @author weijianbo
 * @date 2021-02-19
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/merchant/integral" )
public class BizMerchIntegralController extends BaseController {

    private final IBizMerchIntegralService iBizMerchIntegralService;

    /**
     * 查询积分管理列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:integral:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizMerchIntegral bizMerchIntegral) {
        startPage();
        List<BizMerchIntegral> list = iBizMerchIntegralService.queryList(bizMerchIntegral);
        return getDataTable(list);
    }

    /**
     * 导出积分管理列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:integral:export')" )
    @Log(title = "积分管理" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizMerchIntegral bizMerchIntegral) {
        List<BizMerchIntegral> list = iBizMerchIntegralService.queryList(bizMerchIntegral);
        ExcelUtil<BizMerchIntegral> util = new ExcelUtil<BizMerchIntegral>(BizMerchIntegral.class);
        return util.exportExcel(list, "integral" );
    }

    /**
     * 获取积分管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:integral:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iBizMerchIntegralService.getById(id));
    }

    /**
     * 新增积分管理
     */
    @PreAuthorize("@ss.hasPermi('merchant:integral:add')" )
    @Log(title = "积分管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizMerchIntegral bizMerchIntegral) {
        return toAjax(iBizMerchIntegralService.save(bizMerchIntegral) ? 1 : 0);
    }

    /**
     * 修改积分管理
     */
    @PreAuthorize("@ss.hasPermi('merchant:integral:edit')" )
    @Log(title = "积分管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizMerchIntegral bizMerchIntegral) {
        return toAjax(iBizMerchIntegralService.updateById(bizMerchIntegral) ? 1 : 0);
    }

    /**
     * 删除积分管理
     */
    @PreAuthorize("@ss.hasPermi('merchant:integral:remove')" )
    @Log(title = "积分管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iBizMerchIntegralService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}

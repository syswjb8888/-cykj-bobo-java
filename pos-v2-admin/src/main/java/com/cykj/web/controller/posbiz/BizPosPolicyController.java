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
import com.cykj.pos.domain.BizPosPolicy;
import com.cykj.pos.service.IBizPosPolicyService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 政策管理Controller
 *
 * @author weijianbo
 * @date 2021-02-18
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/terminal/policy" )
public class BizPosPolicyController extends BaseController {

    private final IBizPosPolicyService iBizPosPolicyService;

    /**
     * 查询政策管理列表
     */
    @PreAuthorize("@ss.hasPermi('terminal:policy:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizPosPolicy bizPosPolicy) {
        startPage();
        List<BizPosPolicy> list = iBizPosPolicyService.queryList(bizPosPolicy);
        return getDataTable(list);
    }

    /**
     * 导出政策管理列表
     */
    @PreAuthorize("@ss.hasPermi('terminal:policy:export')" )
    @Log(title = "政策管理" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizPosPolicy bizPosPolicy) {
        List<BizPosPolicy> list = iBizPosPolicyService.queryList(bizPosPolicy);
        ExcelUtil<BizPosPolicy> util = new ExcelUtil<BizPosPolicy>(BizPosPolicy.class);
        return util.exportExcel(list, "policy" );
    }

    /**
     * 获取政策管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('terminal:policy:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iBizPosPolicyService.getById(id));
    }

    /**
     * 新增政策管理
     */
    @PreAuthorize("@ss.hasPermi('terminal:policy:add')" )
    @Log(title = "政策管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPosPolicy bizPosPolicy) {
        return toAjax(iBizPosPolicyService.save(bizPosPolicy) ? 1 : 0);
    }

    /**
     * 修改政策管理
     */
    @PreAuthorize("@ss.hasPermi('terminal:policy:edit')" )
    @Log(title = "政策管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPosPolicy bizPosPolicy) {
        return toAjax(iBizPosPolicyService.updateById(bizPosPolicy) ? 1 : 0);
    }

    /**
     * 删除政策管理
     */
    @PreAuthorize("@ss.hasPermi('terminal:policy:remove')" )
    @Log(title = "政策管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iBizPosPolicyService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}

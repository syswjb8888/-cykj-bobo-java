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
import com.cykj.pos.domain.BizEnterpriseInfo;
import com.cykj.pos.service.IBizEnterpriseInfoService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 企业商户报件信息Controller
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/merchant/enterprise" )
public class BizEnterpriseInfoController extends BaseController {

    private final IBizEnterpriseInfoService iBizEnterpriseInfoService;

    /**
     * 查询企业商户报件信息列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:enterprise:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizEnterpriseInfo bizEnterpriseInfo) {
        startPage();
        List<BizEnterpriseInfo> list = iBizEnterpriseInfoService.queryList(bizEnterpriseInfo);
        return getDataTable(list);
    }

    /**
     * 导出企业商户报件信息列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:enterprise:export')" )
    @Log(title = "企业商户报件信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizEnterpriseInfo bizEnterpriseInfo) {
        List<BizEnterpriseInfo> list = iBizEnterpriseInfoService.queryList(bizEnterpriseInfo);
        ExcelUtil<BizEnterpriseInfo> util = new ExcelUtil<BizEnterpriseInfo>(BizEnterpriseInfo.class);
        return util.exportExcel(list, "enterprise" );
    }

    /**
     * 获取企业商户报件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:enterprise:query')" )
    @GetMapping(value = "/{entId}" )
    public AjaxResult getInfo(@PathVariable("entId" ) Long entId) {
        return AjaxResult.success(iBizEnterpriseInfoService.getById(entId));
    }

    /**
     * 新增企业商户报件信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:enterprise:add')" )
    @Log(title = "企业商户报件信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizEnterpriseInfo bizEnterpriseInfo) {
        return toAjax(iBizEnterpriseInfoService.save(bizEnterpriseInfo) ? 1 : 0);
    }

    /**
     * 修改企业商户报件信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:enterprise:edit')" )
    @Log(title = "企业商户报件信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizEnterpriseInfo bizEnterpriseInfo) {
        return toAjax(iBizEnterpriseInfoService.updateById(bizEnterpriseInfo) ? 1 : 0);
    }

    /**
     * 删除企业商户报件信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:enterprise:remove')" )
    @Log(title = "企业商户报件信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{entIds}" )
    public AjaxResult remove(@PathVariable Long[] entIds) {
        return toAjax(iBizEnterpriseInfoService.removeByIds(Arrays.asList(entIds)) ? 1 : 0);
    }
}

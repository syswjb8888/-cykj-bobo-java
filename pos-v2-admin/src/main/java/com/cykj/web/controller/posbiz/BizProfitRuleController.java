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
import com.cykj.pos.domain.BizProfitRule;
import com.cykj.pos.service.IBizProfitRuleService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 分润规则制订Controller
 *
 * @author ningbingwu
 * @date 2021-01-08
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/profit/profitRule" )
public class BizProfitRuleController extends BaseController {

    private final IBizProfitRuleService iBizProfitRuleService;

    /**
     * 查询分润规则制订列表
     */
    @PreAuthorize("@ss.hasPermi('profit:profitRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizProfitRule bizProfitRule) {
        startPage();
        List<BizProfitRule> list = iBizProfitRuleService.queryList(bizProfitRule);
        return getDataTable(list);
    }

    /**
     * 导出分润规则制订列表
     */
    @PreAuthorize("@ss.hasPermi('profit:profitRule:export')" )
    @Log(title = "分润规则制订" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizProfitRule bizProfitRule) {
        List<BizProfitRule> list = iBizProfitRuleService.queryList(bizProfitRule);
        ExcelUtil<BizProfitRule> util = new ExcelUtil<BizProfitRule>(BizProfitRule.class);
        return util.exportExcel(list, "profitRule" );
    }

    /**
     * 获取分润规则制订详细信息
     */
    @PreAuthorize("@ss.hasPermi('profit:profitRule:query')" )
    @GetMapping(value = "/{profitId}" )
    public AjaxResult getInfo(@PathVariable("profitId" ) Long profitId) {
        return AjaxResult.success(iBizProfitRuleService.getById(profitId));
    }

    /**
     * 新增分润规则制订
     */
    @PreAuthorize("@ss.hasPermi('profit:profitRule:add')" )
    @Log(title = "分润规则制订" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProfitRule bizProfitRule) {
        return toAjax(iBizProfitRuleService.save(bizProfitRule) ? 1 : 0);
    }

    /**
     * 修改分润规则制订
     */
    @PreAuthorize("@ss.hasPermi('profit:profitRule:edit')" )
    @Log(title = "分润规则制订" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProfitRule bizProfitRule) {
        return toAjax(iBizProfitRuleService.updateById(bizProfitRule) ? 1 : 0);
    }

    /**
     * 删除分润规则制订
     */
    @PreAuthorize("@ss.hasPermi('profit:profitRule:remove')" )
    @Log(title = "分润规则制订" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{profitIds}" )
    public AjaxResult remove(@PathVariable Long[] profitIds) {
        return toAjax(iBizProfitRuleService.removeByIds(Arrays.asList(profitIds)) ? 1 : 0);
    }
}

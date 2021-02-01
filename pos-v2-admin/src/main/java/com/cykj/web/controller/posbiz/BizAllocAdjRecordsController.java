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
import com.cykj.pos.domain.BizAllocAdjRecords;
import com.cykj.pos.service.IBizAllocAdjRecordsService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 划拔回调记录Controller
 *
 * @author ningbingwu
 * @date 2021-01-15
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/operation/records" )
public class BizAllocAdjRecordsController extends BaseController {

    private final IBizAllocAdjRecordsService iBizAllocAdjRecordsService;

    /**
     * 查询划拔回调记录列表
     */
    @PreAuthorize("@ss.hasPermi('operation:records:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizAllocAdjRecords bizAllocAdjRecords) {
        startPage();
        List<BizAllocAdjRecords> list = iBizAllocAdjRecordsService.queryList(bizAllocAdjRecords);
        return getDataTable(list);
    }

    /**
     * 导出划拔回调记录列表
     */
    @PreAuthorize("@ss.hasPermi('operation:records:export')" )
    @Log(title = "划拔回调记录" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizAllocAdjRecords bizAllocAdjRecords) {
        List<BizAllocAdjRecords> list = iBizAllocAdjRecordsService.queryList(bizAllocAdjRecords);
        ExcelUtil<BizAllocAdjRecords> util = new ExcelUtil<BizAllocAdjRecords>(BizAllocAdjRecords.class);
        return util.exportExcel(list, "records" );
    }

    /**
     * 获取划拔回调记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('operation:records:query')" )
    @GetMapping(value = "/{operId}" )
    public AjaxResult getInfo(@PathVariable("operId" ) Long operId) {
        return AjaxResult.success(iBizAllocAdjRecordsService.getById(operId));
    }

    /**
     * 新增划拔回调记录
     */
    @PreAuthorize("@ss.hasPermi('operation:records:add')" )
    @Log(title = "划拔回调记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizAllocAdjRecords bizAllocAdjRecords) {
        return toAjax(iBizAllocAdjRecordsService.save(bizAllocAdjRecords) ? 1 : 0);
    }

    /**
     * 修改划拔回调记录
     */
    @PreAuthorize("@ss.hasPermi('operation:records:edit')" )
    @Log(title = "划拔回调记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizAllocAdjRecords bizAllocAdjRecords) {
        return toAjax(iBizAllocAdjRecordsService.updateById(bizAllocAdjRecords) ? 1 : 0);
    }

    /**
     * 删除划拔回调记录
     */
    @PreAuthorize("@ss.hasPermi('operation:records:remove')" )
    @Log(title = "划拔回调记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{operIds}" )
    public AjaxResult remove(@PathVariable Long[] operIds) {
        return toAjax(iBizAllocAdjRecordsService.removeByIds(Arrays.asList(operIds)) ? 1 : 0);
    }
}

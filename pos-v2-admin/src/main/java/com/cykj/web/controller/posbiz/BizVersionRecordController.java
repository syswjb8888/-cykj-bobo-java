package com.cykj.web.controller.posbiz ;

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
import com.cykj.pos.domain.BizVersionRecord;
import com.cykj.pos.service.IBizVersionRecordService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 版本管理Controller
 *
 * @author weijianbo
 * @date 2021-02-06
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/version/record" )
public class BizVersionRecordController extends BaseController {

    private final IBizVersionRecordService iBizVersionRecordService;

    /**
     * 查询版本管理列表
     */
    @PreAuthorize("@ss.hasPermi('version:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizVersionRecord bizVersionRecord) {
        startPage();
        List<BizVersionRecord> list = iBizVersionRecordService.queryList(bizVersionRecord);
        return getDataTable(list);
    }

    /**
     * 导出版本管理列表
     */
    @PreAuthorize("@ss.hasPermi('version:record:export')" )
    @Log(title = "版本管理" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizVersionRecord bizVersionRecord) {
        List<BizVersionRecord> list = iBizVersionRecordService.queryList(bizVersionRecord);
        ExcelUtil<BizVersionRecord> util = new ExcelUtil<BizVersionRecord>(BizVersionRecord.class);
        return util.exportExcel(list, "record" );
    }

    /**
     * 获取版本管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('version:record:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iBizVersionRecordService.getById(id));
    }

    /**
     * 新增版本管理
     */
    @PreAuthorize("@ss.hasPermi('version:record:add')" )
    @Log(title = "版本管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizVersionRecord bizVersionRecord) {
        return toAjax(iBizVersionRecordService.save(bizVersionRecord) ? 1 : 0);
    }

    /**
     * 修改版本管理
     */
    @PreAuthorize("@ss.hasPermi('version:record:edit')" )
    @Log(title = "版本管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizVersionRecord bizVersionRecord) {
        return toAjax(iBizVersionRecordService.updateById(bizVersionRecord) ? 1 : 0);
    }

    /**
     * 删除版本管理
     */
    @PreAuthorize("@ss.hasPermi('version:record:remove')" )
    @Log(title = "版本管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iBizVersionRecordService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}

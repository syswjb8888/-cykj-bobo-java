package com.cykj.web.controller.posbiz;

import com.cykj.common.annotation.Log;
import com.cykj.common.core.controller.BaseController;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.page.TableDataInfo;
import com.cykj.common.enums.BusinessType;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.pos.domain.BizMessageRecords;
import com.cykj.pos.service.IBizMessageRecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 用户消息Controller
 *
 * @author weijianbo
 * @date 2021-02-21
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/message/offline" )
public class BizMessageRecordsController extends BaseController {

    private final IBizMessageRecordsService iBizMessageRecordsService;

    /**
     * 查询用户消息列表
     */
    @PreAuthorize("@ss.hasPermi('message:offline:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizMessageRecords bizMessageRecords) {
        startPage();
        List<BizMessageRecords> list = iBizMessageRecordsService.queryList(bizMessageRecords);
        return getDataTable(list);
    }

    /**
     * 导出用户消息列表
     */
    @PreAuthorize("@ss.hasPermi('message:offline:export')" )
    @Log(title = "离线用户消息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizMessageRecords bizMessageRecords) {
        List<BizMessageRecords> list = iBizMessageRecordsService.queryList(bizMessageRecords);
        ExcelUtil<BizMessageRecords> util = new ExcelUtil<BizMessageRecords>(BizMessageRecords.class);
        return util.exportExcel(list, "offline" );
    }

    /**
     * 获取用户消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:offline:query')" )
    @GetMapping(value = "/{msgId}" )
    public AjaxResult getInfo(@PathVariable("msgId" ) Long msgId) {
        return AjaxResult.success(iBizMessageRecordsService.getById(msgId));
    }

    /**
     * 新增用户消息
     */
    @PreAuthorize("@ss.hasPermi('message:offline:add')" )
    @Log(title = "离线用户消息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizMessageRecords bizMessageRecords) {
        return toAjax(iBizMessageRecordsService.save(bizMessageRecords) ? 1 : 0);
    }

    /**
     * 修改用户消息
     */
    @PreAuthorize("@ss.hasPermi('message:offline:edit')" )
    @Log(title = "离线用户消息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizMessageRecords bizMessageRecords) {
        return toAjax(iBizMessageRecordsService.updateById(bizMessageRecords) ? 1 : 0);
    }

    /**
     * 删除用户消息
     */
    @PreAuthorize("@ss.hasPermi('message:offline:remove')" )
    @Log(title = "用户消息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{msgIds}" )
    public AjaxResult remove(@PathVariable Long[] msgIds) {
        return toAjax(iBizMessageRecordsService.removeByIds(Arrays.asList(msgIds)) ? 1 : 0);
    }
}

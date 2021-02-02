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
import com.cykj.pos.domain.BizPosMachineStatusRecords;
import com.cykj.pos.service.IBizPosMachineStatusRecordsService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 终端设备状态记录Controller
 *
 * @author weijianbo
 * @date 2021-02-02
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/posmachine/statusrecords" )
public class BizPosMachineStatusRecordsController extends BaseController {

    private final IBizPosMachineStatusRecordsService iBizPosMachineStatusRecordsService;

    /**
     * 查询终端设备状态记录列表
     */
    @PreAuthorize("@ss.hasPermi('posmachine:statusrecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizPosMachineStatusRecords bizPosMachineStatusRecords) {
        startPage();
        List<BizPosMachineStatusRecords> list = iBizPosMachineStatusRecordsService.queryList(bizPosMachineStatusRecords);
        return getDataTable(list);
    }

    /**
     * 导出终端设备状态记录列表
     */
    @PreAuthorize("@ss.hasPermi('posmachine:statusrecords:export')" )
    @Log(title = "终端设备状态记录" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizPosMachineStatusRecords bizPosMachineStatusRecords) {
        List<BizPosMachineStatusRecords> list = iBizPosMachineStatusRecordsService.queryList(bizPosMachineStatusRecords);
        ExcelUtil<BizPosMachineStatusRecords> util = new ExcelUtil<BizPosMachineStatusRecords>(BizPosMachineStatusRecords.class);
        return util.exportExcel(list, "statusrecords" );
    }

    /**
     * 获取终端设备状态记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('posmachine:statusrecords:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iBizPosMachineStatusRecordsService.getById(id));
    }

    /**
     * 新增终端设备状态记录
     */
    @PreAuthorize("@ss.hasPermi('posmachine:statusrecords:add')" )
    @Log(title = "终端设备状态记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPosMachineStatusRecords bizPosMachineStatusRecords) {
        return toAjax(iBizPosMachineStatusRecordsService.save(bizPosMachineStatusRecords) ? 1 : 0);
    }

    /**
     * 修改终端设备状态记录
     */
    @PreAuthorize("@ss.hasPermi('posmachine:statusrecords:edit')" )
    @Log(title = "终端设备状态记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPosMachineStatusRecords bizPosMachineStatusRecords) {
        return toAjax(iBizPosMachineStatusRecordsService.updateById(bizPosMachineStatusRecords) ? 1 : 0);
    }

    /**
     * 删除终端设备状态记录
     */
    @PreAuthorize("@ss.hasPermi('posmachine:statusrecords:remove')" )
    @Log(title = "终端设备状态记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iBizPosMachineStatusRecordsService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}

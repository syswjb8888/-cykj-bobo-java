package com.cykj.web.controller.posbiz;

import com.cykj.common.annotation.Log;
import com.cykj.common.core.controller.BaseController;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.page.TableDataInfo;
import com.cykj.common.enums.BusinessType;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.pos.domain.BizCashoutRecords;
import com.cykj.pos.service.IBizCashoutRecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 提现记录Controller
 *
 * @author ningbingwu
 * @date 2021-01-27
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/cashout/records" )
public class BizCashoutRecordsController extends BaseController {

    private final IBizCashoutRecordsService iBizCashoutRecordsService;

    /**
     * 查询提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('cashout:records:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizCashoutRecords bizCashoutRecords) {
        startPage();
        List<BizCashoutRecords> list = iBizCashoutRecordsService.queryList(bizCashoutRecords);
        return getDataTable(list);
    }

    /**
     * 导出提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('cashout:records:export')" )
    @Log(title = "提现记录" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizCashoutRecords bizCashoutRecords) {
        List<BizCashoutRecords> list = iBizCashoutRecordsService.queryList(bizCashoutRecords);
        ExcelUtil<BizCashoutRecords> util = new ExcelUtil<BizCashoutRecords>(BizCashoutRecords.class);
        return util.exportExcel(list, "records" );
    }

    /**
     * 获取提现记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('cashout:records:query')" )
    @GetMapping(value = "/{cashoutId}" )
    public AjaxResult getInfo(@PathVariable("cashoutId" ) Long cashoutId) {
        return AjaxResult.success(iBizCashoutRecordsService.getById(cashoutId));
    }

    /**
     * 新增提现记录
     */
    @PreAuthorize("@ss.hasPermi('cashout:records:add')" )
    @Log(title = "提现记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizCashoutRecords bizCashoutRecords) {
        return toAjax(iBizCashoutRecordsService.save(bizCashoutRecords) ? 1 : 0);
    }

    /**
     * 修改提现记录
     */
    @PreAuthorize("@ss.hasPermi('cashout:records:edit')" )
    @Log(title = "提现记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizCashoutRecords bizCashoutRecords) {
        return toAjax(iBizCashoutRecordsService.updateById(bizCashoutRecords) ? 1 : 0);
    }

    /**
     * 删除提现记录
     */
    @PreAuthorize("@ss.hasPermi('cashout:records:remove')" )
    @Log(title = "提现记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{cashoutIds}" )
    public AjaxResult remove(@PathVariable Long[] cashoutIds) {
        return toAjax(iBizCashoutRecordsService.removeByIds(Arrays.asList(cashoutIds)) ? 1 : 0);
    }
}

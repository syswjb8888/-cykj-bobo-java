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
import com.cykj.pos.domain.BizMerchBill;
import com.cykj.pos.service.IBizMerchBillService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 账单信息Controller
 *
 * @author weijianbo
 * @date 2021-02-06
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/merchant/bill" )
public class BizMerchBillController extends BaseController {

    private final IBizMerchBillService iBizMerchBillService;

    /**
     * 查询账单信息列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:bill:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizMerchBill bizMerchBill) {
        startPage();
        List<BizMerchBill> list = iBizMerchBillService.queryList(bizMerchBill);
        return getDataTable(list);
    }

    /**
     * 导出账单信息列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:bill:export')" )
    @Log(title = "账单信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizMerchBill bizMerchBill) {
        List<BizMerchBill> list = iBizMerchBillService.queryList(bizMerchBill);
        ExcelUtil<BizMerchBill> util = new ExcelUtil<BizMerchBill>(BizMerchBill.class);
        return util.exportExcel(list, "bill" );
    }

    /**
     * 获取账单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:bill:query')" )
    @GetMapping(value = "/{billId}" )
    public AjaxResult getInfo(@PathVariable("billId" ) Long billId) {
        return AjaxResult.success(iBizMerchBillService.getById(billId));
    }

    /**
     * 新增账单信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:bill:add')" )
    @Log(title = "账单信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizMerchBill bizMerchBill) {
        return toAjax(iBizMerchBillService.save(bizMerchBill) ? 1 : 0);
    }

    /**
     * 修改账单信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:bill:edit')" )
    @Log(title = "账单信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizMerchBill bizMerchBill) {
        return toAjax(iBizMerchBillService.updateById(bizMerchBill) ? 1 : 0);
    }

    /**
     * 删除账单信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:bill:remove')" )
    @Log(title = "账单信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{billIds}" )
    public AjaxResult remove(@PathVariable Long[] billIds) {
        return toAjax(iBizMerchBillService.removeByIds(Arrays.asList(billIds)) ? 1 : 0);
    }
}

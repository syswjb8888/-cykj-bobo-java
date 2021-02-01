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
import com.cykj.pos.domain.BizMerchTransactions;
import com.cykj.pos.service.IBizMerchTransactionsService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 商户交易记录Controller
 *
 * @author ningbingwu
 * @date 2021-01-29
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/merchant/transactions" )
public class BizMerchTransactionsController extends BaseController {

    private final IBizMerchTransactionsService iBizMerchTransactionsService;

    /**
     * 查询商户交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:transactions:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizMerchTransactions bizMerchTransactions) {
        startPage();
        List<BizMerchTransactions> list = iBizMerchTransactionsService.queryList(bizMerchTransactions);
        return getDataTable(list);
    }

    /**
     * 导出商户交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:transactions:export')" )
    @Log(title = "商户交易记录" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizMerchTransactions bizMerchTransactions) {
        List<BizMerchTransactions> list = iBizMerchTransactionsService.queryList(bizMerchTransactions);
        ExcelUtil<BizMerchTransactions> util = new ExcelUtil<BizMerchTransactions>(BizMerchTransactions.class);
        return util.exportExcel(list, "transactions" );
    }

    /**
     * 获取商户交易记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:transactions:query')" )
    @GetMapping(value = "/{trnId}" )
    public AjaxResult getInfo(@PathVariable("trnId" ) Long trnId) {
        return AjaxResult.success(iBizMerchTransactionsService.getById(trnId));
    }

    /**
     * 新增商户交易记录
     */
    @PreAuthorize("@ss.hasPermi('merchant:transactions:add')" )
    @Log(title = "商户交易记录" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizMerchTransactions bizMerchTransactions) {
        return toAjax(iBizMerchTransactionsService.save(bizMerchTransactions) ? 1 : 0);
    }

    /**
     * 修改商户交易记录
     */
    @PreAuthorize("@ss.hasPermi('merchant:transactions:edit')" )
    @Log(title = "商户交易记录" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizMerchTransactions bizMerchTransactions) {
        return toAjax(iBizMerchTransactionsService.updateById(bizMerchTransactions) ? 1 : 0);
    }

    /**
     * 删除商户交易记录
     */
    @PreAuthorize("@ss.hasPermi('merchant:transactions:remove')" )
    @Log(title = "商户交易记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{trnIds}" )
    public AjaxResult remove(@PathVariable Long[] trnIds) {
        return toAjax(iBizMerchTransactionsService.removeByIds(Arrays.asList(trnIds)) ? 1 : 0);
    }
}

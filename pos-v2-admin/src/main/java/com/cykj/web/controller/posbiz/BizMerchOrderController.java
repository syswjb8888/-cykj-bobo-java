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
import com.cykj.pos.domain.BizMerchOrder;
import com.cykj.pos.service.IBizMerchOrderService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 订单Controller
 *
 * @author weijianbo
 * @date 2021-02-24
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/merchant/order" )
public class BizMerchOrderController extends BaseController {

    private final IBizMerchOrderService iBizMerchOrderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizMerchOrder bizMerchOrder) {
        startPage();
        List<BizMerchOrder> list = iBizMerchOrderService.queryList(bizMerchOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:order:export')" )
    @Log(title = "订单" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizMerchOrder bizMerchOrder) {
        List<BizMerchOrder> list = iBizMerchOrderService.queryList(bizMerchOrder);
        ExcelUtil<BizMerchOrder> util = new ExcelUtil<BizMerchOrder>(BizMerchOrder.class);
        return util.exportExcel(list, "order" );
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:order:query')" )
    @GetMapping(value = "/{orderId}" )
    public AjaxResult getInfo(@PathVariable("orderId" ) Long orderId) {
        return AjaxResult.success(iBizMerchOrderService.getById(orderId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('merchant:order:add')" )
    @Log(title = "订单" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizMerchOrder bizMerchOrder) {
        return toAjax(iBizMerchOrderService.save(bizMerchOrder) ? 1 : 0);
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('merchant:order:edit')" )
    @Log(title = "订单" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizMerchOrder bizMerchOrder) {
        return toAjax(iBizMerchOrderService.updateById(bizMerchOrder) ? 1 : 0);
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('merchant:order:remove')" )
    @Log(title = "订单" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}" )
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(iBizMerchOrderService.removeByIds(Arrays.asList(orderIds)) ? 1 : 0);
    }
}

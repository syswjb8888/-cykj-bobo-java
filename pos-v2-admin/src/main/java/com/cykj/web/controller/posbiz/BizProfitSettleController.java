package com.cykj.web.controller.posbiz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import cn.hutool.core.date.Month;
import com.cykj.common.utils.StringUtils;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.enums.bizstatus.MonthEnum;
import com.cykj.pos.profit.service.IMerchantProfitService;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.service.IBizWalletService;
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
import com.cykj.pos.domain.BizProfitSettle;
import com.cykj.pos.service.IBizProfitSettleService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 分润结算信息Controller
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/profit/settle" )
public class BizProfitSettleController extends BaseController {

    private final IBizMerchantService iBizMerchantService;

    private final IMerchantProfitService iMerchantProfitService;

    private final IBizProfitSettleService iBizProfitSettleService;

    private final IBizWalletService iBizWalletService;

    /**
     * 查询分润结算信息列表
     */
    @PreAuthorize("@ss.hasPermi('profit:settle:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizProfitSettle bizProfitSettle) {
        startPage();
        List<BizProfitSettle> list = iBizProfitSettleService.queryList(bizProfitSettle);
        return getDataTable(list);
    }

    /**
     * 导出分润结算信息列表
     */
    @PreAuthorize("@ss.hasPermi('profit:settle:export')" )
    @Log(title = "分润结算信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizProfitSettle bizProfitSettle) {
        List<BizProfitSettle> list = iBizProfitSettleService.queryList(bizProfitSettle);
        ExcelUtil<BizProfitSettle> util = new ExcelUtil<BizProfitSettle>(BizProfitSettle.class);
        return util.exportExcel(list, "settle" );
    }

    /**
     * 获取分润结算信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('profit:settle:query')" )
    @GetMapping(value = "/{settId}" )
    public AjaxResult getInfo(@PathVariable("settId" ) Long settId) {
        return AjaxResult.success(iBizProfitSettleService.getById(settId));
    }

    /**
     * 新增分润结算信息
     */
    @PreAuthorize("@ss.hasPermi('profit:settle:add')" )
    @Log(title = "分润结算信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProfitSettle bizProfitSettle) {
        return toAjax(iBizProfitSettleService.save(bizProfitSettle) ? 1 : 0);
    }

    /**
     * 修改分润结算信息
     */
    @PreAuthorize("@ss.hasPermi('profit:settle:edit')" )
    @Log(title = "分润结算信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProfitSettle bizProfitSettle) {
        return toAjax(iBizProfitSettleService.updateById(bizProfitSettle) ? 1 : 0);
    }

    /**
     * 删除分润结算信息
     */
    @PreAuthorize("@ss.hasPermi('profit:settle:remove')" )
    @Log(title = "分润结算信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{settIds}" )
    public AjaxResult remove(@PathVariable Long[] settIds) {
        return toAjax(iBizProfitSettleService.removeByIds(Arrays.asList(settIds)) ? 1 : 0);
    }

    @PreAuthorize("@ss.hasPermi('profit:settle:caculate')" )
    @Log(title = "分润计算" , businessType = BusinessType.PROFIT_CACULATE)
    @PostMapping("/profitCaculate" )
    public AjaxResult profitCaculate(@RequestBody  BizProfitSettle settle){
        AjaxResult ajax = AjaxResult.success("分润计算完成！");
        String year = settle.getCaculateYear();
        String month = settle.getCaculateMonth();
        if(!StringUtils.isNotBlank(year) || !StringUtils.isNotBlank(month)){
            ajax = AjaxResult.error(BizStatusContantEnum.PROFIT_SETTLE_CONDITIONS_IS_NULL.getName());
            ajax.put("code", BizStatusContantEnum.PROFIT_SETTLE_CONDITIONS_IS_NULL.getCode());
            return ajax;
        }
        int calMonth = Month.valueOf(month).getValue()+1;
        int calYear = Integer.valueOf(year);
        LocalDate localDate = LocalDate.now();
        int localYear = localDate.getYear();
        int localMonth = localDate.getMonth().getValue();
        if(localYear <= calYear && localMonth <= calMonth){
            ajax = AjaxResult.error(BizStatusContantEnum.PROFIT_SETTLE_DURATION_ERROR.getName());
            ajax.put("code", BizStatusContantEnum.PROFIT_SETTLE_DURATION_ERROR.getCode());
            return ajax;
        }
        //查询指定的月份是否已经计算过
        List<BizProfitSettle> settleList = iBizProfitSettleService.queryList(settle);
        //不包括根商户
        int counts = iBizMerchantService.count()-1;
        if(settleList != null && settleList.size() > 0 && counts == settleList.size()){
            ajax = AjaxResult.error(BizStatusContantEnum.PROFIT_SETTLE_HAVE_SETTLED.getName());
            ajax.put("code", BizStatusContantEnum.PROFIT_SETTLE_HAVE_SETTLED.getCode());
            return ajax;
        }
        List<Long> settledMerchants = new ArrayList<>();
        for(BizProfitSettle settled:settleList){
            settledMerchants.add(settled.getMerchId());
        }
        List<BizMerchant> merchantList =  iBizMerchantService.list();
        for(BizMerchant merch:merchantList){
            //根商户或指定月份计算过的商户不参与计算
            if(1 == merch.getMerchId() || settledMerchants.contains(merch.getMerchId())) continue;
            iMerchantProfitService.caculateProfit(year,month,merch.getMerchId());
        }
        return ajax;
    }
    @PreAuthorize("@ss.hasPermi('profit:settle:payment')" )
    @Log(title = "分润计算-结算支付" , businessType = BusinessType.PROFIT_PAYMENT)
    @PostMapping("/payment" )
    public AjaxResult settlePayment(@RequestBody Long settId){
        AjaxResult ajax = AjaxResult.success("结算支付成功！");
        iMerchantProfitService.profitPayment(settId);
        return ajax;
    }
    @PreAuthorize("@ss.hasPermi('profit:settle:payment')" )
    @Log(title = "分润计算-批量结算支付" , businessType = BusinessType.PROFIT_PAYMENT)
    @PostMapping("/batchPayment" )
    public AjaxResult batchSettlePayment(@RequestBody List<Long> settIds){
        AjaxResult ajax = AjaxResult.success("批量结算支付成功！");
        for(Long settId:settIds){
            iMerchantProfitService.profitPayment(settId);
        }
        return ajax;
    }
}

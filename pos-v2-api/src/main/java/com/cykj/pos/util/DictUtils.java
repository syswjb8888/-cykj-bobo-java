package com.cykj.pos.util;

import com.cykj.common.utils.StringUtils;
import com.cykj.common.utils.spring.SpringUtils;
import com.cykj.system.service.ISysDictDataService;

public class DictUtils {
    private static ISysDictDataService dictDataService = SpringUtils.getBean(ISysDictDataService.class);
    public static String getDictLabel(String type, String value, String defaultValue) {
        String label = dictDataService.selectDictLabel(type,value);
        return StringUtils.isNotBlank(label)?label:defaultValue;
    }
}

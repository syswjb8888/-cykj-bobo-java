package com.cykj.pos.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <T> String toString(List<T> list, char separator) {
        return StringUtils.join(list.toArray(),separator);
    }
    public static void main(String[] args){
        List<Long> ids = new ArrayList<>();
        ids.add(1L);ids.add(2L);ids.add(20L);ids.add(11L);
        System.out.println(ListUtils.toString(ids,','));
    }
}

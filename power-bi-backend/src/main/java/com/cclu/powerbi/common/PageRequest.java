package com.cclu.powerbi.common;

import com.cclu.powerbi.constant.CommonConstant;
import lombok.Data;

/**
 * 分页请求
 *
 * @author ChangChengLu
 * @from https://github.com/ChangChengLu>
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}

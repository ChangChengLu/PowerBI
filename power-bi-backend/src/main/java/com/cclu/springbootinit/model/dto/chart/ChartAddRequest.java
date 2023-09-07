package com.cclu.springbootinit.model.dto.chart;

import lombok.Data;

/**
 * @author ChangCheng Lu
 * @date 2023/9/6 19:25
 */
@Data
public class ChartAddRequest {

    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表数据
     */
    private String chartData;

    /**
     * 图表类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;

}

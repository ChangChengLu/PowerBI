package com.cclu.powerbi.model.dto.chart;

import com.cclu.powerbi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author ChangCheng Lu
 * @date 2023/9/6 19:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChartQueryRequest extends PageRequest implements Serializable {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表类型
     */
    private String chartType;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;

}

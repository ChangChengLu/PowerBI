package com.cclu.powerbi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cclu.powerbi.model.dto.chart.ChartQueryRequest;
import com.cclu.powerbi.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 21237
* @description 针对表【chart(图表信息表)】的数据库操作Service
* @createDate 2023-09-05 21:21:00
*/
public interface ChartService extends IService<Chart> {

    /**
     * 获取查询包装类
     *
     * @param chartQueryRequest
     * @return
     */
    QueryWrapper<Chart> getQueryWrapper(ChartQueryRequest chartQueryRequest);


    /**
     * 图表错误处理
     * @param chartId
     * @param execMessage
     */
    void handleChartUpdateError(long chartId, String execMessage);

}

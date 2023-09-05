package com.cclu.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cclu.springbootinit.model.entity.Chart;
import com.cclu.springbootinit.service.ChartService;
import com.cclu.springbootinit.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @author 21237
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-09-05 21:21:00
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}





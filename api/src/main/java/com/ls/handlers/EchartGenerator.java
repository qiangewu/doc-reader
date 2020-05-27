package com.ls.handlers;

import com.ls.entity.EnergyUsage;
import com.ls.entity.echarts.Option;
import com.ls.enums.EchartsType;
import com.ls.enums.UnitType;
import com.ls.utils.EchartsUtil;
import com.ls.utils.HtmlTableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 生成Echarts对应图表
 */
public class EchartGenerator {

    private static final Logger logger = LoggerFactory.getLogger(EchartGenerator.class);

    /**
     * 生成 柱状图 峰谷平电量（按月）趋势图
     */
    public static String generateElectricityTrend(Option electricityTrendOption){
        String path = null;
        if(electricityTrendOption!=null){
            path = EchartsUtil.generateEchartsPicture(electricityTrendOption, EchartsType.HISTOGRAM);
        }
        return path;
    }


    /**
     * 生成 平滑曲线图 月均负荷曲线图
     */
    public static String generateLoadLineOption(Option loadLineOption){
        String path = null;
        if(loadLineOption!=null){
            path = EchartsUtil.generateEchartsPicture(loadLineOption, EchartsType.SMOOTH_LINE);
        }
        return path;
    }



    /**
     * 生成 平滑曲线图 总体功率因数趋势图
     */
    public static String generatePowerFactorTrend(Option powerFactorTrendOption){
        String path = null;
        if(powerFactorTrendOption!=null){
            path = EchartsUtil.generateEchartsPicture(powerFactorTrendOption, EchartsType.SMOOTH_LINE);
        }
        return path;
    }


}
package com.ls.handlers;

import com.ls.enums.EchartsType;
import com.ls.enums.UnitType;
import com.ls.utils.EchartsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EchartGenerator {

    private static final Logger logger = LoggerFactory.getLogger(EchartGenerator.class);


    public static void main(String[] args) {
        String[] items = new String[] {"峰电量","谷电量","平电量"};
        String[] xRanges = new String[] {"6月1日", "4日", "7日", "10日", "13日", "16日", "19日", "22日", "25日", "28日", "30日"};
        int[][] datas = new int[][]{{10000000,2,3,4,5},{10000000,2,3,20000000,5},{10000000,2,3,4,40000000}};
        String unit = UnitType.KWH.getDesc();
        //生成柱状图所需的option
         EchartsUtil.generateEchartsPicture(items,new String[]{},xRanges,datas,unit, EchartsType.HISTOGRAM);

//        String[] items = new String[] {"有功功率|晶体 三厂","test"};
//        String[] xRanges = new String[] {"6月1日", "4日", "7日", "10日", "13日", "16日", "19日", "22日", "25日", "28日", "30日"};
//        int[][] datas = new int[][]{{21000,12000,14000,35000,12000,15000,17000,21000,12000,14000,35000},{11000,22000,17000,25000,10000,8000,15000,11000,16000,18000,25000}};
//        String unit = UnitType.KW.getDesc();
//        //生成平滑曲线图所需的option
//        String option2 = EchartsUtil.generateSmoothLineOption(items,new String[]{},xRanges,datas,unit);
//        EchartsUtil.generateEChart(option2);
    }

}
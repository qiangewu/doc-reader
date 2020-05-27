package com.ls.handlers;

import com.ls.entity.BuildAnalysisTemplate;
import com.ls.entity.EnergyUsage;
import com.ls.entity.echarts.Option;
import com.ls.entity.echarts.SeriesItem;
import com.ls.enums.*;
import com.ls.utils.EchartsUtil;
import com.ls.utils.HtmlTableUtil;
import com.ls.utils.WordFileUtil;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对应楼宇潜力客户用能初步分析报告模板
 * 数据转化、数据模拟等处理
 */
@Component
public class BuildTemplateWordHandler {

    /**
     * 将BuildAnalysisTemplate 转为Word识别的Map信息
     * @param template
     * @return
     */
    public static HashMap<String,String> templateToTable(BuildAnalysisTemplate template){
        HashMap<String,String> resultTable = new HashMap<>();
        resultTable.put("${doorNo}",template.getDoorNo());
        resultTable.put("${userName}",template.getUserName());
        resultTable.put("${userType}",template.getUserType());
        resultTable.put("${buildingType}",template.getBuildingType());
        resultTable.put("${totalCapacity}",template.getTotalCapacity());
        resultTable.put("${area}",template.getArea());
        resultTable.put("${serveTelNum}",template.getServeTelNum());
        resultTable.put("$P{electricityTrend}",template.getElectricityTrend());
        resultTable.put("${electricQuantityTotal}",template.getElectricQuantityTotal());
        resultTable.put("${electricQuantityPeak}",template.getElectricQuantityPeak());
        resultTable.put("${electricQuantityPeakRatio}",template.getElectricQuantityPeakRatio());
        resultTable.put("${electricQuantityTrough}",template.getElectricQuantityTrough());
        resultTable.put("${electricQuantityTroughRatio}",template.getElectricQuantityTroughRatio());
        resultTable.put("${electricQuantityAverage}",template.getElectricQuantityAverage());
        resultTable.put("${electricQuantityAverageRatio}",template.getElectricQuantityAverageRatio());
        resultTable.put("${electricityTrendAnalysis}",template.getElectricityTrendAnalysis());
        resultTable.put("$P{loadLine}",template.getLoadLine());
        resultTable.put("${loadAverage}",template.getLoadAverage());
        resultTable.put("${loadMaxValue}",template.getLoadMaxValue());
        resultTable.put("${loadMaxTime}",template.getLoadMaxTime());
        resultTable.put("${loadMinValue}",template.getLoadMinValue());
        resultTable.put("${loadMinTime}",template.getLoadMinTime());
        resultTable.put("${loadAnalysis}",template.getLoadAnalysis());
        resultTable.put("$P{powerFactorTrend}",template.getPowerFactorTrend());
        resultTable.put("${powerElectric}",template.getPowerElectric());
        resultTable.put("${reactiveElectric}",template.getReactiveElectric());
        resultTable.put("${powerFactor}",template.getPowerFactor());
        resultTable.put("${assessmentCriterion}",template.getAssessmentCriterion());
        resultTable.put("${coefficient}",template.getCoefficient());
        resultTable.put("${powerFactorAnalysis}",template.getPowerFactorAnalysis());
        resultTable.put("$P{powerUsage}",template.getPowerUsage());
        resultTable.put("${energyConsumptionDiagnosis}",template.getEnergyConsumptionDiagnosis());
        resultTable.put("${electricDiagnosis}",template.getElectricDiagnosis());
        resultTable.put("${loadDiagnosis}",template.getLoadDiagnosis());
        resultTable.put("${powerFactorDiagnosis}",template.getPowerFactorDiagnosis());
        return resultTable;
    }


    public static void generatePicture(BuildAnalysisTemplate template){
        //生成echarts 柱状图 峰谷平电量（按月）趋势图
        Option electricityTrendOption = template.getElectricityTrendOption();
        if(electricityTrendOption!=null){
            String path = EchartsUtil.generateEchartsPicture(electricityTrendOption, EchartsType.HISTOGRAM);
            template.setElectricityTrend(path);
        }
        //echarts 平滑曲线图 月均负荷曲线图
        Option loadLineOption = template.getLoadLineOption();
        if(electricityTrendOption!=null){
            String path = EchartsUtil.generateEchartsPicture(loadLineOption, EchartsType.SMOOTH_LINE);
            template.setLoadLine(path);
        }
        //echarts 平滑曲线图 总体功率因数趋势图
        Option powerFactorTrendOption = template.getPowerFactorTrendOption();
        if(powerFactorTrendOption!=null){
            String path = EchartsUtil.generateEchartsPicture(powerFactorTrendOption, EchartsType.SMOOTH_LINE);
            template.setPowerFactorTrend(path);
        }
        //主要用能情况表
        List<EnergyUsage> energyUsageList = template.getEnergyUsageList();
        String powerUsage = TableGenerator.generateEnergyUsageTable(energyUsageList);
        template.setPowerUsage(powerUsage);
    }
}

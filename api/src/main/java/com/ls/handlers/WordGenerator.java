package com.ls.handlers;

import com.ls.entity.BuildAnalysisTemplate;
import com.ls.utils.WordFileUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 替换文档信息
 */
@Component
public class WordGenerator {

    final static String PICTURE_PATH = "C:\\Users\\zhangyang\\Desktop\\temp";

    @PostConstruct
    void generateWord(){
        XWPFDocument xwpfDocument = null;
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\zhangyang\\Desktop\\temp\\doc-analysis\\template.docx");
            FileOutputStream fos = new FileOutputStream("C:\\Users\\zhangyang\\Desktop\\temp\\doc-analysis\\target.docx");
            ZipSecureFile.setMinInflateRatio(-1.0d);
            BuildAnalysisTemplate buildAnalysisTemplate = inintBuildAnalysisTemplate();
            HashMap<String,String> resultTable = templateToTable(buildAnalysisTemplate);
            WordFileUtils.replaceDocx(fis,fos,resultTable);
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 模拟
     * 楼宇潜力客户用能初步分析报告模板
     * 数据
     * @return
     */
    BuildAnalysisTemplate inintBuildAnalysisTemplate(){
        BuildAnalysisTemplate template = new BuildAnalysisTemplate();
        template.setDoorNo("0077723286");
        template.setUserName("上海浦东大酒店");
        template.setUserType("");
        template.setBuildingType(null);
        template.setTotalCapacity("1234567");
        template.setArea("上海");
        template.setServeTelNum("0510-xxxxx");
        //echarts 柱状图
        template.setElectricityTrend(PICTURE_PATH+ File.separator+"test-4d0a8899.png");
        template.setElectricQuantityTotal("49207504");
        template.setElectricQuantityPeak("1863771");
        template.setElectricQuantityPeakRatio("3.79%");
        template.setElectricQuantityTrough("1701008");
        template.setElectricQuantityTroughRatio("3.46%");
        template.setElectricQuantityAverage("45642725");
        template.setElectricQuantityAverageRatio("92.76%");
        template.setElectricityTrendAnalysis("本期，该用户单位能耗为XXX，在同类别楼宇中属于平均偏向水平。总用电量为49207504kWh。" +
                "其中峰时用电1863771kWh，占比3.79%，平时用电45642725kWh，占比92.76%，谷时用电1701008kWh，占比23.46%。从用电量方面来看，" +
                "峰时用电占比不是较高的，该用户可以合理避开高峰用电。");
        //echarts 平滑曲线图
        template.setLoadLine(PICTURE_PATH+ File.separator+"test-4d0a8899.png");
        template.setLoadAverage("20545.38");
        template.setLoadMaxValue("25452");
        template.setLoadMaxTime("2019-06-28 23:29:20");
        template.setLoadMinValue("17256");
        template.setLoadMinTime("17256");
        template.setLoadAnalysis("本期，最大负荷25452kW，发生时间2019-06-28 23:29:20，最小负荷17256kW，" +
                "发生时间2019-06-14 05:30:50，平均负荷20545.38kW。负载率大于30%且低于80%，用电负荷合理，" +
                "不存在 “大马拉小车”或“小马拉大车”现象。");
        //echarts 平滑曲线图
        template.setPowerFactorTrend(PICTURE_PATH+ File.separator+"test-4d0a8899.png");
        template.setPowerElectric("49207680");
        template.setReactiveElectric("13804800");
        template.setPowerFactor("0.9628");
        template.setAssessmentCriterion("0.9");
        template.setCoefficient("0%");
        template.setPowerFactorAnalysis("本期，该用户总体功率因数为0.9628，高于考核标准值0.9，符合无功补偿的要求。" +
                "建议该变电站继续加强无功管理，及时做好电容装置的运行维护工作。");
        //html table图表
        template.setPowerUsage(PICTURE_PATH+ File.separator+"test-4d0a8899.png");
        template.setEnergyConsumptionDiagnosis("本期，该用户平均单位面积能耗为XX，远低于同类——星级酒店的标准，存在较大的改进空间。");
        template.setElectricDiagnosis("本期，该用户总用电量为49207504kWh。其中峰时用电1863771kWh，占比3.79%，平时用电45642725kWh，占比92.76%，" +
                "谷时用电1701008kWh，占比3.46%。从用电量方面来看，峰时用电占比不是最高的，该用户已经合理避开高峰用电。");
        template.setLoadDiagnosis("本期，变压器的平均负载率为XX，变压主变存在“大马拉小车”现象。");
        template.setPowerFactorDiagnosis("该用户总体功率因数低于考核标准值，建议该用户增加无功管理，使功率因数达到标准，以控制力调电费。");
        return template;
    }

    HashMap<String,String> templateToTable(BuildAnalysisTemplate template){
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
}

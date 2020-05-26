package com.ls.utils;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class EchartsUtil {

    private static final String EC_PATH = "C:\\programes\\git\\doc-reader\\api\\src\\main\\resources\\static\\js\\echarts-convert.js";
    private static final String PHANTOM_PATH = "C:\\software\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";
    private static final String TEMP_PATH = "C:\\Users\\zhangyang\\Desktop\\temp\\";

    static Logger logger = LoggerFactory.getLogger(EchartsUtil.class);

    /**
     * 生成柱状图option
     * @param items   对照组名称，String[]
     *                * @param colors   对照组颜色，String[] 非必填 提供三组不同颜色的对照组
     * @param xRanges   x轴刻度，String[]
     * @param datas   对照组对应数据，int[][]
     * @return
     */
    public static String generateHistogramOption(String[] items,String[] colors,String[] xRanges,int[][] datas, String unit){
        HashMap<String, Object> resultMap = generateResultMap(items,colors,xRanges,datas,unit);
        String option = null;
        try {
            option = FreemarkerUtil.generateString("histogram.json", "/templates", resultMap);
        } catch (IOException e) {
            logger.error(e.toString());
        } catch (TemplateException e) {
            logger.error(e.toString());
        }finally {
            return option;
        }
    }

    /**
     * 生成平滑曲线图option
     * 当前模板只支持单个组的曲线，不支持多组对比
     * @param items   对照组名称，String[] 必填
     * @param colors   对照组颜色，String[] 非必填 提供三组不同颜色的对照组
     * @param xRanges   x轴刻度，String[] 必填
     * @param datas   对照组对应数据，int[][] 必填
     * @return
     */
    public static String generateSmoothLineOption(String[] items,String[] colors,String[] xRanges,int[][] datas,String unit){
        HashMap<String, Object> resultMap = generateResultMap(items,colors,xRanges,datas,unit);
        String option = null;
        try {
            option = FreemarkerUtil.generateString("smooth-line.json", "/templates", resultMap);
        } catch (IOException e) {
            logger.error(e.toString());
        } catch (TemplateException e) {
            logger.error(e.toString());
        }finally {
            return option;
        }
    }

    /**
     * 根据输入信息处理为option可以识别的类型
     * @param items
     * @param colors
     * @param xRanges
     * @param datas
     * @param unit
     * @return
     */
    static HashMap<String, Object> generateResultMap(String[] items,String[] colors,String[] xRanges,int[][] datas, String unit){
        Map<String,String> seriesMap = new HashMap<>();
        for(int i = 0;i<items.length;i++){
            seriesMap.put(items[i],toIntArrayStr(datas[i]));
        }
        if(colors==null||colors.length<1){
            colors = new String[]{"#ff9900", "#00cc66", "#1495eb"};
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("items", items);
        resultMap.put("colors", toStringArrayStr(colors));
        resultMap.put("xRanges",  toStringArrayStr(xRanges));
        resultMap.put("seriesMap", seriesMap);
        resultMap.put("unit", "单位:  "+unit);
        return resultMap;
    }

    /**
     * 将字符型数组转化为','分割的字符串
     * @return
     */
    static String toStringArrayStr(String[] array){
        if(array==null||array.length<1){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(String str:array){
            sb.append("\""+str+"\""+",");
        }
        return sb.substring(0,sb.length()-1);
    }

    /**
     * 将int型数组转化为','分割的字符串
     * @return
     */
    static String toIntArrayStr(int[] array){
        if(array==null||array.length<1){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int str:array){
            sb.append(str+",");
        }
        return sb.substring(0,sb.length()-1);
    }

    /**
     * phantomjs根据option生成图表图片
     * @param option
     * @return
     */
    public static String generateEChart(String option) {
        String dataPath = writeFile(option);
        String fileName= "test-"+ UUID.randomUUID().toString().substring(0, 8) + ".png";
        String path = TEMP_PATH +fileName;
        try {
            File file = new File(path);     //文件路径（路径+文件名）
            if (!file.exists()) {   //文件不存在则创建文件，先创建目录
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            String cmd = PHANTOM_PATH + " " + EC_PATH + " -infile " + dataPath + " -outfile " + path;
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                logger.info(line);
            }
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            return path;
        }
    }

    /**
     * 新建临时的echarts option.json文件
     * 用于phantomjs生成图片
     * @param options
     * @return
     */
    public static String writeFile(String options) {
        String dataPath= TEMP_PATH + UUID.randomUUID().toString().substring(0, 8) +".json";
        try {
            /* 写入Txt文件 */
            File writename = new File(dataPath); // 相对路径，如果没有则要建立一个新的output.txt文件
            if (!writename.exists()) {   //文件不存在则创建文件，先创建目录
                File dir = new File(writename.getParent());
                dir.mkdirs();
                writename.createNewFile(); // 创建新文件
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(options); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPath;
    }

}

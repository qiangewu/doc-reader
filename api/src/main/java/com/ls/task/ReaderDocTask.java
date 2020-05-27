package com.ls.task;

import com.ls.entity.BuildAnalysisTemplate;
import com.ls.handlers.BuildTemplateWordHandler;
import com.ls.handlers.SimulateDateHandler;
import com.ls.utils.FileUtil;
import com.ls.utils.WordFileUtil;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时读取某路径下的doc文件模板并解析
 * 生成对应的doc文件
 */
@Component
public class ReaderDocTask {

    final static String PICTURE_PATH = "C:\\Users\\zhangyang\\Desktop\\temp\\test\\";

    /**
     * 楼宇潜力客户用能初步分析报告模板
     */
    @PostConstruct
//    @Scheduled(cron = "0 0/2 * * * ?")
    public void readerExcel(){
        XWPFDocument xwpfDocument = null;
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\zhangyang\\Desktop\\temp\\doc-analysis\\template.docx");
            FileOutputStream fos = new FileOutputStream("C:\\Users\\zhangyang\\Desktop\\temp\\doc-analysis\\target.docx");
            ZipSecureFile.setMinInflateRatio(-1.0d);
            //模拟数据，实际场景需要替换此处
            BuildAnalysisTemplate buildAnalysisTemplate = SimulateDateHandler.inintBuildAnalysisTemplate();
            //生成对应图片
            BuildTemplateWordHandler.generatePicture(buildAnalysisTemplate);
            //转化Word识别的Map
            HashMap<String,String> resultTable = BuildTemplateWordHandler.templateToTable(buildAnalysisTemplate);
            WordFileUtil.replaceDocx(fis,fos,resultTable);
            fis.close();
            fos.close();
            //清空临时文件
            FileUtil.deleteAllSafely(PICTURE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

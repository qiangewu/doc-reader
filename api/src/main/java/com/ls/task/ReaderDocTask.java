package com.ls.task;

import com.ls.utils.WordFileUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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
//@Component
public class ReaderDocTask {

    final static String PICTURE_PATH = "C:\\Users\\zhangyang\\Desktop\\temp";

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
            Map<String,String> params = new HashMap<>();
            params.put("${test}","你好");
            params.put("$P{electricityTrend}",PICTURE_PATH+ File.separator+"3.png");
            WordFileUtils.replaceDocx(fis,fos,params);
            fis.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

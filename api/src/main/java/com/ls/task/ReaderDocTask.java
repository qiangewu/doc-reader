package com.ls.task;

import com.ls.test.WordHandler;
import com.ls.utils.DocxFileReplaceUtil;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
            WordHandler.executeDocx(fis,fos,params);
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

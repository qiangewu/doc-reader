package com.ls.task;

import com.ls.config.GlobalConfig;
import com.ls.entity.BuildAnalysisTemplate;
import com.ls.handlers.BuildTemplateWordHandler;
import com.ls.handlers.SimulateDateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 定时读取某路径下的doc文件模板并解析
 * 生成对应的doc文件
 */
@Component
public class ReaderDocTask {

    @Autowired
    GlobalConfig globalConfig;

    /**
     * 楼宇潜力客户用能初步分析报告模板
     */
    @PostConstruct
//    @Scheduled(cron = "0 0/2 * * * ?")
    public void readerExcel(){
        //模拟数据，实际场景需要替换此处
        BuildAnalysisTemplate buildAnalysisTemplate = SimulateDateHandler.inintBuildAnalysisTemplate();
        BuildTemplateWordHandler.generateNewWord(buildAnalysisTemplate);
    }



}

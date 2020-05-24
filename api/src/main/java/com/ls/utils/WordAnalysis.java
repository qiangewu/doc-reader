package com.ls.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析Word文档
 */
public class WordAnalysis {

    Logger logger = LoggerFactory.getLogger(WordAnalysis.class);

    //解析word文件
    public void analysis(String fileName){
        if(!FileUtils.isWord(fileName)){
            logger.error("{}不符合word文件解析类型（只能解析doc及docx文件）！",fileName);
            return;
        }

    }

}

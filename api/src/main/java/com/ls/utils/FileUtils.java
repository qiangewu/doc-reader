package com.ls.utils;


import com.ls.enums.FileType;
import org.springframework.util.StringUtils;

public class FileUtils {

    /**
     * 判断是否是doc或者docx文件
     * @param fileName
     * @return
     */
    public static boolean isWord(String fileName){
        if(StringUtils.isEmpty(fileName)){
            return false;
        }
        String[] fileParts = fileName.split("\\.");
        String fileType = fileParts[fileParts.length-1];
        if(FileType.DOC.getDesc().equals(fileType)||FileType.DOCX.getDesc().equals(fileType)){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
//        String fileName = "C:\\Users\\zhangyang\\Desktop\\temp\\doc-analysis\\template\\楼宇潜力客户用能初步分析报告模板.docx";
        String fileName = "";
        System.out.println(fileName.split("\\.")[0]);
    }
}

package com.ls.utils;


import com.ls.enums.FileType;
import org.springframework.util.StringUtils;

public class FileUtil {

    /**
     * 判断是否是word文档
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

}

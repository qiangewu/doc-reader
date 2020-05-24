package com.ls.utils;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XWPFDocumentFactory {

    public static XWPFDocument obtainXwpfDocument(){
        XWPFDocument xwpfDocument = null;
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\zhangyang\\Desktop\\temp\\doc-analysis\\template.docx");
            ZipSecureFile.setMinInflateRatio(-1.0d);
            xwpfDocument = new XWPFDocument(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xwpfDocument;
    }
}

package com.ls.test;

import com.alibaba.fastjson.JSONObject;
import com.ls.factory.XWPFDocumentFactory;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;

import java.io.FileInputStream;
import java.util.Collections;

/**
 * 通过Word插入图片
 */
public class WordUtils {

//    public static void main(String[] args) {
//        XWPFDocument xwpfDocument = XWPFDocumentFactory.obtainXwpfDocument();
//        XmlCursor cursor = xwpfParagraph.getCTP().newCursor();
//        XWPFParagraph newPara = xwpfDocument.insertNewParagraph(cursor);
//        newPara.setAlignment(ParagraphAlignment.CENTER);//居中
//        XWPFRun newParaRun = newPara.createRun();
//        newParaRun.addPicture(new FileInputStream("./doc/bus.png"), XWPFDocument.PICTURE_TYPE_PNG,"bus.png,",Units.toEMU(200), Units.toEMU(200));
//        xwpfDocument.removeBodyElement(xwpfDocument.getPosOfParagraph(xwpfParagraph));
//
//    }
}

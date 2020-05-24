package com.ls.utils;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class WordMethds {

    public static void main(String[] args)throws Exception {
//        创建一个document对象，相当于新建一个word文档（后缀名为.docx）。
        XWPFDocument document = new XWPFDocument();
//        创建一个段落对象。
        XWPFParagraph paragraph=document.createParagraph();
//        创建一个run。run具体是什么，我也不知道。但是run是这里面的最小单元了。
        XWPFRun run=paragraph.createRun();
//        插入图片
        run.addPicture(new FileInputStream("C:\\Users\\zhangyang\\Desktop\\temp\\doc-analysis\\1.jpg"),
                XWPFDocument.PICTURE_TYPE_PNG,
                "1.png",
                Units.toEMU(400),
                Units.toEMU(200));
//        创建一个输出流 即是该文档的保存位置
        OutputStream outputStream=new FileOutputStream("C:\\Users\\zhangyang\\Desktop\\temp\\doc-analysis\\template-docx.docx");
        document.write(outputStream);
        outputStream.close();
    }
}

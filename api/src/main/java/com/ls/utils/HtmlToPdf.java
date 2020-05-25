package com.ls.utils;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HtmlToPdf {
	
	public static void topdf(String content,String path) throws DocumentException, FileNotFoundException {
		 ITextRenderer renderer = new ITextRenderer();
		    ITextFontResolver fontResolver = renderer.getFontResolver();
		    try {
		    //设置字体，否则不支持中文,在html中使用字体，html{ font-family: SimSun;}
				fontResolver.addFont("static/songti.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    renderer.setDocumentFromString(content);
		    renderer.layout();
		    renderer.createPDF(new FileOutputStream(new File(path)));
	}
	
}
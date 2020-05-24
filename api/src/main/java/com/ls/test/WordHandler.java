package com.ls.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WordHandler {


	private Logger logger = LoggerFactory.getLogger(WordHandler.class);

	public static void executeDocx(InputStream in,OutputStream os,Map<String, String> reuleMap){
		try {
			XWPFDocument document = new XWPFDocument(in);
			/**
			 * 替换段落中的指定文字
			 */
			Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
			while (itPara.hasNext()) {
				XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
				Set<String> set = reuleMap.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					List<XWPFRun> run = paragraph.getRuns();
					for (int i = 0; i < run.size(); i++) {
						if (run.get(i).getText(run.get(i).getTextPosition()) != null &&
								run.get(i).getText(run.get(i).getTextPosition()).equals(key)) {
							/**
							 * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
							 * 就可以把原来的文字全部替换掉了
							 */
							run.get(i).setText(reuleMap.get(key), 0);
						}
					}
				}
			}

			/**
			 * 替换表格中的指定文字
			 */
			Iterator<XWPFTable> itTable = document.getTablesIterator();
			while (itTable.hasNext()) {
				XWPFTable table = (XWPFTable) itTable.next();
				int count = table.getNumberOfRows();
				for (int i = 0; i < count; i++) {
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {
						for (Map.Entry<String, String> e : reuleMap.entrySet()) {
							if (cell.getText().equals(e.getKey())) {
								cell.removeParagraph(0);
								cell.setText(e.getValue());
							}
						}
					}
				}
			}
			document.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}

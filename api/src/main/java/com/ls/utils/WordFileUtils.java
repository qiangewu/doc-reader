package com.ls.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * word文档处理的相应工具类
 */
public class WordFileUtils {


	private Logger logger = LoggerFactory.getLogger(WordFileUtils.class);

	/**
	 * 使用Map替换word文档中占位符对应的字段
	 * @param in
	 * @param os
	 * @param reuleMap
	 */
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

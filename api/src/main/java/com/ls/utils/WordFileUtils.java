package com.ls.utils;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.GsonBuilderUtils;

import javax.imageio.ImageIO;

/**
 * word文档处理的相应工具类
 */
public class WordFileUtils {


	private Logger logger = LoggerFactory.getLogger(WordFileUtils.class);

	final static String WORD_PREFIX = "${";

	final static String PICTURE_PREFIX = "$P{";

	final static String SUFFIX = "}";

	/**
	 * DOC宽度
	 */
	final static Double DOC_WIDTH = 435d;

	/**
	 * 使用Map替换word文档中占位符对应的字段
	 * ${}替换对应文字
	 * $P{}替换对应图片
	 * @param in
	 * @param os
	 * @param reuleMap
	 */
	public static void replaceDocx(InputStream in,OutputStream os,Map<String, String> reuleMap) throws Exception{
		try {
			XWPFDocument document = new XWPFDocument(in);
			/**
			 * 替换段落中的指定文字
			 * 段落上支持替换
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
							//处理文字
							if(isWord(key)){
								run.get(i).setText(reuleMap.get(key), 0);
							}
							//处理图片
							if(isPicture(key)){
								//删除原来位置的占位符
								run.get(i).setText(null, 0);
								String picturePath = reuleMap.get(key);
								BufferedImage sourceImg = ImageIO.read(new FileInputStream(picturePath));
								//根据图片实际长宽比例处理DOC中图片大小
								run.get(i).addPicture(new FileInputStream(picturePath), XWPFDocument.PICTURE_TYPE_PICT,null, Units.toEMU(DOC_WIDTH), Units.toEMU(DOC_WIDTH/sourceImg.getWidth()*sourceImg.getHeight()));
							}
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

	/**
	 * 判断替换key类型是文字
	 */
	public static boolean isWord(String key){
		if(key.startsWith(WORD_PREFIX)&&key.endsWith(SUFFIX)){
			return true;
		}
		return false;
	}


	/**
	 * 判断替换key类型是图片
	 */
	public static boolean isPicture(String key){
		if(key.startsWith(PICTURE_PREFIX)&&key.endsWith(SUFFIX)){
			return true;
		}
		return false;
	}
	
}

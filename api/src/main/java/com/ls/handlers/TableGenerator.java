package com.ls.handlers;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.ls.entity.EnergyUsage;
import com.ls.enums.IntentionStatus;
import com.ls.enums.LoadLevel;
import com.ls.enums.ProductionType;
import com.ls.utils.FreemarkerUtil;
import com.ls.utils.HtmlTableUtil;
import freemarker.template.TemplateException;
import gui.ava.html.parser.HtmlParser;
import gui.ava.html.parser.HtmlParserImpl;
import gui.ava.html.renderer.ImageRenderer;
import gui.ava.html.renderer.ImageRendererImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;

/**
 * 生成对应table图片
 */
public class TableGenerator {

    /**
     * 主要用能情况表
     */
    public static String generateEnergyUsageTable(List<EnergyUsage> energyUsageList){
        String path = null;
        if(energyUsageList!=null){
            Map<String,Object> datas = new HashMap<>();
            datas.put("energyUsagaList",energyUsageList);
            path = HtmlTableUtil.generateTablePicture(datas);
        }
        return path;
    }

}

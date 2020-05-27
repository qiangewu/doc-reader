package com.ls.handlers;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.ls.entity.EnergyUsage;
import com.ls.enums.IntentionStatus;
import com.ls.enums.LoadLevel;
import com.ls.enums.ProductionType;
import com.ls.utils.FreemarkerUtil;
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

    static Logger logger = LoggerFactory.getLogger(TableGenerator.class);


    public static void main(String[] args) {

        String imageHtml = null;
        try {
            Map<String,Object> datas = new HashMap<>();
            EnergyUsage usage1 = init();
            usage1.setLoadLevel(LoadLevel.ONE.getType());
            usage1.setProductionType(ProductionType.SECURITY_ASSURANCE.getType());
            usage1.setReplacementIntention(IntentionStatus.HAVE.getType());
            EnergyUsage usage2 = init();
            usage2.setLoadLevel(LoadLevel.TWO.getType());
            usage2.setProductionType(ProductionType.MAIN_PRODUCTION.getType());
            usage2.setReplacementIntention(IntentionStatus.HAVE.getType());
            EnergyUsage usage3 = init();
            usage3.setLoadLevel(LoadLevel.TREE.getType());
            usage3.setProductionType(ProductionType.SUPPORTING_PRODUCTION.getType());
            usage3.setReplacementIntention(IntentionStatus.NONE.getType());
            EnergyUsage usage4 = init();
            usage4.setEnergyType("用能类型4");
            usage4.setLoadLevel(LoadLevel.ONE.getType());
            usage4.setProductionType(ProductionType.NONPRODUCTIVE.getType());
            usage4.setReplacementIntention(IntentionStatus.NONE.getType());
            EnergyUsage usage5 = init();
            List<EnergyUsage> list = new ArrayList<>();
            list.add(usage1);
            list.add(usage2);
            list.add(usage3);
            list.add(usage4);
            list.add(usage5);
            datas.put("energyUsagaList",list);
            imageHtml = FreemarkerUtil.generateString("energy-usaga.html", "/templates", datas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        HtmlParser htmlParser = new HtmlParserImpl();
        htmlParser.loadHtml(imageHtml);
        ImageRenderer imageRenderer = new ImageRendererImpl(htmlParser);
        imageRenderer.saveImage("C:\\Users\\zhangyang\\Desktop\\temp\\1.gif");

    }


    public static EnergyUsage init(){
        EnergyUsage usage = new EnergyUsage();
        usage.setDeviceName("testDevice");
        usage.setDeviceNum(1200);
        usage.setEnergyType("用能类型1");
        usage.setExpectedCapacity(123000);
        usage.setTotalCapacity(230000);
        usage.setLoadLevel(LoadLevel.ONE.getType());
        usage.setProductionType(ProductionType.SUPPORTING_PRODUCTION.getType());
        usage.setReplacementIntention(IntentionStatus.HAVE.getType());
        return usage;
    }
}

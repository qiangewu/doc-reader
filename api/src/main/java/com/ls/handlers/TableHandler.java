package com.ls.handlers;

import com.ls.utils.FreemarkerUtil;
import freemarker.template.TemplateException;
import gui.ava.html.parser.HtmlParser;
import gui.ava.html.parser.HtmlParserImpl;
import gui.ava.html.renderer.ImageRenderer;
import gui.ava.html.renderer.ImageRendererImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

public class TableHandler {

    static Logger logger = LoggerFactory.getLogger(TableHandler.class);


    public static void main(String[] args) {
        String imageHtml = null;
        try {
            imageHtml = FreemarkerUtil.generateString("energy-usaga.html", "/templates", null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
//        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
//        imageGenerator.getBufferedImage();
//        //Thread.sleep(2000);
//        imageGenerator.saveAsImage(imageName);
        HtmlParser htmlParser = new HtmlParserImpl();
        htmlParser.loadHtml(imageHtml);
        ImageRenderer imageRenderer = new ImageRendererImpl(htmlParser);
        imageRenderer.saveImage("C:\\Users\\zhangyang\\Desktop\\temp\\1.png");
    }
}

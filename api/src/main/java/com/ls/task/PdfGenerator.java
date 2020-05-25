package com.ls.task;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.ls.factory.Base64ImgReplacedElementFactory;
import com.ls.utils.HtmlToPdf;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时读取某路径下的doc文件模板并解析
 * 生成对应的doc文件
 */
@Controller
public class PdfGenerator {

    @Autowired
    private TemplateEngine templateEngine;


    @PostConstruct()
    public void hello(){
        generatePdf();
    }


    @PostMapping("/hello")
    public void index2(HttpServletRequest request, HttpServletResponse response){
        System.out.println("hello！！！");
//        generatePdf();
    }

    @GetMapping("/test")
    public void generatePdf() {
//        Context context = new WebContext(request, response, servletContext, request.getLocale());
        Context context = new Context();
        Map<String,Object> variables = new HashMap<>();
        variables.put("name","yishengxiaoyao");
        variables.put("days",30);
        context.setVariables(variables);
        String content = templateEngine.process("test",context);
        String fileName = "C:\\Users\\zhangyang\\Desktop\\temp\\test.pdf";
//        ITextRenderer renderer = new ITextRenderer();
//        ITextFontResolver fontResolver = renderer.getFontResolver();
//        //设置字体，否则不支持中文,在html中使用字体，html{ font-family: SimSun;}
//        try {
//            fontResolver.addFont("templates/songti.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        renderer.setDocumentFromString(content);
//        renderer.layout();
//        try {
//            renderer.createPDF(new FileOutputStream(new File(fileName)));
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        if (StringUtils.isBlank(content)) {
            return;
        }
        content = content.trim().replaceAll("&lt;","<").replaceAll( "&gt;",">").replaceAll("<br/>","\n|\r\n|\r" )
                .replaceAll("&nbsp;"," ");
        content= content.replace("closingtags=\"\"", "/");


        String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String webappRoot = classpath.replaceAll("/target/classes", "/src/main/resources");



        try {
            ITextRenderer renderer = new ITextRenderer();
            OutputStream os = new FileOutputStream(fileName);
            // 如果携带图片则加上以下两行代码,将图片标签转换为Itext自己的图片对象，Base64ImgReplacedElementFactory为图片处理类
            renderer.getSharedContext().setReplacedElementFactory(new Base64ImgReplacedElementFactory());
            renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(1);

            renderer.setDocumentFromString(content);
            ITextFontResolver fontResolver = renderer.getFontResolver();
            // 解决中文支持问题，参数为字体的路径，html页面也必须引入字体
            fontResolver.addFont(webappRoot+"static/SimSun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

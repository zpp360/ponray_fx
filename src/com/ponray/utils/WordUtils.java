package com.ponray.utils;


import com.ponray.constans.Constants;
import com.ponray.entity.Test;
import com.ponray.entity.TestData;
import com.ponray.main.Main;
import javafx.scene.paint.Color;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

public class WordUtils {

    public static void write2Docx(Test test,List<Test> list)throws Exception{
        XWPFDocument document= new XWPFDocument();

        //Write the Document in file system
        File wordFile = new File(System.getProperty("user.dir") + File.separator + "datafile" + File.separator+test.getTestNum()+".docx");
        FileOutputStream out = new FileOutputStream(wordFile);

        //添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleParagraphRun = titleParagraph.createRun();

        titleParagraphRun.setText(test.getProgramName()+"实验报告");
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(20);

        //段落
//        XWPFParagraph firstParagraph = document.createParagraph();
//        XWPFRun run = firstParagraph.createRun();
//        run.setText("Java POI 生成word文件。");
//        run.setColor("696969");
//        run.setFontSize(16);
//
//        //设置段落背景颜色
//        CTShd cTShd = run.getCTR().addNewRPr().addNewShd();
//        cTShd.setVal(STShd.CLEAR);
//        cTShd.setFill("97FFFF");

        //换行
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun paragraphRun1 = paragraph1.createRun();
        paragraphRun1.setText("\r");



        if(Constants.KQL.equals(test.getProgramName()) || Constants.ZDL.equals(test.getProgramName())){
            //开启力和折断力，显示试样名称和峰值
            XWPFTable infoTable = document.createTable(list.size(),3);
            //列宽自动分割
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));
            //表格第一行
            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("实验编号");
            infoTableRowOne.getCell(1).setText("试样名称");
            infoTableRowOne.getCell(2).setText("峰值(N)");
            if(list!=null && list.size()>0){
                for (Test data:list){
                    XWPFTableRow infoTableRowTwo = infoTable.createRow();
                    infoTableRowTwo.getCell(0).setText(String.valueOf(data.getTestNum()));
                    infoTableRowTwo.getCell(1).setText(String.valueOf(data.getSimpleName()));
                    infoTableRowTwo.getCell(2).setText(String.valueOf(data.getMaxLoad()));
                }
            }
        }

        if(Constants.CCL.equals(test.getProgramName())){
            //穿刺力，显示试样名称，峰值和穿刺深度
            XWPFTable infoTable = document.createTable(list.size(),4);
            //列宽自动分割
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));
            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("实验编号");
            infoTableRowOne.getCell(1).setText("试样名称");
            infoTableRowOne.getCell(2).setText("峰值(N)");
            infoTableRowOne.getCell(3).setText("穿刺深度(mm)");
            if(list!=null && list.size()>0){
                for (Test data:list){
                    XWPFTableRow infoTableRowTwo = infoTable.createRow();
                    infoTableRowTwo.getCell(0).setText(String.valueOf(data.getTestNum()));
                    infoTableRowTwo.getCell(1).setText(String.valueOf(data.getSimpleName()));
                    infoTableRowTwo.getCell(2).setText(String.valueOf(data.getMaxLoad()));
                    infoTableRowTwo.getCell(3).setText(String.valueOf(data.getDeep()));
                }
            }
        }
        if(test.getProgramName().startsWith(Constants.LSL)){
            //拉伸，显示试样名称，峰值，面积，拉伸强度，标距，伸长率
            XWPFTable infoTable = document.createTable(list.size(),7);
            //列宽自动分割
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));
            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("实验编号");
            infoTableRowOne.getCell(1).setText("试样名称");
            infoTableRowOne.getCell(2).setText("峰值(N)");
            infoTableRowOne.getCell(3).setText("面积(mm²)");
            infoTableRowOne.getCell(4).setText("拉伸强度(MPa)");
            infoTableRowOne.getCell(5).setText("标距(mm)");
            infoTableRowOne.getCell(6).setText("伸长率(%)");
            if(list!=null && list.size()>0){
                for (Test data:list){
                    XWPFTableRow infoTableRowTwo = infoTable.createRow();
                    infoTableRowTwo.getCell(0).setText(String.valueOf(data.getTestNum()));
                    infoTableRowTwo.getCell(1).setText(String.valueOf(data.getSimpleName()));
                    infoTableRowTwo.getCell(2).setText(String.valueOf(data.getMaxLoad()));
                    infoTableRowTwo.getCell(3).setText(String.valueOf(data.getArea()));
                    infoTableRowTwo.getCell(4).setText(String.valueOf(data.getMpa()));
                    infoTableRowTwo.getCell(5).setText(String.valueOf(data.getLo()));
                    infoTableRowTwo.getCell(6).setText(String.valueOf(data.getExtension()));
                }
            }
        }

        if(Constants.DLZ.equals(test.getProgramName())){
            //定力值
            XWPFTable infoTable = document.createTable(list.size(),5);
            //列宽自动分割
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));
            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("实验编号");
            infoTableRowOne.getCell(1).setText("试样名称");
            infoTableRowOne.getCell(2).setText("峰值(N)");
            infoTableRowOne.getCell(3).setText("力值1(N)");
            infoTableRowOne.getCell(4).setText("力值2(N)");
            if(list!=null && list.size()>0){
                for (Test data:list){
                    XWPFTableRow infoTableRowTwo = infoTable.createRow();
                    infoTableRowTwo.getCell(0).setText(String.valueOf(data.getTestNum()));
                    infoTableRowTwo.getCell(1).setText(data.getSimpleName());
                    infoTableRowTwo.getCell(2).setText(data.getMaxLoad()+"");
                    infoTableRowTwo.getCell(3).setText(data.getDlzLoad1()+"");
                    infoTableRowTwo.getCell(4).setText(data.getDlzLoad2()+"");
                }
            }
        }

        if(Constants.DWY.equals(test.getProgramName()) || Constants.HSHDXCS.equals(test.getProgramName())){
            //定位移和活塞滑动性测试
            XWPFTable infoTable = document.createTable(list.size(),5);
            //列宽自动分割
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));
            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("实验编号");
            infoTableRowOne.getCell(1).setText("试样名称");
            infoTableRowOne.getCell(2).setText("峰值(N)");
            infoTableRowOne.getCell(3).setText("位移1(mm)");
            infoTableRowOne.getCell(4).setText("位移2(mm)");
            if(list!=null && list.size()>0){
                for (Test data:list){
                    XWPFTableRow infoTableRowTwo = infoTable.createRow();
                    infoTableRowTwo.getCell(0).setText(String.valueOf(data.getTestNum()));
                    infoTableRowTwo.getCell(1).setText(data.getSimpleName());
                    infoTableRowTwo.getCell(2).setText(data.getMaxLoad()+"");
                    infoTableRowTwo.getCell(3).setText(data.getDwyPos1()+"");
                    infoTableRowTwo.getCell(4).setText(data.getDwyPos2()+"");
                }
            }
        }

        if(Constants.BLL.equals(test.getProgramName())){
            //剥离力 力最大值，最小值，平均值，剥离强度,宽度
            XWPFTable infoTable = document.createTable(list.size(),7);
            //列宽自动分割
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));
            XWPFTableRow infoTableRowOne = infoTable.createRow();
            infoTableRowOne.getCell(0).setText("实验编号");
            infoTableRowOne.getCell(1).setText("试样名称");
            infoTableRowOne.getCell(2).setText("宽度(mm)");
            infoTableRowOne.getCell(3).setText("最大值(N)");
            infoTableRowOne.getCell(4).setText("最小值(N)");
            infoTableRowOne.getCell(5).setText("平均值(N)");
            infoTableRowOne.getCell(6).setText("剥离强度(N/mm)");
            if(list!=null && list.size()>0){
                for (Test data:list){
                    XWPFTableRow infoTableRowTwo = infoTable.createRow();
                    infoTableRowTwo.getCell(0).setText(String.valueOf(data.getTestNum()));
                    infoTableRowTwo.getCell(1).setText(data.getSimpleName());
                    infoTableRowTwo.getCell(2).setText(data.getWidth()+"");
                    infoTableRowTwo.getCell(3).setText(data.getMaxLoad()+"");
                    infoTableRowTwo.getCell(4).setText(data.getMinLoad()+"");
                    infoTableRowTwo.getCell(5).setText(data.getAvgLoad()+"");
                    infoTableRowTwo.getCell(6).setText(data.getBlqd()+"");
                }
            }
        }
        File file = new File(System.getProperty("user.dir") + File.separator + "datafile" + File.separator+test.getTestNum()+".png");
        final javafx.scene.SnapshotParameters params
                = new javafx.scene.SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        //对Node进行截图，只会截取显示出来的部分，未显示出来的部分无法截图（没有火狐截图高级）
        javafx.scene.image.WritableImage snapshot = Main.mainChart.snapshot(params, null);
        //将JavaFX格式的WritableImage对象转换成AWT BufferedImage 对象来进行保存
        final java.awt.image.BufferedImage image
                = javafx.embed.swing.SwingFXUtils.fromFXImage(snapshot, null);
        //通过ImageIO来存储图片
        try {
            javax.imageio.ImageIO.write(image, "png", file);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        FileInputStream is = new FileInputStream(file);
        titleParagraphRun.addBreak();
        titleParagraphRun.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, test.getTestNum()+".png", Units.toEMU(500), Units.toEMU(400)); // 200x200 pixels
        is.close();


        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

        //添加页眉
        CTP ctpHeader = CTP.Factory.newInstance();
        CTR ctrHeader = ctpHeader.addNewR();
        CTText ctHeader = ctrHeader.addNewT();
        String headerText = "实验报告";
        ctHeader.setStringValue(headerText);
        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
        //设置为右对齐
        headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFParagraph[] parsHeader = new XWPFParagraph[1];
        parsHeader[0] = headerParagraph;
        policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);

        //添加页脚
        CTP ctpFooter = CTP.Factory.newInstance();
        CTR ctrFooter = ctpFooter.addNewR();
        CTText ctFooter = ctrFooter.addNewT();
        String footerText = "实验报告";
        ctFooter.setStringValue(footerText);
        XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);
        headerParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFParagraph[] parsFooter = new XWPFParagraph[1];
        parsFooter[0] = footerParagraph;
        policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

        document.write(out);
        out.close();
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("rundll32 url.dll FileProtocolHandler "+wordFile.getAbsolutePath());

    }

}

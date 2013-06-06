/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JDBC_New;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.IndexedColors;


/**
 *
 * @author Lugia
 */
public class POI_Excel {
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:";
    String dbName = "db";
    Connection conn = null;
    Statement s = null;
    ResultSet rs = null;
    String[] str = null;
    
    public void read(int kind,String name,String date,String date2,String orderNum) {
        FileInputStream filein = null;
        try {
            String fileinName = null;
            String fileoutName = null;
            double money = 0;
            switch (kind) {
                case 1:
                    //对账单
                    fileinName = "对账单打印模板";
                    fileoutName = "对账单打印";
                    break;
                case 4:
                    //送货单
                    fileinName = "送货单打印模板";
                    fileoutName = "送货单打印";
                    break;
            }
            filein = new FileInputStream(new File("data/" + fileinName + ".xls"));
            HSSFWorkbook wb = new HSSFWorkbook(filein);
            CreationHelper createHelper = wb.getCreationHelper();
            HSSFSheet sheet = wb.getSheetAt(0);
            Operate.loadDriver();
            try {
                conn = DriverManager.getConnection(protocol + dbName + ";create=true");
            } catch (SQLException e) {
            }
            try {
                s = conn.createStatement();
                if (kind == 1) {
                    rs = s.executeQuery("select 订单资料big.客户名称,订单资料big.订单号," + " 产品库.货号,订单资料small.产品名称,订单资料small.单价,订单资料small.投产数量,订单资料small.金额,订单资料small.备注 " + " from 订单资料big,订单资料small,产品库 " + " where 订单资料big.订单号=订单资料small.订单号 AND 订单资料small.产品名称=产品库.产品名称 " + " AND 订单资料big.客户名称 = '" + name + "'");
                    orderNum = name;
                } else if (kind == 4) {
                    rs = s.executeQuery("select 订单资料big.客户名称,订单资料big.订单号," + " 产品库.货号,订单资料small.产品名称,订单资料small.单价,订单资料small.投产数量,订单资料small.金额,订单资料small.备注 " + " from 订单资料big,订单资料small,产品库 " + " where 订单资料big.订单号=订单资料small.订单号 AND 订单资料small.产品名称=产品库.产品名称 " + " AND 订单资料big.订单号 = '" + orderNum + "'");
                }
                int id = 1;
                Row row = null;
                while (rs.next()) {
                    row = sheet.getRow(3);
                    if (kind == 1) {
                        //对账单
                        if (Integer.parseInt(rs.getString("订单号").trim().substring(0, 6)) >= Integer.parseInt(date) && Integer.parseInt(rs.getString("订单号").trim().substring(0, 6)) <= Integer.parseInt(date2)) {
                            row.getCell(1).setCellValue(createHelper.createRichTextString(rs.getString("客户名称").trim()));
                            row = sheet.createRow(sheet.getLastRowNum() + 1);
                            row.createCell(0).setCellValue(id);
                            style(row.getCell(0), wb);
                            row.createCell(1).setCellValue(createHelper.createRichTextString(rs.getString("订单号").trim()));
                            style(row.getCell(1), wb);
                            row.createCell(2).setCellValue(createHelper.createRichTextString(rs.getString("产品名称").trim()));
                            style(row.getCell(2), wb);
                            row.createCell(3).setCellValue(createHelper.createRichTextString(rs.getString("单价").trim()));
                            style(row.getCell(3), wb);
                            row.createCell(4).setCellValue(createHelper.createRichTextString(rs.getString("投产数量").trim()));
                            style(row.getCell(4), wb);
                            row.createCell(5).setCellValue(createHelper.createRichTextString(rs.getString("金额").trim()));
                            style(row.getCell(5), wb);
                            row.createCell(6).setCellValue(createHelper.createRichTextString(rs.getString("备注").trim()));
                            style(row.getCell(6), wb);
                            money = money + Double.parseDouble(rs.getString("金额"));
                        }
                    } else if (kind == 4) {
                        //送货单
                        row.getCell(1).setCellValue(createHelper.createRichTextString(rs.getString("客户名称").trim()));
                        row.getCell(3).setCellValue(createHelper.createRichTextString(rs.getString("订单号").trim()));
                        row = sheet.createRow(sheet.getLastRowNum() + 1);
                        row.createCell(0).setCellValue(id);
                        style(row.getCell(0), wb);
                        row.createCell(1).setCellValue(createHelper.createRichTextString(rs.getString("货号").trim()));
                        style(row.getCell(1), wb);
                        row.createCell(2).setCellValue(createHelper.createRichTextString(rs.getString("产品名称").trim()));
                        style(row.getCell(2), wb);
                        row.createCell(3).setCellValue(createHelper.createRichTextString(rs.getString("单价").trim()));
                        style(row.getCell(3), wb);
                        row.createCell(4).setCellValue(createHelper.createRichTextString(rs.getString("投产数量").trim()));
                        style(row.getCell(4), wb);
                        row.createCell(5).setCellValue(createHelper.createRichTextString(rs.getString("金额").trim()));
                        style(row.getCell(5), wb);
                        row.createCell(6).setCellValue(createHelper.createRichTextString(rs.getString("备注").trim()));
                        style(row.getCell(6), wb);
                        money = money + Double.parseDouble(rs.getString("金额"));
                    }
                    id++;
                }
                row = sheet.createRow(id + 4);
                row.createCell(0).setCellValue(createHelper.createRichTextString("金额： "));
                row.createCell(5).setCellValue(createHelper.createRichTextString("签名： "));
                row.createCell(1).setCellValue(money); //计算总金额
            } catch (SQLException e1) {
            }
            try {
                conn.close();
                conn = null;
                s.close();
                s = null;
                rs.close();
                rs = null;
            } catch (Exception e) {
            }

            File dir = new File("打印记录/");
            if(!dir.exists())
               dir.mkdirs();
            FileOutputStream fileOut = new FileOutputStream("打印记录/"+fileoutName + orderNum + ".xls");
            wb.write(fileOut);
            fileOut.close();
            //do 打开Excel
            Runtime.getRuntime().exec("cmd  /c  start 打印记录/" + fileoutName + orderNum + ".xls");
            //new Print().printFileAction(fileoutName+".xls");//调用打印
        } catch (IOException ex) {
            Logger.getLogger(POI_Excel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                filein.close();
            } catch (IOException ex) {
                Logger.getLogger(POI_Excel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void style(Cell cell,HSSFWorkbook wb){
        CellStyle style = wb.createCellStyle();
	style.setBorderBottom(CellStyle.BORDER_THIN);
	style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderLeft(CellStyle.BORDER_THIN);
	style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderRight(CellStyle.BORDER_THIN);
	style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderTop(CellStyle.BORDER_THIN);
	style.setTopBorderColor(IndexedColors.BLACK.getIndex());

	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平 居中

        cell.setCellStyle(style);
    }
}

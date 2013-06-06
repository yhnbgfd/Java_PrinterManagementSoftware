/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JDBC_New;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lugia
 */
public class Manage {
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:";
    String dbName = "db";
    Connection conn = null;
    Statement s = null;
    ResultSet rs = null;
    String[][] str = null;
    
    public String[][] all_manage(/*,String customerName,String date年月*/){//当只选择kind时
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int longdu = new Operate().lineNum("订单资料small")+new Operate().lineNum("物料进货单small");
            //0:编号,日期,单号,往来单位,收支项目,收入,支出,结余
            str = new String[longdu][8];
            try {
            s=conn.createStatement();
            rs = s.executeQuery("select 订单资料big.订单号,订单资料big.客户名称,订单资料small.产品名称,订单资料small.金额 FROM 订单资料big,订单资料small where 订单资料small.订单号=订单资料big.订单号");
            int row = 0;
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("订单号").trim().substring(0, 8);
                str[row][2] = rs.getString("订单号").trim();
                str[row][3] = rs.getString("客户名称").trim();
                str[row][4] = rs.getString("产品名称").trim();

                    str[row][5] = rs.getString("金额").trim();
                    str[row][7] = rs.getString("金额").trim();
                row++;
            }
            rs = s.executeQuery("select 物料进货单big.进货单号,物料进货单big.供应商名称,物料进货单small.物料名称,物料进货单small.金额 FROM 物料进货单big,物料进货单small where 物料进货单small.进货单号=物料进货单big.进货单号");
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("进货单号").trim().substring(0, 8);
                str[row][2] = rs.getString("进货单号").trim();
                str[row][3] = rs.getString("供应商名称").trim();
                str[row][4] = rs.getString("物料名称").trim();

                    str[row][6] = rs.getString("金额").trim();
                    str[row][7] = "-"+rs.getString("金额").trim();
                row++;
            }
            } catch (SQLException e1) {}

        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] all_manage(String date,String date2){//当只选择kind时
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int longdu = new Operate().lineNum("订单资料small")+new Operate().lineNum("物料进货单small");
            //0:编号,日期,单号,往来单位,收支项目,收入,支出,结余
            str = new String[longdu][8];
            try {
            s=conn.createStatement();
            rs = s.executeQuery("select 订单资料big.订单号,订单资料big.客户名称,订单资料small.产品名称,订单资料small.金额 FROM 订单资料big,订单资料small where 订单资料small.订单号=订单资料big.订单号");
            int row = 0;
            while(rs.next()){
                if(Integer.parseInt(rs.getString("订单号").substring(0, 6).trim())>=Integer.parseInt(date)&&Integer.parseInt(rs.getString("订单号").substring(0, 6).trim())<=Integer.parseInt(date2)){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("订单号").trim().substring(0, 8);
                str[row][2] = rs.getString("订单号").trim();
                str[row][3] = rs.getString("客户名称").trim();
                str[row][4] = rs.getString("产品名称").trim();
                str[row][5] = rs.getString("金额").trim();
                str[row][7] = rs.getString("金额").trim();
                row++;
            }}
            rs = s.executeQuery("select 物料进货单big.进货单号,物料进货单big.供应商名称,物料进货单small.物料名称,物料进货单small.金额 FROM 物料进货单big,物料进货单small where 物料进货单small.进货单号=物料进货单big.进货单号");
            while(rs.next()){
                if(Integer.parseInt(rs.getString("进货单号").substring(0, 6).trim())>=Integer.parseInt(date)&&Integer.parseInt(rs.getString("进货单号").substring(0, 6).trim())<=Integer.parseInt(date2)){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("进货单号").trim().substring(0, 8);
                str[row][2] = rs.getString("进货单号").trim();
                str[row][3] = rs.getString("供应商名称").trim();
                str[row][4] = rs.getString("物料名称").trim();
                str[row][6] = rs.getString("金额").trim();
                str[row][7] = "-"+rs.getString("金额").trim();
                row++;
            }}
            } catch (SQLException e1) {}

        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] customer_summary(){
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int length = new Operate().lineNum("订单资料small");        
        str = new String[length][13];
        //编号,客户编码,客户名称,联系人,订单号,产品名称,交货日期,投产数量,单位,单价,价格,已付款,备注
        try {
            s=conn.createStatement();
            rs = s.executeQuery(
                    "select 订单资料small.编号,客户资料.客户编号,订单资料big.客户名称,客户资料.联系人,订单资料big.订单号,订单资料small.产品名称,订单资料big.预交日期,订单资料small.投产数量,订单资料small.单位,订单资料small.单价,订单资料big.总金额,订单资料big.已付金额,订单资料small.备注 "
                    + " FROM 客户资料,订单资料big,订单资料small "
                    + " where 订单资料big.客户名称=客户资料.客户名称 AND 订单资料small.订单号=订单资料big.订单号");
            int row = 0;            
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("客户编号").trim();
                str[row][2] = rs.getString("客户名称").trim();
                str[row][3] = rs.getString("联系人").trim();
                str[row][4] = rs.getString("订单号").trim();
                str[row][5] = rs.getString("产品名称").trim();
                str[row][6] = rs.getString("预交日期").trim();
                str[row][7] = rs.getString("投产数量").trim();
                str[row][8] = rs.getString("单位").trim();
                str[row][9] = rs.getString("单价").trim();
                if((rs.getString("编号").trim()).equals("1")){
                    str[row][10] = rs.getString("总金额").trim();
                    str[row][11] = rs.getString("已付金额").trim();}
                str[row][12] = rs.getString("备注").trim();
                row++;
            }
            } catch (SQLException e1) {}
            try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] customer_summary(String name){
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int length = new Operate().lineNum("订单资料small");
        str = new String[length][13];
        //编号,客户编码,客户名称,联系人,订单号,产品名称,交货日期,投产数量,单位,单价,价格,已付款,备注
        try {
            s=conn.createStatement();
            rs = s.executeQuery(
                    "select 订单资料small.编号,客户资料.客户编号,订单资料big.客户名称,客户资料.联系人,订单资料big.订单号,订单资料small.产品名称,订单资料big.预交日期,订单资料small.投产数量,订单资料small.单位,订单资料small.单价,订单资料big.总金额,订单资料big.已付金额,订单资料small.备注 "
                    + " FROM 客户资料,订单资料big,订单资料small "
                    + " where 订单资料big.客户名称=客户资料.客户名称 AND 订单资料small.订单号=订单资料big.订单号 AND 订单资料big.客户名称='"+name+"'");
            int row = 0;
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("客户编号").trim();
                str[row][2] = rs.getString("客户名称").trim();
                str[row][3] = rs.getString("联系人").trim();
                str[row][4] = rs.getString("订单号").trim();
                str[row][5] = rs.getString("产品名称").trim();
                str[row][6] = rs.getString("预交日期").trim();
                str[row][7] = rs.getString("投产数量").trim();
                str[row][8] = rs.getString("单位").trim();
                str[row][9] = rs.getString("单价").trim();
                if((rs.getString("编号").trim()).equals("1")){
                    str[row][10] = rs.getString("总金额").trim();
                    str[row][11] = rs.getString("已付金额").trim();}
                str[row][12] = rs.getString("备注").trim();
                row++;
            }
            } catch (SQLException e1) {}
            try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] customer_summary(String name,String date,String date2){
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int length = new Operate().lineNum("订单资料small");
        str = new String[length][13];
        //编号,客户编码,客户名称,联系人,订单号,产品名称,交货日期,投产数量,单位,单价,价格,已付款,备注
        try {
            s=conn.createStatement();
            rs = s.executeQuery(
                    "select 订单资料small.编号,客户资料.客户编号,订单资料big.客户名称,客户资料.联系人,订单资料big.订单号,订单资料small.产品名称,订单资料big.预交日期,订单资料small.投产数量,订单资料small.单位,订单资料small.单价,订单资料big.总金额,订单资料big.已付金额,订单资料small.备注 "
                    + " FROM 客户资料,订单资料big,订单资料small "
                    + " where 订单资料big.客户名称=客户资料.客户名称 AND 订单资料small.订单号=订单资料big.订单号 AND 订单资料big.客户名称='"+name+"'");
            int row = 0;
            while(rs.next()){
                if(Integer.parseInt(rs.getString("订单号").substring(0, 6).trim())>=Integer.parseInt(date)&&Integer.parseInt(rs.getString("订单号").substring(0, 6).trim())<=Integer.parseInt(date2)){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("客户编号").trim();
                str[row][2] = rs.getString("客户名称").trim();
                str[row][3] = rs.getString("联系人").trim();
                str[row][4] = rs.getString("订单号").trim();
                str[row][5] = rs.getString("产品名称").trim();
                str[row][6] = rs.getString("预交日期").trim();
                str[row][7] = rs.getString("投产数量").trim();
                str[row][8] = rs.getString("单位").trim();
                str[row][9] = rs.getString("单价").trim();
                if((rs.getString("编号").trim()).equals("1")){
                    str[row][10] = rs.getString("总金额").trim();
                    str[row][11] = rs.getString("已付金额").trim();}
                str[row][12] = rs.getString("备注").trim();
                row++;
            }}
            } catch (SQLException e1) {}
            try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] Statements(){//客户对账单,对账单,列数9:
        //编号,客户名称,产品名称,投产数量,单价,总价,已付款,出货时间,备注
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        rs = s.executeQuery(
                    "select 订单资料small.编号,订单资料big.订单号,订单资料big.客户名称,订单资料small.产品名称,订单资料small.投产数量,订单资料small.单价,订单资料big.总金额,订单资料big.已付金额,订单资料big.预交日期,订单资料small.备注"
                    + " FROM 订单资料big,订单资料small"
                    + " where 订单资料big.订单号=订单资料small.订单号");
        int row = 0;
        int length = new Operate().lineNum("订单资料small");
        str = new String[length][9];
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("客户名称").trim();
                str[row][2] = rs.getString("产品名称").trim();
                str[row][3] = rs.getString("投产数量").trim();
                str[row][4] = rs.getString("单价").trim();
                if((rs.getString("编号").trim()).equals("1")){
                str[row][5] = rs.getString("总金额").trim();
                str[row][6] = rs.getString("已付金额").trim();}
                str[row][7] = rs.getString("预交日期").trim();
                str[row][8] = rs.getString("备注").trim();
                row++;
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    public String[][] Statements(String name){//客户对账单,对账单,列数9:
        //编号,客户名称,产品名称,投产数量,单价,总价,已付款,出货时间,备注
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        rs = s.executeQuery(
                    "select 订单资料small.编号,订单资料big.订单号,订单资料big.客户名称,订单资料small.产品名称,订单资料small.投产数量,订单资料small.单价,订单资料big.总金额,订单资料big.已付金额,订单资料big.预交日期,订单资料small.备注"
                    + " FROM 订单资料big,订单资料small"
                    + " where 订单资料big.订单号=订单资料small.订单号 AND 订单资料big.客户名称='"+name+"'");
        int row = 0;
        int length = new Operate().lineNum("订单资料small");
        str = new String[length][9];
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("客户名称").trim();
                str[row][2] = rs.getString("产品名称").trim();
                str[row][3] = rs.getString("投产数量").trim();
                str[row][4] = rs.getString("单价").trim();
                if((rs.getString("编号").trim()).equals("1")){
                    str[row][5] = rs.getString("总金额").trim();
                    str[row][6] = rs.getString("已付金额").trim();}
                str[row][7] = rs.getString("预交日期").trim();
                str[row][8] = rs.getString("备注").trim();
                row++;
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    public String[][] Statements(String name,String date,String date2){//客户对账单,对账单,列数9:
        //编号,客户名称,产品名称,投产数量,单价,总价,已付款,出货时间,备注
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        rs = s.executeQuery(
                    "select 订单资料small.编号,订单资料big.订单号,订单资料big.客户名称,订单资料small.产品名称,订单资料small.投产数量,订单资料small.单价,订单资料big.总金额,订单资料big.已付金额,订单资料big.预交日期,订单资料small.备注"
                    + " FROM 订单资料big,订单资料small"
                    + " where 订单资料big.订单号=订单资料small.订单号 AND 订单资料big.客户名称='"+name+"'");
        int row = 0;
        int length = new Operate().lineNum("订单资料small");
        str = new String[length][9];
            while(rs.next()){
                if(Integer.parseInt(rs.getString("订单号").trim().substring(0, 6))>=Integer.parseInt(date)&&Integer.parseInt(rs.getString("订单号").trim().substring(0, 6))<=Integer.parseInt(date2)){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("客户名称").trim();
                str[row][2] = rs.getString("产品名称").trim();
                str[row][3] = rs.getString("投产数量").trim();
                str[row][4] = rs.getString("单价").trim();
                if((rs.getString("编号").trim()).equals("1")){
                    str[row][5] = rs.getString("总金额").trim();
                    str[row][6] = rs.getString("已付金额").trim();}
                str[row][7] = rs.getString("预交日期").trim();
                str[row][8] = rs.getString("备注").trim();
                row++;
            }}
        
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    public String[][] Delivery_Note(){//送货单(订单打印格式)
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int length = new Operate().lineNum("订单资料small");
        str = new String[length][9];
        //编号,客户名称.,产品名称.投产数量.单价,总价,已付款,出货时间,备注
        try {
            s=conn.createStatement();
            rs = s.executeQuery(
                    "select 订单资料small.编号,订单资料big.订单号,订单资料small.产品名称,订单资料small.投产数量,订单资料small.单价,订单资料big.总金额,订单资料big.已付金额,订单资料big.预交日期,订单资料small.备注"
                    + " FROM 订单资料big,订单资料small"
                    + " where 订单资料big.订单号=订单资料small.订单号 ");
             int row = 0;
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("订单号").trim();
                str[row][2] = rs.getString("产品名称").trim();
                str[row][3] = rs.getString("投产数量").trim();
                str[row][4] = rs.getString("单价").trim();
                if((rs.getString("编号").trim()).equals("1")){
                str[row][5] = rs.getString("总金额").trim();
                str[row][6] = rs.getString("已付金额").trim();
                str[row][7] = rs.getString("备注").trim();}
                row++;
            }
            } catch (SQLException e1) {}
            try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] Delivery_Note(String name){//送货单(订单打印格式)
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int length = new Operate().lineNum("订单资料small");
        str = new String[length][9];
        //编号,客户名称.,产品名称.投产数量.单价,总价,已付款,出货时间,备注
        try {
            s=conn.createStatement();
            rs = s.executeQuery(
                    "select 订单资料small.编号,订单资料big.订单号,订单资料small.产品名称,订单资料small.投产数量,订单资料small.单价,订单资料big.总金额,订单资料big.已付金额,订单资料big.预交日期,订单资料small.备注"
                    + " FROM 订单资料big,订单资料small"
                    + " where 订单资料big.订单号=订单资料small.订单号 AND 订单资料big.客户名称='"+name+"'");
             int row = 0;
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("订单号").trim();
                str[row][2] = rs.getString("产品名称").trim();
                str[row][3] = rs.getString("投产数量").trim();
                str[row][4] = rs.getString("单价").trim();
                if((rs.getString("编号").trim()).equals("1")){
                str[row][5] = rs.getString("总金额").trim();
                str[row][6] = rs.getString("已付金额").trim();
                str[row][7] = rs.getString("备注").trim();}
                row++;
            }
            } catch (SQLException e1) {}
            try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] Delivery_Note(String name,String date,String date2){//送货单(订单打印格式)
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int length = new Operate().lineNum("订单资料small");
        str = new String[length][9];
        //编号,客户名称.,产品名称.投产数量.单价,总价,已付款,出货时间,备注
        try {
            s=conn.createStatement();
            rs = s.executeQuery(
                    "select 订单资料small.编号,订单资料big.订单号,订单资料small.产品名称,订单资料small.投产数量,订单资料small.单价,订单资料big.总金额,订单资料big.已付金额,订单资料big.预交日期,订单资料small.备注"
                    + " FROM 订单资料big,订单资料small"
                    + " where 订单资料big.订单号=订单资料small.订单号 AND 订单资料big.客户名称='"+name+"'");
             int row = 0;
            while(rs.next()){
                if(Integer.parseInt(rs.getString("订单号").trim().substring(0, 8))>=Integer.parseInt(date)&&Integer.parseInt(rs.getString("订单号").trim().substring(0, 8))<=Integer.parseInt(date2)){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("订单号").trim();
                str[row][2] = rs.getString("产品名称").trim();
                str[row][3] = rs.getString("投产数量").trim();
                str[row][4] = rs.getString("单价").trim();
                if((rs.getString("编号").trim()).equals("1")){
                    str[row][5] = rs.getString("总金额").trim();
                    str[row][6] = rs.getString("已付金额").trim();
                    str[row][7] = rs.getString("备注").trim();}
                row++;
            }}
            } catch (SQLException e1) {}
            try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] Supplier_summary(){//供应商汇总表
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int length = new Operate().lineNum("物料进货单small");
        str = new String[length][12];
        //编号,供应商编码,供应商名称,联系人,物料编号,物料名称,数量,单价,价格,已付款,要求,备注
        try {
            s=conn.createStatement();
            rs = s.executeQuery(
                    "select 供应商资料.供应商编号,物料进货单big.供应商名称,供应商资料.联系人,物料进货单small.编号,物料进货单small.物料名称,物料进货单small.物料数量,物料进货单small.单价,物料进货单big.总金额,物料进货单big.已付金额,物料进货单small.备注"
                    + " FROM 供应商资料,物料进货单big,物料进货单small"
                    + " where 物料进货单big.供应商名称=供应商资料.供应商名称 AND 物料进货单big.进货单号=物料进货单small.进货单号");
            int row = 0;
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("供应商编号").trim();
                str[row][2] = rs.getString("供应商名称").trim();
                str[row][3] = rs.getString("联系人").trim();
                str[row][4] = rs.getString("编号").trim();
                str[row][5] = rs.getString("物料名称").trim();
                str[row][6] = rs.getString("物料数量").trim();
                str[row][7] = rs.getString("单价").trim();
                if(str[row][4].equals("1")){
                    str[row][8] = rs.getString("总金额").trim();
                    str[row][9] = rs.getString("已付金额").trim();
                    str[row][10] = rs.getString("备注").trim();}
                row++;
            }
            } catch (SQLException e1) {}
            try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] Supplier_summary(String name){//供应商汇总表
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int length = new Operate().lineNum("物料进货单small");
        str = new String[length][12];
        //编号,供应商编码,供应商名称,联系人,物料编号,物料名称,数量,单价,价格,已付款,要求,备注
        try {
            s=conn.createStatement();
            rs = s.executeQuery(
                    "select 供应商资料.供应商编号,物料进货单big.供应商名称,供应商资料.联系人,物料进货单small.编号,物料进货单small.物料名称,物料进货单small.物料数量,物料进货单small.单价,物料进货单big.总金额,物料进货单big.已付金额,物料进货单small.备注"
                    + " FROM 供应商资料,物料进货单big,物料进货单small"
                    + " where 物料进货单big.供应商名称=供应商资料.供应商名称 AND 物料进货单big.进货单号=物料进货单small.进货单号 AND 物料进货单big.供应商名称='"+name+"'");
            int row = 0;
            while(rs.next()){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("供应商编号").trim();
                str[row][2] = rs.getString("供应商名称").trim();
                str[row][3] = rs.getString("联系人").trim();
                str[row][4] = rs.getString("编号").trim();
                str[row][5] = rs.getString("物料名称").trim();
                str[row][6] = rs.getString("物料数量").trim();
                str[row][7] = rs.getString("单价").trim();
                if(str[row][4].equals("1")){
                    str[row][8] = rs.getString("总金额").trim();
                    str[row][9] = rs.getString("已付金额").trim();
                    str[row][10] = rs.getString("备注").trim();}
                row++;
            }
            } catch (SQLException e1) {}
            try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
    public String[][] Supplier_summary(String name,String date,String date2){//供应商汇总表
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        int length = new Operate().lineNum("物料进货单small");
        str = new String[length][12];
        //编号,供应商编码,供应商名称,联系人,物料编号,物料名称,数量,单价,价格,已付款,要求,备注
        try {
            s=conn.createStatement();
            rs = s.executeQuery(
                    "select 物料进货单big.进货单号,供应商资料.供应商编号,物料进货单big.供应商名称,供应商资料.联系人,物料进货单small.编号,物料进货单small.物料名称,物料进货单small.物料数量,物料进货单small.单价,物料进货单big.总金额,物料进货单big.已付金额,物料进货单small.备注"
                    + " FROM 供应商资料,物料进货单big,物料进货单small"
                    + " where 物料进货单big.供应商名称=供应商资料.供应商名称 AND 物料进货单big.进货单号=物料进货单small.进货单号 AND 物料进货单big.供应商名称='"+name+"'");
            int row = 0;
            while(rs.next()){
                if(Integer.parseInt(rs.getString("进货单号").trim().substring(0, 6))>=Integer.parseInt(date)&&Integer.parseInt(rs.getString("进货单号").trim().substring(0, 6))<=Integer.parseInt(date2)){
                str[row][0] = String.valueOf(row+1);
                str[row][1] = rs.getString("供应商编号").trim();
                str[row][2] = rs.getString("供应商名称").trim();
                str[row][3] = rs.getString("联系人").trim();
                str[row][4] = rs.getString("编号").trim();//物料编号
                str[row][5] = rs.getString("物料名称").trim();
                str[row][6] = rs.getString("物料数量").trim();
                str[row][7] = rs.getString("单价").trim();
                if(str[row][4].equals("1")){
                    str[row][8] = rs.getString("总金额").trim();
                    str[row][9] = rs.getString("已付金额").trim();
                    str[row][10] = rs.getString("备注").trim();}
                row++;
            }}
            } catch (SQLException e1) {}
            try {
            conn.close();
            conn = null;
            s.close();
            s = null;
            rs.close();
            rs = null;
        } catch (Exception e) {}
        return str;
    }
}
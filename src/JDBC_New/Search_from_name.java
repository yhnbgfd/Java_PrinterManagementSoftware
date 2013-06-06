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
import java.util.Scanner;


/**
 *
 * @author Lugia
 */
public class Search_from_name {
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:";
    String dbName = "db";
    Connection conn = null;
    Statement s = null;
    ResultSet rs = null;
    String[][] str = null;
    String[] nn = null;

    public String[] getName(int type){
        String tablename = null;
        String name = null;
        int ii = 0;
        switch(type){
            case 0:
                tablename = "产品库";ii=4;
                name = "产品名称";
                break;
            case 1:
                tablename = "物料库";ii=4;
                name = "物料名称";
                break;
            case 2:
                tablename = "客户资料";ii=3;
                name = "客户名称";
                break;
            case 3:
                tablename = "供应商资料";ii=3;
                name = "供应商名称";
                break;
            default:
        }

        Operate t = new Operate();
        t.loadDriver();
        int k = t.lineNum(tablename);
        nn = new String[k];

        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int i = 0;
        try {
        s=conn.createStatement();
        rs=s.executeQuery("select "+name+" , COUNT(*) from "+tablename+" GROUP BY "+name);        
        while (rs.next()) {

            Scanner s = new Scanner(rs.getString(1));//忽略空白
            nn[i] = s.next();
            while(s.hasNext())
                nn[i] = nn[i]+s.next();
            i++;
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
        String[] s = new String[i];
        for(int j=0;j<i;j++)
            s[j] = nn[j];
        return s;
    }
    public String[] getName(int type,String customerName){  //下拉框对应客户的货物查找
        String tablename = null;
        String name = null;
        String good = null;
        int ii = 0;
        switch(type){
            case 0:
                tablename = "产品库";ii=4;
                name = "客户名称";
                good = "产品名称";
                break;
            case 1:
                tablename = "物料库";ii=4;
                name = "供应商名称";
                good = "物料名称";
                break;
            case 2:
                tablename = "客户资料";ii=3;
                name = "客户名称";
                break;
            case 3:
                tablename = "供应商资料";ii=3;
                name = "供应商名称";
                break;
            default:
        }

        Operate t = new Operate();
        t.loadDriver();
        int k = t.lineNum(tablename);
        nn = new String[k];

        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int i = 0;
        try {
        s=conn.createStatement();
        rs=s.executeQuery("select "+good+" , COUNT(*) from "+tablename+" where "+name+"='"+customerName+"' GROUP BY "+good);
        while (rs.next()) {

            Scanner s = new Scanner(rs.getString(1));//忽略空白
            nn[i] = s.next();
            while(s.hasNext())
                nn[i] = nn[i]+s.next();
            i++;
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
        String[] s = new String[i];
        for(int j=0;j<i;j++)
            s[j] = nn[j];
        return s;
    }
    public String[][] name_search(int type,int kind,String keyname){
        String tablename = null;
        String cname = null;
        int cNum = 0;

        if(type==1){
            if(kind==0){                                                        //产品库-产品资料
                tablename = "产品库";
                cname = "产品名称";
                cNum = 11;
            }else if(kind==1){                                                  //产品库-物料资料
                tablename = "物料库";
                cname = "物料名称";
                cNum = 6;
            }
        }else if(type==2){
            if(kind==0){                                                        //客户资料-客户
                tablename = "客户资料";
                cname = "客户名称";
                cNum = 12;
            }else if(kind==1){                                                  //客户资料-供应商
                tablename = "供应商资料";
                cname = "供应商名称";
                cNum = 11;
            }
        }else if(type==3){
            if(kind==0){                                                        //订单管理-订单
                tablename = "订单资料big";
                cname = "客户名称";
                cNum = 7;
            }else if(kind==1){                                                  //订单管理-进货单
                tablename = "物料进货单big";
                cname = "供应商名称";
                cNum = 7;
            }
        }

        Operate t = new Operate();
        t.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        rs=s.executeQuery("SELECT "+cname+", COUNT(*) FROM "+tablename+" where "+cname+" = '"+keyname+"'GROUP BY "+cname);
        int num = 0;
        while(rs.next()){
            num = rs.getInt(2);
        }
        str = new String[num][cNum];
        rs=s.executeQuery("select * from "+tablename+" where "+cname+" = '"+keyname+"'");
        int i = 0;
        while (rs.next()) {
            for(int k=0;k<cNum;k++){
                str[i][k] = rs.getString(k+1).trim();
                }
            i++;
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

        if(type==3){
          Operate opr = new Operate();
          for(int j=0;j<str.length;j++){
            for(int i=str[j].length-2;i>=1;i--)
                str[j][i+1]=str[j][i];
            str[j][1]=opr.readNum(0,kind,str[j][2]);
            }
        }
            
        return str;
    }
    /**
     * 给客户名返回客户编号(供应商)
     * @param type
     * @param name
     * @return
     */
    public String name_num(int type,String name){
        String tablename = null;
        String peopleNum = null;
        String tname = null;
        String namenum = null;
        switch(type){
            case 0:
                tablename = "客户资料";
                peopleNum = "客户编号";
                tname = "客户名称";
                break;
            case 1:
                tablename = "供应商资料";
                peopleNum = "供应商编号";
                tname = "供应商名称";
                break;
        }
        new Operate().loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        rs=s.executeQuery("select * from "+tablename+" where "+tname+"='"+name+"'");
        while (rs.next()) {
            namenum = rs.getString(2).trim();
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
        return namenum;
    }
}

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
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Lugia
 */
public class Time {
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:";
    String dbName = "db";
    Connection conn = null;
    Statement s = null;
    ResultSet rs = null;
    String[][] str = null;

  public  String[][] time_yMd(int type,int kind,String time1,String time2){
        String tablename = null;
        String str0 = null;
        String order = null;
        int kk = 0;
        if(type==3){            //订单管理
            if(kind==0){
                tablename = "订单资料big";
                order = "订单号";
                kk=8;
            }else if(kind==1){
                tablename = "物料进货单big";
                order = "进货单号";
                kk=8;
            }
        }else if(type==4){      //汇总
            if(kind==0){

            }else if(kind==1){

            }else if(kind==2){

            }else if(kind==3){

            }else if(kind==4){  //送货单
                tablename = "订单资料big";
            }
        }
        

        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Operate rownum = new Operate();
        str = new String[rownum.lineNum(tablename)][kk];//实例化返回数组
        try {
            s=conn.createStatement();
            rs=s.executeQuery("select * from "+tablename);
            int row = 0;
             while (rs.next()){
                    Scanner s = new Scanner(rs.getString(order));//拿第3个,订单号
                    str0 = s.next();
                    while(s.hasNext())
                        str0 = str0+s.next();
                    /*时间比较*/
                    if(Integer.parseInt(str0.substring(0,8))>=Integer.parseInt(time1)&&Integer.parseInt(str0.substring(0,8))<=Integer.parseInt(time2)){
                        for(int k=0;k<kk-1;k++){
                            Scanner s2 = new Scanner(rs.getString(k+1));
                            str[row][k] = s2.next();
                            while(s2.hasNext())
                                 str[row][k]=str[row][k]+s2.next();
                     }row++;
                 }
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

  public  String[][] time_yM(int kind,Date time1,Date time2){
        String tablename = null;
        
        if(kind==0){    //全部

        }else if(kind==1){  //对账单
            tablename = "订单资料big";
        }else if(kind==2){  //供应商汇总表
            tablename = "物料进货单big";
        }else if(kind==3){  //客户汇总表
            tablename = "订单资料big";
        }
        String time_1 = null;
        String time_2 = null;
        String time_str1 = DateFormat.getDateTimeInstance().format(time1);
        String time_str2 = DateFormat.getDateTimeInstance().format(time2);
        time_1 = time_str1.substring(0, 4)+time_str1.substring(5, 7);
        time_2 = time_str2.substring(0, 4)+time_str2.substring(5, 7);

        

        return null;
    }
}

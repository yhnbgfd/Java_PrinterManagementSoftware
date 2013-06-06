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
public class Auto_goods {
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:";
    String dbName = "db";
    Connection conn = null;
    Statement s = null;
    ResultSet rs = null;
    String[] str = null;

    public String[] auto_goods(int kind,String customerName,String goodName){
        int length = 0;
        String tablename = null;String customername = null;String goodname = null;
        switch(kind){
            case 0:
                length = 9;
                tablename = "产品库";
                customername = "客户名称";
                goodname = "产品名称";
                break;
            case 1:
                length = 4;
                tablename = "物料库";
                customername = "供应商名称";
                goodname = "物料名称";
                break;
        }str = new String[length];
        Operate.loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {}
        try {
        s=conn.createStatement();
        rs=s.executeQuery("select * from "+tablename+" where "+customername+"='"+customerName+"' AND "+goodname+"='"+goodName+"'");
        //产品库:编号,\货号,\产品名称,投产数量,\单价,单位,金额,\印版名称,\色数,\印版规格,\规格方式,\版数,\备注
        //物料库:编号,\物料编号,\物料名称,物料数量,\单价,单位,金额,要求,\备注
        while (rs.next()) {
            if(kind==0){
                str[0] = rs.getString("货号").trim();
                str[1] = rs.getString("产品名称").trim();
                str[2] = rs.getString("单价").trim();
                str[3] = rs.getString("印版名称").trim();
                str[4] = rs.getString("色数").trim();
                str[5] = rs.getString("印版规格").trim();
                str[6] = rs.getString("规格方式").trim();
                str[7] = rs.getString("版数").trim();
                str[8] = rs.getString("备注").trim();
            }else if(kind==1){
                str[0] = rs.getString("物料编号").trim();
                str[1] = rs.getString("物料名称").trim();
                str[2] = rs.getString("单价").trim();
                str[3] = rs.getString("备注").trim();
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
        return str;
    }
}

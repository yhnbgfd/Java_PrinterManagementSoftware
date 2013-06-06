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
public class Operate {
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:";
    String dbName = "db";
    Connection conn = null;
    Statement s = null;
    ResultSet rs = null;
    String[] meiyong = new String[20];
    
     static void loadDriver() {
        try {
            Class.forName(driver).newInstance();           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取查询结果的行数
     * @param name
     * @return
     */
    int lineNum(String name){
        loadDriver();
        int i = 0;
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
           /*读行*/
        rs = s.executeQuery("SELECT COUNT(*) FROM "+name);
        while(rs.next()){
            //System.out.println("COUNT(*): "+rs.getInt(1));
            i = rs.getInt(1);
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
        return i;
    }
    /**
     * 分类
     */
    private String[] classify(int type/*big*/,int kind/*small*/,String[] ss,String name/*隐藏的那个*/){
        String big_tablename = null;
        String small_tablename = null;
        String hidename = null;//smalltable中不显示的项的名称
        String column_big = null;
        String column_small = null;
        String sql_big = null;
        String sql_small = null;
        String[] str_classify = new String[7];
        if(type==2){
            if(kind==0)//客户资料-客户
            {
                big_tablename = "客户资料";
                small_tablename = "产品库";
                hidename = "客户名称";
                column_big = "12";
                column_small = "10";
                if(ss.length>11)
                sql_big = "insert into 客户资料 values('"+ss[0]+"','"+ss[1]+"','"+ss[2]+"','"+ss[3]+"','"+ss[4]+"','"+ss[5]+"','"+ss[6]+"','"+ss[7]+"','"+ss[8]+"','"+ss[9]+"','"+ss[10]+"','"+ss[11]+"')";
                sql_small = "insert into 产品库 values('"+name+"','"+ss[0]+"','"+ss[1]+"','"+ss[2]+"','"+ss[3]+"','"+ss[4]+"','"+ss[5]+"','"+ss[6]+"','"+ss[7]+"','"+ss[8]+"','"+ss[9]+"')";
            }
            else if(kind==1)//客户资料-供应商
            {
                big_tablename = "供应商资料";
                small_tablename = "物料库";
                hidename = "供应商名称";
                column_big = "11";
                column_small = "5";
                if(ss.length>6)
                sql_big = "insert into 供应商资料 values('"+ss[0]+"','"+ss[1]+"','"+ss[2]+"','"+ss[3]+"','"+ss[4]+"','"+ss[5]+"','"+ss[6]+"','"+ss[7]+"','"+ss[8]+"','"+ss[9]+"','"+ss[10]+"')";
                sql_small = "insert into 物料库 values('"+name+"','"+ss[0]+"','"+ss[1]+"','"+ss[2]+"','"+ss[3]+"','"+ss[4]+"')";

            }
        }else if(type==3){
            if(kind==0)//订单管理-订单
            {
                big_tablename = "订单资料big";
                small_tablename = "订单资料small";
                hidename = "订单号";
                column_big = "7";
                column_small = "12";
                sql_big = "insert into 订单资料big values('"+ss[0]+"','"+ss[2]+"','"+ss[3]+"','"+ss[4]+"','"+ss[5]+"','"+ss[6]+"','"+ss[7]+"')";//空掉ss[1]客户编码
                if(ss.length>9)
                sql_small = "insert into 订单资料small values('"+name+"','"+ss[0]+"','"+ss[2]+"','"+ss[3]+"','"+ss[4]+"','"+ss[5]+"','"+ss[6]+"','"+ss[7]+"','"+ss[8]+"','"+ss[9]+"','"+ss[10]+"','"+ss[11]+"','"+ss[12]+"')";

            }
            else if(kind==1)//订单管理-进货单
            {
                big_tablename = "物料进货单big";
                small_tablename = "物料进货单small";
                hidename = "进货单号";
                column_big = "7";
                column_small = "8";
                sql_big = "insert into 物料进货单big values('"+ss[0]+"','"+ss[2]+"','"+ss[3]+"','"+ss[4]+"','"+ss[5]+"','"+ss[6]+"','"+ss[7]+"')";
                if(ss.length>8)
                sql_small = "insert into 物料进货单small values('"+name+"','"+ss[0]+"','"+ss[2]+"','"+ss[3]+"','"+ss[4]+"','"+ss[5]+"','"+ss[6]+"','"+ss[7]+"','"+ss[8]+"')";

            }
        }
        str_classify[0] = big_tablename;
        str_classify[1] = small_tablename;
        str_classify[2] = hidename;
        str_classify[3] = column_big;
        str_classify[4] = column_small;
        str_classify[5] = sql_big;
        str_classify[6] = sql_small;

        return str_classify;
    }
    /**
     * 是否重复搜索
     * @return
     */
    private boolean search(String[] str_classify,String keyword/*不显示的那个*/){//无id,big table
        loadDriver();
        String str = null;
        
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        rs=s.executeQuery("select * from "+str_classify[0]);
        int row = 0;
        while (rs.next()) {
                Scanner s = new Scanner(rs.getString(3));//忽略空白
                str = s.next();
                while(s.hasNext())
                str = str+s.next();
                if(str.equals(keyword)){//相同,修改
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
                    return false;
                }
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
        return true;
    }
    private boolean search(String[] str_classify,String id/*编号*/,String keyword/*不显示的那个*/){//有id,small table
        loadDriver();
        String str = null;
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        rs=s.executeQuery("select * from "+str_classify[1]+" where "+str_classify[2]+" = '"+keyword+"'");
        int row = 0;
        while (rs.next()) {
                Scanner s = new Scanner(rs.getString(2));//取table中id号
                str = s.next();
                while(s.hasNext())
                str = str+s.next();
                if(str.equals(id)){
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
                    return false;
                }//比较,相同则返回
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
        return true;
    }
    public String[][] query(int kind){
        loadDriver();
        String[][] str_return = null;
        String table_name = null;int ii = 0;
        //String[] str_classify = classify(type,kind,meiyong,"name");
        if(kind==0){//产品库
            table_name = "产品库";ii=11;
        }else{//物料库
            table_name = "物料库";ii=6;
        }

        int length = lineNum(table_name);//取行数
        str_return = new String[length][ii];

        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
        s=conn.createStatement();
        rs=s.executeQuery("select * from "+table_name);
        int row = 0;
        while (rs.next()) {
            for(int i=0;i<ii;i++){              
                str_return[row][i] = rs.getString(i+1).trim();
            }
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
        return str_return;
    }
    /**
     * big table数据显示,点击下拉框时调用
     */
    public String[][] query_big(int type,int kind){
        loadDriver();
        String[][] str_return = null;
        
        String[] str_classify = classify(type,kind,meiyong,"name");

        int length = lineNum(str_classify[0]);//取行数
        if(type==3) str_return = new String[length][Integer.parseInt(str_classify[3])+1];
             else   str_return = new String[length][Integer.parseInt(str_classify[3])];
        
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
        s=conn.createStatement();
        rs=s.executeQuery("select * from "+str_classify[0]);
        int row = 0;
        while (rs.next()) {
            for(int i=0;i<Integer.parseInt(str_classify[3]);i++){             
                str_return[row][i] = rs.getString(i+1).trim();
            }
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
        if(type==3){
          for(int j=0;j<str_return.length;j++){
            for(int i=str_return[j].length-2;i>=1;i--)
                str_return[j][i+1]=str_return[j][i];
            str_return[j][1]=readNum(0,kind,str_return[j][2]);
            }
        }
        return str_return;
    }
    /**
     *选择big table时调用,显示small table内容
     */
    public String[][] query_small(int type,int kind,String keyword/*选择行的关键字,small中不显示的那个*/){
        loadDriver();
        String[][] str_return = null;
        String[] str_classify = classify(type,kind,meiyong,"name");

        

        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
        s=conn.createStatement();

      //取行数
        rs=s.executeQuery("select "+str_classify[2]+" ,COUNT(*) from "+str_classify[1]+" where "+str_classify[2]+" ='"+keyword+"'group by "+str_classify[2]);
        int length = 0;
        while(rs.next()){
            length = rs.getInt(2);
        }
        str_return = new String[length][Integer.parseInt(str_classify[4])+1];


        rs=s.executeQuery("select * from "+str_classify[1]+" where "+str_classify[2]+" = '"+keyword+"'");
        int row = 0;
        while (rs.next()) {
            for(int i=0;i<Integer.parseInt(str_classify[4])+1;i++){
             str_return[row][i] = rs.getString(i+1).trim();//忽略空白
            }
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
       if(type==2){
        for(int j=0;j<str_return.length;j++){
            for(int i=0;i<str_return[j].length-1;i++)
                str_return[j][i]=str_return[j][i+1];         
            }
        }
        if(type==3){
         for(int j=0;j<str_return.length;j++){          
                str_return[j][0]=str_return[j][1];
            str_return[j][1]=readNum(1,kind,str_return[j][2]);
            }
        }
        return str_return;
    }
    /**
     * 更新修改数据
     */
    public void update(int type,int kind,String[] into,String name/*在small隐藏的那个*/){//无id,big table
        loadDriver();
        String[] str_classify = classify(type,kind,into,name);
        boolean tf = search(str_classify,name);
        if(into[type]==null) return;
        if(into[type].isEmpty()) return;
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
            s=conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!tf){//运行删除          
            try {            
            s.executeUpdate("delete  from "+str_classify[0]+" where "+str_classify[2]+" = '"+name+"'");
             } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        try {            
            s.executeUpdate(str_classify[5]);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void update(int type,int kind,String id,String[] into,String name/*客户名称，订单号*/){//有id small table
        loadDriver();
        String[] str_classify = classify(type,kind,into,name);
        boolean tf = search(str_classify,id,name);
        if(into[2]==null) return;
        if(into[2].isEmpty()) return;
        
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!tf){//运行删除
            delete_small(type,kind,name,id);
            try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            s=conn.createStatement();
            s.executeUpdate(str_classify[6]);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 删除
     */
    public void delete_big(int type,int kind,String keyword){
        String[] str_classify = classify(type,kind,meiyong,"name");
        loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        s.executeUpdate("delete  from "+str_classify[0]+" where "+str_classify[2]+" = '"+keyword+"'");
        s.executeUpdate("delete  from "+str_classify[1]+" where "+str_classify[2]+" = '"+keyword+"'");
        

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void delete_small(int type,int kind,String keyword,String id){
        String[] str_classify = classify(type,kind,meiyong,"name");
        loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        s.executeUpdate("delete  from "+str_classify[1]+" where "+str_classify[2]+" = '"+keyword+"' and 编号 = '"+id+"'");

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            conn.close();
            conn = null;
            s.close();
            s = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readNum(int big_small,int type,String keyword){
        String Num = null;
        String tablename = null;
        String name = null;
        if(big_small==0){
            if(type==0){
                tablename = "客户资料";
                name = "客户名称";
            }else if(type==1){
                tablename = "供应商资料";
                name = "供应商名称";
            }
        }else if(big_small==1){
            if(type==0){
                tablename = "产品库";
                name = "产品名称";
            }else if(type==1){
                tablename = "物料库";
                name = "物料名称";
            }
        }

        loadDriver();
        try {
            conn = DriverManager.getConnection(protocol + dbName
            + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
        s=conn.createStatement();
        rs=s.executeQuery("select * from "+tablename+" where "+name+"= '"+keyword+"'");
        int i=big_small+2;
        while (rs.next()) {         
                if(rs.getString(i)==null)
                    continue;
            Scanner sk = new Scanner(rs.getString(i));//忽略空白
            Num = sk.next();
            while(sk.hasNext())
                Num = Num+sk.next();

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
        return Num;
    }

}

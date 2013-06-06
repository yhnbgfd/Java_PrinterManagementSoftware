package login;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;

public class Login {

	public boolean Login(String name,String password) throws IOException{

		//读取存有用户名和密码的文件，然后校对,正确返回true，错误返回false
		FileInputStream filein = new FileInputStream("db/user.xls");
		HSSFWorkbook wb = new HSSFWorkbook(filein);
		HSSFSheet sheet = wb.getSheetAt(0);
                        HSSFRow row=null;
                        boolean find;
                  for(int i=0;i<=sheet.getLastRowNum();i++)
                    {
                       row = sheet.getRow((short) i);
                       if(name.equals(row.getCell(0).toString()))
                          { find=true;  break;}
                    }
			Cell cell = row.getCell(1);

		if (password.equals(row.getCell(1).toString())){
			return true;
		}
		else{
		return false;}
	}
}
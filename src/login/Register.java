package login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;


/*
 * 对传入的数据进行处理,校对密码,存入资料,提示操作成功或者失败,成功之后返回登录窗口
 *
 **/
public class Register{
	public boolean Register1(String name,String password,String password2,String question,String answer ) throws IOException{
             if(name.isEmpty()||password.isEmpty()||password2.isEmpty()||question.isEmpty()||answer.isEmpty())
               {
                 JOptionPane s = new JOptionPane();
                 s.showMessageDialog(null, "请填写全部资料");
                 return false;
                 }
             else if(password.equals ( password2)){
			FileInputStream filein = new FileInputStream(new File("db/user.xls"));
			HSSFWorkbook wb = new HSSFWorkbook(filein);
			CreationHelper createHelper = wb.getCreationHelper();

			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);

			row.createCell(0).setCellValue(
					createHelper.createRichTextString(name));
			row.createCell(1).setCellValue(
					createHelper.createRichTextString(password));
			row.createCell(2).setCellValue(
					createHelper.createRichTextString(question));
			row.createCell(3).setCellValue(
					createHelper.createRichTextString(answer));

			FileOutputStream fileOut = new FileOutputStream("db/user.xls");
			wb.write(fileOut);
			fileOut.close();

			JOptionPane s = new JOptionPane();
			s.showMessageDialog(null, "注册成功");
			LoginWin ee = new LoginWin();
                        return true;
		}else{
			JOptionPane s = new JOptionPane();
			s.showMessageDialog(null, "两次密码不相同");
                        return false;
		}
             
	}
	/*
	public static void main(String args[]) throws IOException{
		new Register();
		//FileInputStream filein1 = new FileInputStream(new File("account_template.xls"));

		HSSFWorkbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		CreationHelper createHelper = wb.getCreationHelper();
		FileOutputStream fileOut1 = new FileOutputStream("account.xls");
		wb.write(fileOut1);
		fileOut1.close();
	}*/
}
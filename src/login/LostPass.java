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

/*函数实现功能：对传入的密码提示问题的答案与 存在Excel的进行校对，对重新设置的密码进行校对，
 * 如果没错误，弹出对话框告诉修改成功则将新密码覆盖旧密码并且实例化登录窗口，销毁当前窗口
 * */
public class LostPass {
	public boolean LostPass1(int i,String name,String answer,String password,String password2 ) throws IOException{
            if(name.isEmpty()||answer.isEmpty()||password.isEmpty()||password2.isEmpty())
            {
                 JOptionPane ss = new JOptionPane();
		 ss.showMessageDialog(null, "请填写全部资料");
                 return false;
            }
                        FileInputStream filein = new FileInputStream(new File("db/user.xls"));
			HSSFWorkbook wb = new HSSFWorkbook(filein);
			CreationHelper createHelper = wb.getCreationHelper();
                        HSSFSheet sheet = wb.getSheetAt(0);
                        HSSFRow row=sheet.getRow(i);
                        boolean find = false;
               
             if(password.equals(password2))
                     {

			if(answer.equals(row.getCell(3).toString())){

				row.createCell(1).setCellValue(
						createHelper.createRichTextString(password));			     

				FileOutputStream fileOut = new FileOutputStream("db/user.xls");
				wb.write(fileOut);
				fileOut.close();
				JOptionPane s = new JOptionPane();
				s.showMessageDialog(null, "修改成功");
                                return true;
			}else{
				JOptionPane s = new JOptionPane();
				s.showMessageDialog(null, "答案错误");
			}
		}else{
			JOptionPane s = new JOptionPane();
			s.showMessageDialog(null, "两次密码不相同");
		}
                return false;
	}

}

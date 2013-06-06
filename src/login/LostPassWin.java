package login;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;


public class LostPassWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JTextField jTextField = null;
	private JPasswordField jPassword = null;
	private JPasswordField password1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField2 = null;
	private JLabel jLabel4 = null;
        private String name = null;
        private boolean find = false;
        private int row1;
	/**
		构造方法
	 * @throws IOException
	 */
	public LostPassWin(String name) throws IOException {
		super();
                this.name=name;
		initialize();
                if(!find)
                {
                    JOptionPane s = new JOptionPane();
                    s.showMessageDialog(null, "找不到用户资料");
                    dispose();
                    new LoginWin();	
                }
	}

	/**
	 * 构造方法设置
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.setResizable(false);//禁止拖动大小
		this.setSize(400, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("密码修改，密码找回");
		//设置背景
		jContentPane.setOpaque(false);
		ImageIcon img = new ImageIcon("101103213271200baf1d78262f.jpg");
		JLabel background = new JLabel(img);this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();    // 定义工具包
	    Dimension screenSize = kit.getScreenSize();   // 获取屏幕的尺寸
	    int screenWidth = screenSize.width/2;         // 获取屏幕的宽
	    int screenHeight = screenSize.height/2;       // 获取屏幕的高
	    int height = this.getHeight();
	    int width = this.getWidth();
	    setLocation(screenWidth-width/2, screenHeight-height/2);
	}

	/**
	 * 容器组件设置
	 * @throws IOException
	 */
	private JPanel getJContentPane() throws IOException {
		if (jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(70, 130, 276, 18));
			jLabel4.setText("回答密码提示问题后输入要修改的新密码：");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(70, 190, 100, 18));
			jLabel6.setText("新密码确认：");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(70, 160, 100, 18));
			jLabel5.setText("新密码：");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(70, 100, 100, 18));
			jLabel3.setText("密码问题答案：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(70, 70, 100, 18));
			jLabel2.setText("密码提示问题：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(70, 40, 100, 18));
			jLabel1.setText("用户名：");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getPassword(), null);
			jContentPane.add(getPassword1(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel4, null);
		}
		return jContentPane;
	}

	/**
	 * 用户名填写框的get方法
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(200, 40, 150, 25));
			jTextField.setText(name);
                    jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * 密码填写框的get方法
	 */
	private JPasswordField getPassword() {
		if (jPassword == null) {
			jPassword = new JPasswordField();
			jPassword.setBounds(new Rectangle(200, 160, 150, 25));

		}
		return jPassword;
	}

	/**
	 * This method initializes password1
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getPassword1() {
		if (password1 == null) {
			password1 = new JPasswordField();
			password1.setBounds(new Rectangle(200, 190, 150, 25));
		}
		return password1;
	}

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(114, 239, 66, 26));
			jButton.setText("修改");
			jButton.addActionListener(new ActionListener() {          // 捕获按钮的ActionEvent事件
				public void actionPerformed(ActionEvent e) {
					String p1 = String.valueOf( jPassword.getPassword() );
					String p2 = String.valueOf( password1.getPassword() );
					String answer = jTextField2.getText();
                                        String name = jTextField.getText();
					try {
						LostPass ee = new LostPass();
						if(ee.LostPass1(row1,name,answer,p1,p2))
                                                {
                                                    dispose();
                                                    new LoginWin();
                                                }


					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(220, 239, 66, 28));
			jButton1.setText("取消");
			jButton1.addMouseListener(											//加入鼠标监听器
					new MouseAdapter(){											//适配器
						public void mouseClicked(MouseEvent e){
							new LoginWin();					//实例化登录界面
							dispose();											//关闭当前页面
						}
					});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField1
	 *
	 * @return javax.swing.JTextField
	 * @throws IOException
	 */
	private JTextField getJTextField1() throws IOException {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(200, 70, 150, 25));
			   FileInputStream filein = new FileInputStream(new File("db/user.xls"));
			HSSFWorkbook wb = new HSSFWorkbook(filein);
                        HSSFSheet sheet = wb.getSheetAt(0);
                        HSSFRow row=null;
                        
                  for(int i=0;i<=sheet.getLastRowNum();i++)
                    {
                       row = sheet.getRow((short) i);
                       if(name.equals(row.getCell(0).toString()))
                          { find=true;  row1=row.getRowNum(); break;}
                    }
			Cell cell = row.getCell(2);
			jTextField1.setText(
					cell.getRichStringCellValue().getString()
					);
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField2
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(200, 100, 150, 25));
		}
		return jTextField2;
	}
}
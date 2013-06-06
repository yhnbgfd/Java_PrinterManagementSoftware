package login;

import desktopapplicationtest.DesktopApplicationTest;
import desktopapplicationtest.DesktopApplicationTestView;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdesktop.application.Application;



public class LoginWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField jTextField = null;
	private JPasswordField jPasswordField = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JCheckBox jCheckBox = null;
	/**
	 * 构造函数
	 */
	 LoginWin() {
		super();
		initialize();
	}
	/**
	 * 构造函数里面的初始化应用程序
	 */
	private void initialize() {
		this.setResizable(false);
		this.setSize(400, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("登录");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();    		// 定义工具包
	    Dimension screenSize = kit.getScreenSize();   		// 获取屏幕的尺寸
	    int screenWidth = screenSize.width/2;         		// 获取屏幕的宽
	    int screenHeight = screenSize.height/2;       		// 获取屏幕的高
	    int height = this.getHeight();
	    int width = this.getWidth();
	    setLocation(screenWidth-width/2, screenHeight-height/2);
	}
	/**
		设置各种构件的初始参数
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(59, 133, 58, 18));
			jLabel1.setText("  密码：");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(58, 103, 60, 18));
			jLabel.setText("用户名：");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			//设置背景
			jContentPane.setOpaque(false);
			ImageIcon img = new ImageIcon("data/login.jpg");
			JLabel background = new JLabel(img);this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
			background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJPasswordField(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			//jContentPane.add(getJButton2(), null);  注册按钮
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getJCheckBox(), null);
		}
		return jContentPane;
	}
	/**
	 * 设置用户名框的get方法
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(120, 100, 150, 25));
		}
		return jTextField;
	}
	/**
	 * 设置密码框的get方法
	 */
	private JTextField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(new Rectangle(120, 130, 150, 25));
		}
		return jPasswordField;
	}
	/**
	 * 设置登录按钮的get方法
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(114, 189, 66, 26));
			jButton.setText("登录");
			jButton.addActionListener(new ActionListener() {          // 捕获按钮的ActionEvent事件
				public void actionPerformed(ActionEvent e) {
					//System.out.println("ActionEvent!OOXX");
					//do
					String p2 = String.valueOf( jPasswordField.getPassword() );
					String p1 = jTextField.getText();
					try {
					Login ee = new Login();
					if(ee.Login(p1,p2)){
						//MainFrame aa = new MainFrame();//实例化主窗口
                                                // DesktopApplicationTest dat = new DesktopApplicationTest();
                                               DesktopApplicationTestImpl.launch(DesktopApplicationTest.class, null);                                               
						dispose();
					}
					else{
						JOptionPane s = new JOptionPane();
						s.showMessageDialog(null, "密码错误");
					}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

                class DesktopApplicationTestImpl extends DesktopApplicationTest {

                    public DesktopApplicationTestImpl() {
                    }

                    @Override
                    protected void startup() {
                        show(new DesktopApplicationTestView(Application.getInstance(DesktopApplicationTest.class)));
                    }

                    @Override
                    protected void configureWindow(java.awt.Window root) {
                    }
                }
				});
		}
		return jButton;
	}
	/**
	 * 设置取消按钮的get方法
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(200, 189, 66, 28));
			jButton1.setText("取消");
			jButton1.addMouseListener(											//加入鼠标监听器
					new MouseAdapter(){											//适配器
						public void mouseClicked(MouseEvent e){
							System.exit(0);										//关闭程序
						}
					});
		}
		return jButton1;
	}
	/**
	 * 设置注册用户按钮的get方法
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(250, 98, 100, 30));
			jButton2.setText("注册帐户");
			jButton2.setBorderPainted (false);                         			//隐藏边框
			jButton2.setContentAreaFilled(false);                      			//隐藏背景
			jButton2.addMouseListener(											//加入鼠标监听器
					new MouseAdapter(){											//适配器
						public void mouseClicked(MouseEvent e){
							RegisterWin ee = new RegisterWin();					//实例化注册界面
							dispose();											//关闭当前页面
						}
					});
		}
		return jButton2;
	}
	/**
	 * 设置忘记密码按钮的get方法
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(250, 128, 130, 30));
			jButton3.setText("忘记/修改密码");
			jButton3.setBorderPainted (false);									//隐藏框架
			jButton3.setContentAreaFilled(false);								//隐藏背景
			jButton3.addMouseListener(											//加入鼠标监听器
					new MouseAdapter(){											//适配器
						public void mouseClicked(MouseEvent e){
							try {
								LostPassWin a1 = new LostPassWin(jTextField.getText());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}					//实例化找回密码界面
							dispose();											//关闭当前页面
						}
					});
		}
		return jButton3;
	}
	/**
	 * 设置复选框记住密码的get方法
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(100, 160, 83, 21));
			jCheckBox.setText("记住密码");
			jCheckBox.setContentAreaFilled(false);
		}
		return jCheckBox;
	}
	public static void main(String[] args){
           
        try {
            /*
            FileInputStream filein = new FileInputStream("data/Information/用户资料.xls");
            HSSFWorkbook wb = new HSSFWorkbook(filein);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = sheet.getRow(1);
            if((row.getCell(0).toString())){
            }
             */
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows风格
            new LoginWin();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginWin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(LoginWin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LoginWin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LoginWin.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}

package login;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class RegisterWin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton confirmationButton = null;
	private JButton cancelButton = null;
	private JLabel userNameLabel = null;
	private JLabel passwordLabel = null;
	private JLabel cPasswordLabel = null;
	private JTextField userTextField = null;
	private JPasswordField jPasswordField = null;
	private JPasswordField jPasswordField1 = null;
	private JLabel tishiLabel = null;
	private JLabel answerLabel = null;
	private JTextField questionTextField = null;
	private JTextField answerTextFiedl = null;

	/**
	 *构造函数
	 */
	public RegisterWin() {
		super();
		initialize();
	}

	/**
		构造方法
	 */
	private void initialize() {
		this.setResizable(false);							//禁止拖动大小
		this.setSize(400, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("注册");

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
	 *JPanel参数设置
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			answerLabel = new JLabel();
			answerLabel.setBounds(new Rectangle(80, 200, 93, 26));
			answerLabel.setText("密码问题答案:");
			tishiLabel = new JLabel();
			tishiLabel.setBounds(new Rectangle(80, 160, 93, 26));
			tishiLabel.setText("密码提示问题:");
			cPasswordLabel = new JLabel();
			cPasswordLabel.setBounds(new Rectangle(80, 120, 93, 26));
			cPasswordLabel.setText("确认密码:");
			passwordLabel = new JLabel();
			passwordLabel.setBounds(new Rectangle(80, 80, 93, 26));
			passwordLabel.setText("密码:");
			userNameLabel = new JLabel();
			userNameLabel.setBounds(new Rectangle(80, 40, 93, 26));
			userNameLabel.setText("用户名:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getConfirmationButton(), null);
			jContentPane.add(getCancelButton(), null);
			jContentPane.add(userNameLabel, null);
			jContentPane.add(passwordLabel, null);
			jContentPane.add(cPasswordLabel, null);
			jContentPane.add(getUserTextField(), null);
			jContentPane.add(getJPasswordField(), null);
			jContentPane.add(getJPasswordField1(), null);
			jContentPane.add(tishiLabel, null);
			jContentPane.add(answerLabel, null);
			jContentPane.add(getquestionTextField(), null);
			jContentPane.add(getanswerTextFiedl(), null);
		}
		return jContentPane;
	}

	/**
	 *确认按钮的get方法
	 */
	private JButton getConfirmationButton() {
		if (confirmationButton == null) {
			confirmationButton = new JButton();
			confirmationButton.setBounds(new Rectangle(114, 239, 66, 26));
			confirmationButton.setText("确认");
			confirmationButton.addActionListener(new ActionListener() {          // 捕获按钮的ActionEvent事件
				public void actionPerformed(ActionEvent e) {
					//System.out.println("ActionEvent!OOXX");
					//do
					String password1 = String.valueOf( jPasswordField.getPassword() );
					String password2 = String.valueOf( jPasswordField1.getPassword() );
					String name = userTextField.getText();
					String question = questionTextField.getText();
					String answer = answerTextFiedl.getText();
					try {
					Register ee = new Register();
					if(ee.Register1(name,password1,password2,question,answer))
					  dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				});
		}
		return confirmationButton;
	}

	/**
	 * 取消按钮的get方法
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setBounds(new Rectangle(220, 239, 66, 26));
			cancelButton.setText("取消");
			cancelButton.addMouseListener(											//加入鼠标监听器
					new MouseAdapter(){											//适配器
						public void mouseClicked(MouseEvent e){
							LoginWin ee = new LoginWin();					//实例化登录界面
							dispose();											//关闭当前页面
						}
					});
		}
		return cancelButton;
	}

	/**
	 * 登录名框的get方法
	 */
	private JTextField getUserTextField() {
		if (userTextField == null) {
			userTextField = new JTextField();
			userTextField.setBounds(new Rectangle(200, 40, 150, 25));
			userTextField.setText("admin");
		}
		return userTextField;
	}

	/**
	 * 密码框的get方法
	 */
	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(new Rectangle(200, 120, 150, 25));
		}
		return jPasswordField;
	}

	/**
	 * 确认密码框的get方法
	 */
	private JPasswordField getJPasswordField1() {
		if (jPasswordField1 == null) {
			jPasswordField1 = new JPasswordField();
			jPasswordField1.setBounds(new Rectangle(200, 80, 150, 25));
			jPasswordField1.setText("");
		}
		return jPasswordField1;
	}

	/**
	 * 设置提示问题框的get方法
	 */
	private JTextField getquestionTextField() {
		if (questionTextField == null) {
			questionTextField = new JTextField();
			questionTextField.setBounds(new Rectangle(200, 200, 150, 25));
		}
		return questionTextField;
	}

	/**
	 * 提示问题答案的get方法
	 */
	private JTextField getanswerTextFiedl() {
		if (answerTextFiedl == null) {
			answerTextFiedl = new JTextField();
			answerTextFiedl.setBounds(new Rectangle(200, 160, 150, 25));
		}
		return answerTextFiedl;
	}
}

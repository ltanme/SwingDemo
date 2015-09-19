package com.winfan.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.winfan.dao.UserDao;
import com.winfan.model.User;
import com.winfan.util.DbUtil;
import com.winfan.util.StringUtil;


public class LonOnFrm extends JFrame {

	private JPanel contentPane;
	private JTextField userNameTxt;
	private JPasswordField passwordTxt;
    private DbUtil dbUtil = new DbUtil();
    private UserDao userDao = new UserDao();
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LonOnFrm frame = new LonOnFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LonOnFrm() {
		//改变系统默认字体
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		
		setResizable(false);
		setTitle("管理员登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("图书管理系统");
		lblNewLabel.setIcon(new ImageIcon(LonOnFrm.class.getResource("/images/logo.png")));
		
		JLabel label = new JLabel("用户名:");
		label.setIcon(new ImageIcon(LonOnFrm.class.getResource("/images/userName.png")));
		
		userNameTxt = new JTextField();
		userNameTxt.setColumns(10);
		
		JLabel label_1 = new JLabel("密   码：");
		label_1.setIcon(new ImageIcon(LonOnFrm.class.getResource("/images/password.png")));
		
		passwordTxt = new JPasswordField();
		
		JButton button = new JButton("登录");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginActionPerformed(e);
			}
		});
		button.setIcon(new ImageIcon(LonOnFrm.class.getResource("/images/login.png")));
		
		JButton button_1 = new JButton("重置");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetValueActionPerformed(e);
			}
		});
		button_1.setIcon(new ImageIcon(LonOnFrm.class.getResource("/images/reset.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(68)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(44)
							.addComponent(button)
							.addGap(85)
							.addComponent(button_1))
						.addComponent(label_1)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(passwordTxt, Alignment.LEADING)
									.addComponent(userNameTxt, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(127, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(19)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(passwordTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(button_1))
					.addGap(32))
		);
		contentPane.setLayout(gl_contentPane);
		this.setLocationRelativeTo(null); //设置jframe居中显示 
	}

	private void loginActionPerformed(ActionEvent evt) {
	  String userName = this.userNameTxt.getText();
	  String password = new String(this.passwordTxt.getPassword());
	  if(StringUtil.isEmpty(userName)){
		  JOptionPane.showMessageDialog(null, "用户名不能为空！");
		  return;
	  }
	  
	  if(StringUtil.isEmpty(password)){
		  JOptionPane.showMessageDialog(null, "密码不能空！");
		  return;
	  }
	  
	 User user = new User(userName,password);
	  Connection con = null;
	   try {
		con = dbUtil.getCon();
		User current = userDao.login(con, user);
		if(current!=null){
			//JOptionPane.showMessageDialog(null, "登录成功");
			dispose();//销毁当前窗口
			new MainFrm().setVisible(true);//设置主窗口为显示状态
		}else{
			JOptionPane.showMessageDialog(null, "登录失败，用户名和密码错误！");
		}
	} catch (Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "登录失败，数据库连接失败");
	}finally {
		try {
			dbUtil.closeCon(con);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	   
	}

	private void resetValueActionPerformed(ActionEvent evt) {
		this.userNameTxt.setText("");
		this.passwordTxt.setText("");
	}
}

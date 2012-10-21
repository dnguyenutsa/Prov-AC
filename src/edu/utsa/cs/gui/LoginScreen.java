package edu.utsa.cs.gui;


import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class LoginScreen{
	JButton SUBMIT;
	JFrame f;
	JLabel label1,label2;
	final JTextField text1;
	final JPasswordField text2;
	LoginScreen(){
		f=new JFrame();
		f.getContentPane().setLayout(null);
		label1 = new JLabel();
		label1.setText("UserName:");
		label1.setBounds(400,50,100,20);
		text1 = new JTextField(25);
		text1.setBounds(500,50,100,20);
		label2 = new JLabel();
		label2.setText("Password:");
		label2.setBounds(400,80,100,20);
		text2 = new JPasswordField(25);
		text2.setBounds(500,80,100,20);
		SUBMIT=new JButton("Login");
		SUBMIT.setBounds(500,110,100,20);

		f.add(label1);
		f.add(text1);
		f.add(label2);
		f.add(text2);
		f.add(SUBMIT);
		f.setSize(1024,768);
		f.setVisible(true);
		SUBMIT.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String value1=text1.getText();
				String value2=text2.getText();
				String user1="";
				String pass1="";
				try{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "");
					Statement st = con.createStatement();
					ResultSet res = st.executeQuery("SELECT * FROM login where username='"+value1+"' and password='"+value2+"'");
					while (res.next()) {
						user1 = res.getString("username");
						pass1 = res.getString("password");
					}
					if(!value1.equals(user1) && !value2.equals(pass1)) {
						JOptionPane.showMessageDialog(null,"Incorrect login or password","Error",JOptionPane.ERROR_MESSAGE);
						text1.setText("");
						text2.setText("");
					}
					else if(value1.equals("")){
						JOptionPane.showMessageDialog(null,"Please Enter Username");
					}
					else if(value2.equals("")){
						JOptionPane.showMessageDialog(null,"Please Enter Password");
					}
					else if(value2.length()<6){
						JOptionPane.showMessageDialog(null,"Password should consist atleast 6 characters!");
					}

					else{

						JOptionPane.showMessageDialog(null,"Login Successful");
					}

				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		});
	}
	public static void main(String arg[])	{
		LoginScreen frame=new LoginScreen();
	}
}

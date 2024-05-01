import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class LoginFrame extends JFrame {

	//private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField txtField;
	private JPasswordField passwordField;
	public String userName, password;
	private JLabel lblNewLabel;


	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	

	
	// TASARIM KISMI //
	
	public LoginFrame() {
		setResizable(false);
		setTitle("Login Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(747, 300, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		panel.setBounds(0, 0, 230, 261);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/logopng2.png")));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(29, 23, 170, 194);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("A3SOFT");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1.setBounds(143, 236, 77, 14);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(245, 245, 245));
		panel_1.setBounds(230, 0, 254, 261);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblGiri = new JLabel("Yönetici Girişi");
		lblGiri.setBounds(37, 26, 185, 29);
		lblGiri.setFont(new Font("Verdana", Font.BOLD, 24));
		panel_1.add(lblGiri);
		
		txtField = new JTextField();
		txtField.setFont(new Font("Verdana", Font.PLAIN, 11));
		txtField.setBounds(26, 103, 121, 20);
		panel_1.add(txtField);
		txtField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Verdana", Font.PLAIN, 11));
		passwordField.setBounds(26, 159, 121, 20);
		panel_1.add(passwordField);

		
		JButton btnNewButton = new JButton("Giriş Yap ");
		btnNewButton.setBackground(new Color(245, 245, 245));
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				/*BUTONA TIKLANIRSA*/	
				userName = txtField.getText();
				password = new String(passwordField.getPassword()); // 
				try {
					
		            // CnDb sınıfından bir nesne oluştur ve bağlantıyı aç
		            CnDb connection = new CnDb();
		    		connection.connection();

		            // SQL sorgusunu oluştur
		            String sql = "SELECT * FROM adminler WHERE username = ? AND pass = ? "; 
		            PreparedStatement statement = connection.con.prepareStatement(sql);
		            statement.setString(1, userName);
		            statement.setString(2, password);

		            // Sorguyu çalıştır ve sonuçları al
		            ResultSet results = statement.executeQuery();
		            
		            // Eğer sonuçlar varsa, kullanıcı adı ve şifre doğru
		            if (results.next()) {
		                // Kullanıcı adı ve şifre doğru
		            	dispose(); //hide(); 
		            	MainFrame mf = new MainFrame();
		            	mf.show();
		            } else {
		                // Kullanıcı adı veya şifre yanlış
		                JOptionPane.showMessageDialog(null, "Hatalı kullanıcı adı veya parola!","Message",JOptionPane.ERROR_MESSAGE);
		            }

		            // Kaynakları temizle
		            results.close();
		            statement.close();
		            connection.con.close();
		            
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
				
			}
		});
		
		
		btnNewButton.setBounds(155, 201, 89, 23);
		panel_1.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Kullanıcı Adı");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(26, 78, 79, 14);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Parola");
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(26, 134, 46, 14);
		panel_1.add(lblNewLabel_3);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Göster");
		chckbxNewCheckBox.addActionListener(new ActionListener() { 			/// PASSWORDFIELD CHECKBOX
			public void actionPerformed(ActionEvent e) {
				
				if (chckbxNewCheckBox.isSelected()) {
	    			passwordField.setEchoChar((char)0);
	    			
	    		}else  {
	    			passwordField.setEchoChar('●');
	    		}
			}
		});
		chckbxNewCheckBox.setBackground(new Color(245, 245, 245));
		chckbxNewCheckBox.setFont(new Font("Verdana", Font.PLAIN, 11));
		chckbxNewCheckBox.setBounds(26, 186, 97, 23);
		panel_1.add(chckbxNewCheckBox);
		
		lblNewLabel = new JLabel("image");
	
		
	}
}

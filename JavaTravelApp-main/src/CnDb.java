import java.sql.*;
import javax.swing.JOptionPane;


public class CnDb {
	
	public Connection con;
	public Statement stmt;
	public ResultSet rst;
	public PreparedStatement psmt;
	
	private String kullanici_adi="root";
	private String parola="";
    private String db_ismi="a3";
    private String host="localhost";
    private int port=3306;
	
	public void connection() {
		try {
			String url="jdbc:mysql://"+host+":"+port+"/"+db_ismi; 
			// Bağlantı adresini belirler
			con = DriverManager.getConnection(url,kullanici_adi,parola);	
			//DriverManager ile bağlantı adresine bağlanır
			//JOptionPane.showMessageDialog(null, "Bağlantı Başarılı!","Message",JOptionPane.INFORMATION_MESSAGE);	
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Sunucu Hatası!","Message",JOptionPane.ERROR_MESSAGE);	
			// Eğer hata alınırsa mesaj kutusu ile bildirir
		}
	}
    public Connection getConnection() {
        return con;
    }// Diğer classlardan Connection u kullanak için get olarak kullanılır
}









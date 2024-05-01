import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLDataException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Cursor;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JDateChooser;
import com.toedter.components.JLocaleChooser;
import com.toedter.calendar.JDayChooser;
import com.mysql.cj.xdevapi.SqlUpdateResult;
import com.toedter.calendar.JCalendar;
import java.awt.Component;

public class MainFrame extends JFrame {

	//private static final long serialVersionUID = 1L;
	
	//
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	JTable table_1 = new JTable();
	JScrollPane scrollPane = new JScrollPane();
	
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JPanel turlarPanel;
	private JComboBox combobox_1;

	/// QUERYLER
	private String TurQuery = "SELECT * FROM turlar";
	private String PersonelQuery = "SELECT * FROM personeller";
	private String MusteriQuery = "SELECT * FROM musteriler";
	private String BiletQuery = "SELECT * FROM biletler";
	private String TasitQuery = "SELECT * FROM tasitlar";
	
	private int buttonTurn = 1;
	private String buttonSil = "Turlar";
	
	String[] allColsTu = { "turid", "tasitid", "harakettime", "destination" };
	String[] allColsPe = { "personelid", "personelname", "personelsurname", "personelsalary","personelbirthdate", "personeljoindate" };
	String[] allColsMu = { "musteriid", "musteriname", "musterisurname", "musterination", "musteriemail", "musteripass"};
	String[] allColsBi = { "biletid", "bilettype", "biletprice", "turid","musteriid", "tasitid", "personelid" };
	String[] allColsTa = { "tasitid", "tasittype", "tasitplate"};
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	public void populateTable(CnDb connection, String query, JPanel turlarPanel, JTable table_1, String[] colNames) {
		try {
			
			connection.stmt = connection.con.createStatement();
			connection.rst = connection.stmt.executeQuery(query);

			
			DefaultTableModel model = new DefaultTableModel();
			ResultSetMetaData metaData = connection.rst.getMetaData();

			int cols = metaData.getColumnCount();
			String[] colname = new String[cols];
			for (int i = 0; i < cols; i++) {
				colname[i] = metaData.getColumnName(i + 1);
				model.setColumnIdentifiers(colname);
			}

			// Tabloya satır ekler
			while (connection.rst.next()) {
				Object[] rowData = new Object[cols];
				for (int i = 0; i < cols; i++) {
					rowData[i] = connection.rst.getString(colNames[i]);
				}
				model.addRow(rowData);
			}
			
			table_1.setFont(new Font("Verdana", Font.PLAIN, 11));

			scrollPane.setViewportView(table_1);

			// table_1'i düzenler
			table_1.setModel(model);

		} catch (Exception e) {
			System.out.println("HATA VERILER CEKILEMEDI.");
			System.out.println(e);
		} finally {
			try {
				if (connection.con != null) {
					connection.con.close();
					System.out.println("Fonksiyondaki baglantı kapandı");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/// GÜNCELLE FONKSIYONLARI
	
	public void guncellePersonel() {
		String query = "UPDATE personeller SET personelid = ? ,personelname = ? ,personelsurname = ? ,personelsalary = ? ,personelbirthdate = ? ,personeljoindate = ? WHERE personelid = ?;";
		
		String personelid = textField_1.getText();
		String personelname = textField_2.getText();
		String personelsurname = textField_3.getText();
		String personelsalary = textField_4.getText();
		String personelbirthdate = textField_5.getText();;
		String personeljoindate = textField_6.getText();;

		// Database bağlantısı
		CnDb connection = new CnDb();
		connection.connection();
		
		// PreparedStatement kullanara query execute edilir
		try {
			System.out.println("Personeller guncelle TRY'A GIRDI");
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, personelid); //query nin içindeki ? işaretleri için veri girer
			pstmt.setString(2, personelname);
			pstmt.setString(3, personelsurname);
			pstmt.setString(4, personelsalary);
			pstmt.setString(5, personelbirthdate);
			pstmt.setString(6, personeljoindate);
			pstmt.setString(7, personelid);
			System.out.println("JOINDATE BITIRDI");

			pstmt.executeUpdate(); // query yi çalıştırır
			System.out.println("TRY BITIRDI");

		} catch (SQLException e2) {
			System.out.println("CATCH'E GIRDI");

			e2.printStackTrace();
		} finally {
			populateTable(connection, PersonelQuery, turlarPanel, table_1, allColsPe);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, PersonelQuery, turlarPanel, table_1, allColsPe);
		
	}
	
	public void guncelleTur() {
		String query = "UPDATE turlar SET turid = ? ,tasitid = ? ,harakettime = ? ,destination = ?  WHERE turid = ?;";
		
		String turid = textField_1.getText();
		String tasitid = textField_2.getText();
		String harakettime = textField_3.getText();
		String destination = textField_4.getText();

		// Database bağlantısı
		CnDb connection = new CnDb();
		connection.connection();
		
		// PreparedStatement kullanara query execute edilir
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, turid); //query nin içindeki ? işaretleri için veri girer
			pstmt.setString(2, tasitid);
			pstmt.setString(3, harakettime);
			pstmt.setString(4, destination);
			pstmt.setString(5, turid);
			pstmt.executeUpdate(); // query yi çalıştırır
		} catch (SQLException e2) {
			e2.printStackTrace();
			
		} finally {
			populateTable(connection, TurQuery, turlarPanel, table_1, allColsTu);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, TurQuery, turlarPanel, table_1, allColsTu);
	}
	
	public void guncelleMusteri() {
		System.out.println("ButtonTurn = "+buttonTurn);

    	// EKLE BUTONUNA TIKLANDIGI ZAMAN
		String musteriid = textField_1.getText();
		String musteriname = textField_2.getText();
		String musterisurname = textField_3.getText();
		String musterination = textField_4.getText();
		String musteriemail = textField_5.getText();
		String musteripass = textField_6.getText();
		
		// Database bağlantısı
		CnDb connection = new CnDb();
		connection.connection();

		// sql sorgusu
		String query = "UPDATE musteriler SET musteriid = ? ,musteriname = ? ,musterisurname = ? ,musterination = ?  ,musteriemail = ? ,musteriemail = ? WHERE turid = ?;";
		// PreparedStatement kullanara query execute edilir
		
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, musteriid); //query nin içindeki ? işaretleri için veri girer
			pstmt.setString(2, musteriname);
			pstmt.setString(3, musterisurname);
			pstmt.setString(4, musterination);
			pstmt.setString(5, musteriemail);
			pstmt.setString(6, musteripass);
			pstmt.setString(1, musteriid);
			pstmt.executeUpdate(); // query yi çalıştırır
			System.out.println("");
		} catch (SQLException e2) {
			e2.printStackTrace();
			
		} finally {
			populateTable(connection, MusteriQuery, turlarPanel, table_1, allColsMu);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, MusteriQuery, turlarPanel, table_1, allColsMu);
	}
	
	public void guncelleTasit(String turu) {
		
		String tasitid = textField_1.getText();
		String tasittype = turu;
		String tasitplate = textField_3.getText();
		
		// Database bağlantısı
		CnDb connection = new CnDb();
		connection.connection();

		// sql sorgusu
		String query = "UPDATE tasitlar SET  tasittype = ? ,tasitplate = ? WHERE tasitid = ?;";
		// PreparedStatement kullanara query execute edilir
		
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, tasittype);
			pstmt.setString(2, tasitplate);
			pstmt.setString(3, tasitid);
			pstmt.executeUpdate(); // query yi çalıştırır
		} catch (Exception e2) {
			System.out.println(e2);
		} finally {
			populateTable(connection, TasitQuery, turlarPanel, table_1, allColsTa);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, TasitQuery, turlarPanel, table_1, allColsTa);
	}
	
	public void guncelleBilet(String type) {
		
		String biletid = textField_1.getText();
		String bilettype = type;
		String biletprice = textField_3.getText();
		String turid = textField_4.getText();
		String musteriid  = textField_5.getText();
		String tasitid  = textField_6.getText();
		String personelid   = textField_7.getText();
		
		// Veritabanı bağlantısı
		CnDb connection = new CnDb();
		connection.connection();

		// SQL query
		String query = "UPDATE biletler SET bilettype = ? ,biletprice = ? ,turid = ? ,musteriid = ? ,tasitid = ? ,personelid = ? WHERE biletid = ?;";
		// PreparedStatement kullanara query execute edilir
		
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query); 
			pstmt.setString(1, bilettype);
			pstmt.setString(2, biletprice);
			pstmt.setString(3, turid);
			pstmt.setString(4, musteriid);
			pstmt.setString(5, tasitid);
			pstmt.setString(6, personelid);
			pstmt.setString(7, biletid);
			pstmt.executeUpdate(); // query yi çalıştırır
		} catch (SQLException e2) {
			e2.printStackTrace();
			
		} finally {
			populateTable(connection, BiletQuery, turlarPanel, table_1, allColsBi);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, BiletQuery, turlarPanel, table_1, allColsBi);
		
	}
	
	
	/// EKLE FONKSIYONLARI
	
	public void eklePersonel() {
		System.out.println("ButtonTurn = "+buttonTurn);
    	System.out.println("Personeller ekle butona tıkaldbı");

		// EKLE BUTONUNA TIKLANDIGI ZAMAN
		String personelid = textField_1.getText();
		String personelname = textField_2.getText();
		String personelsurname = textField_3.getText();
		String personelsalary = textField_4.getText();
		String personelbirthdate = textField_5.getText();;
		String personeljoindate = textField_6.getText();;

		// Database bağlantısı
		CnDb connection = new CnDb();
		connection.connection();

		// sql sorgusu
		String query = "INSERT INTO personeller (personelid, personelname, personelsurname, personelsalary, personelbirthdate, personeljoindate) VALUES (?, ?, ?, ?, ?, ?)";

		// PreparedStatement kullanara query execute edilir
		try {
			System.out.println("Personeller ekle TRY'A GIRDI");
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, personelid); //query nin içindeki ? işaretleri için veri girer
			pstmt.setString(2, personelname);
			pstmt.setString(3, personelsurname);
			pstmt.setString(4, personelsalary);
			pstmt.setString(5, personelbirthdate);
			pstmt.setString(6, personeljoindate);
			System.out.println("JOINDATE BITIRDI");

			pstmt.executeUpdate(); // query yi çalıştırır
			System.out.println("TRY BITIRDI");

		} catch (SQLException e2) {
			System.out.println("CATCH'E GIRDI");

			e2.printStackTrace();
		} finally {
			populateTable(connection, PersonelQuery, turlarPanel, table_1, allColsPe);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, PersonelQuery, turlarPanel, table_1, allColsPe);
	}
	
	public void ekleTur() {
		System.out.println("ButtonTurn = "+buttonTurn);
    	System.out.println("Turlar ekle butona tıkaldbı");

    	// EKLE BUTONUNA TIKLANDIGI ZAMAN
		String turid = textField_1.getText();
		String tasitid = textField_2.getText();
		String harakettime = textField_3.getText();
		String destination = textField_4.getText();

		// Database bağlantısı
		CnDb connection = new CnDb();
		connection.connection();

		// sql sorgusu
		String query = "INSERT INTO turlar (turid, tasitid, harakettime, destination) VALUES (?, ?, ?, ?)";
		// PreparedStatement kullanara query execute edilir
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, turid); //query nin içindeki ? işaretleri için veri girer
			pstmt.setString(2, tasitid);
			pstmt.setString(3, harakettime);
			pstmt.setString(4, destination);
			pstmt.executeUpdate(); // query yi çalıştırır
		} catch (SQLException e2) {
			e2.printStackTrace();
			
		} finally {
			populateTable(connection, TurQuery, turlarPanel, table_1, allColsTu);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, TurQuery, turlarPanel, table_1, allColsTu);
	}
	
	public void ekleMusteri() {
		System.out.println("ButtonTurn = "+buttonTurn);
    	System.out.println("Musteriler ekle butona tıkaldbı");

    	// EKLE BUTONUNA TIKLANDIGI ZAMAN
		String musteriid = textField_1.getText();
		String musteriname = textField_2.getText();
		String musterisurname = textField_3.getText();
		String musterination = textField_4.getText();
		String musteriemail = textField_5.getText();
		String musteripass = textField_6.getText();
		
		// Database bağlantısı
		CnDb connection = new CnDb();
		connection.connection();

		// sql sorgusu
		String query = "INSERT INTO musteriler (musteriid, musteriname, musterisurname, musterination, musteriemail, musteripass) VALUES (?, ?, ?, ?, ?, ?)";
		// PreparedStatement kullanara query execute edilir
		
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, musteriid); //query nin içindeki ? işaretleri için veri girer
			System.out.println("müşteriid GEÇTİ");
			pstmt.setString(2, musteriname);
			pstmt.setString(3, musterisurname);
			pstmt.setString(4, musterination);
			pstmt.setString(5, musteriemail);
			pstmt.setString(6, musteripass);
			pstmt.executeUpdate(); // query yi çalıştırır
			System.out.println("");
		} catch (SQLException e2) {
			e2.printStackTrace();
			
		} finally {
			populateTable(connection, MusteriQuery, turlarPanel, table_1, allColsMu);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, MusteriQuery, turlarPanel, table_1, allColsMu);
	}
	
	public void ekleTasit(String turu) {
		System.out.println("ButtonTurn = "+buttonTurn);
    	System.out.println("Tasit ekle butona tıkaldbı");

    	// EKLE BUTONUNA TIKLANDIGI ZAMAN
		String tasitid = textField_1.getText();
		String tasittype = turu; //textField_2.getText();
		String tasitplate = textField_3.getText();
		System.out.println("Tasıt Turu "+tasittype);

		// Database bağlantısı
		CnDb connection = new CnDb();
		connection.connection();

		// sql sorgusu
		String query = "INSERT INTO tasitlar (tasitid, tasittype, tasitplate) VALUES (?, ?, ?)";
		// PreparedStatement kullanara query execute edilir
		
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, tasitid); //query nin içindeki ? işaretleri için veri girer
			pstmt.setString(2, turu);
			pstmt.setString(3, tasitplate);
			pstmt.executeUpdate(); // query yi çalıştırır
		} catch (SQLException e2) {
			e2.printStackTrace();
			
		} finally {
			populateTable(connection, TasitQuery, turlarPanel, table_1, allColsTa);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, TasitQuery, turlarPanel, table_1, allColsTa);
	}
	
	public void ekleBilet(String type) {
		System.out.println("ButtonTurn = "+buttonTurn);
    	System.out.println("Bilet ekle butona tıkaldbı");
    	
    	// EKLE BUTONUNA TIKLANDIGI ZAMAN
		String biletid = textField_1.getText();
		String bilettype = type;
		String biletprice = textField_3.getText();
		String turid = textField_4.getText();
		String musteriid  = textField_5.getText();
		String tasitid  = textField_6.getText();
		String personelid   = textField_7.getText();
		
		// Veritabanı bağlantısı
		CnDb connection = new CnDb();
		connection.connection();

		// SQL query
		String query = "INSERT INTO biletler (biletid, bilettype, biletprice, turid, musteriid, tasitid, personelid) VALUES (?, ?, ?, ?, ?, ?, ?)";
		// PreparedStatement kullanara query execute edilir
		
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query); 
			pstmt.setString(1, biletid);//query nin içindeki ? işaretleri için veri girer
			pstmt.setString(2, bilettype);
			pstmt.setString(3, biletprice);
			pstmt.setString(4, turid);
			pstmt.setString(5, musteriid);
			pstmt.setString(6, tasitid);
			pstmt.setString(7, personelid);
			pstmt.executeUpdate(); // query yi çalıştırır
		} catch (SQLException e2) {
			e2.printStackTrace();
			
		} finally {
			populateTable(connection, BiletQuery, turlarPanel, table_1, allColsBi);
			System.out.println("FINALLY GIRDI");
		}
		populateTable(connection, BiletQuery, turlarPanel, table_1, allColsBi);
	}
	
	/// ARA FONKSIYONLARI
	
	public void araPersonel(String ara) {					// GİRİLEN ID YE GÖRE PERSONELLER TABLOSUNA SORGU ATAR
		String id = ara;
		String value = textField_9.getText();
		CnDb connection = new CnDb();
		connection.connection();
		System.out.println("ID "+id);
		System.out.println("Value "+value);	
		if(id=="Id") {
			populateTable(connection, PersonelQuery + " where personelid = "+value , turlarPanel, table_1, allColsPe);
		}else if (id=="İsim") {
			populateTable(connection, PersonelQuery + " where personelname like '%"+value+"%'" , turlarPanel, table_1, allColsPe);
		} 
	}
	
	public void araTur(String ara) {
		String id = ara;
		String value = textField_9.getText();
		CnDb connection = new CnDb();
		connection.connection();
		System.out.println("ID "+id);
		System.out.println("Value "+value);	
		if(id=="Id") {
			populateTable(connection, TurQuery + " where turid = "+value , turlarPanel, table_1, allColsTu);
		}else if (id=="Hareket Zamanı") {
			populateTable(connection, TurQuery + " where harakettime like '%"+value+"%'" , turlarPanel, table_1, allColsTu);
		} 
	}
	
	public void araMusteri(String ara) {
		String id = ara;
		String value = textField_9.getText();
		CnDb connection = new CnDb();
		connection.connection();
		System.out.println("ID "+id);
		System.out.println("Value "+value);	
		if(id=="Id") {
			populateTable(connection, MusteriQuery + " where musteriid = "+value , turlarPanel, table_1, allColsMu);
		}else if (id=="İsim") {
			populateTable(connection, MusteriQuery + " where musteriname like '%"+value+"%'", turlarPanel, table_1, allColsMu);//ara olarak ismi alır
		} 
	}
	
	public void araTasit(String ara) {
		String id = ara;
		String value = textField_9.getText();
		CnDb connection = new CnDb();
		connection.connection();
		System.out.println("ID "+id);
		System.out.println("Value "+value);	
		if(id=="Id") {
			populateTable(connection, TasitQuery + " where tasitid = "+value , turlarPanel, table_1, allColsTa);
		}else if (id=="Plaka") {
			populateTable(connection, TasitQuery + " where tasitplate like '%"+value+"%'" , turlarPanel, table_1, allColsTa);
		}else if (id=="Tür") {
			populateTable(connection, TasitQuery + " where tasittype like '%"+value+"%'" , turlarPanel, table_1, allColsTa);
		}
	}
	
	public void araBilet(String ara) {
		String id = ara;
		String value = textField_9.getText();
		CnDb connection = new CnDb();
		connection.connection();
		System.out.println("ID "+id);
		System.out.println("Value "+value);	
		if(id=="Id") {
			populateTable(connection, BiletQuery + " where biletid = "+value , turlarPanel, table_1, allColsBi);
		}else if (id=="B. Türü") {
			populateTable(connection, BiletQuery + " where bilettype like '%"+value+"%'" , turlarPanel, table_1, allColsBi);
		}
	}
	
	/// SİLME FONKSIYONLARI
	
	
	public void silTur() {				// ID NOSUNA GÖRE TURLAR TABLOSUNDAN SATIR SİLER 
		CnDb connection = new CnDb();
		connection.connection();
		String id = textField.getText();
		
		
		String query = "delete from turlar where turid = ?";
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, id); // queryde ? işaretine değeri atar
			pstmt.executeUpdate(); // query yi execute eder (çalıştırır)
			populateTable(connection, TurQuery, turlarPanel, table_1, allColsTu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void silPersonel() {			// ID NOSUNA GÖRE PERSONELLER TABLOSUNDAN SATIR SİLER 
		String id = textField.getText();
		CnDb connection = new CnDb();
		connection.connection();
		
		String query = "delete from personeller where personelid = ?";
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, id); 
			pstmt.executeUpdate(); 
			populateTable(connection, PersonelQuery, turlarPanel, table_1, allColsPe);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void silMusteri() {			// ID NOSUNA GÖRE MUSTERİLER TABLOSUNDAN SATIR SİLER 
		String id = textField.getText();
		CnDb connection = new CnDb();
		connection.connection();
		String query = "delete from musteriler where musteriid = ?";
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, id); 
			pstmt.executeUpdate(); 
			populateTable(connection, MusteriQuery, turlarPanel, table_1, allColsMu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void silBilet() {			// ID NOSUNA GÖRE BİLETLER TABLOSUNDAN SATIR SİLER 
		String id = textField.getText();
		CnDb connection = new CnDb();
		connection.connection();
		
		String query = "delete from biletler where biletid = ?";
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate(); 
			populateTable(connection, BiletQuery, turlarPanel, table_1, allColsBi);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void silTasit() {			// ID NOSUNA GÖRE SATISLAR TABLOSUNDAN SATIR SİLER
		String id = textField.getText();
		CnDb connection = new CnDb();
		connection.connection();
		
		String query = "delete from tasitlar where tasitid = ?";
		try {
			PreparedStatement pstmt = connection.con.prepareStatement(query);
			pstmt.setString(1, id); 
			pstmt.executeUpdate(); 
			populateTable(connection, TasitQuery, turlarPanel, table_1, allColsTa);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			System.out.println("FINALLY GIRDI SILTASIT");
			populateTable(connection, TasitQuery, turlarPanel, table_1, allColsTa);
		}
		
	}
	
	public void textSıfır() {			// TEXTFIELD ALANLARINI SIFIRLAR
		textField.setText(null);
		textField_1.setText(null);
		textField_2.setText(null);
		textField_3.setText(null);
		textField_4.setText(null);
		textField_5.setText(null);
		textField_6.setText(null);
		textField_7.setText(null);
		textField_8.setText(null);
	}
	
	///

	/// *******	MAIN FRAME *******
	
	public MainFrame() {
		setTitle("MainFrame");

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 744, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(0, 0, 150, 481);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setEnabled(false);
		panel_1.setBounds(160, 0, 558, 481);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JPanel turlarPanel = new JPanel();
		turlarPanel.setBackground(new Color(245, 245, 245));
		turlarPanel.setBounds(10, 11, 538, 459);
		panel_1.add(turlarPanel);
		turlarPanel.setLayout(null);

		
		textField = new JTextField();
		textField.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField.setBounds(389, 427, 49, 20);
		turlarPanel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Silinecek Id");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel.setBounds(304, 431, 75, 14);
		turlarPanel.add(lblNewLabel);

		textField_1 = new JTextField();
		textField_1.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		textField_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_1.setColumns(10);
		textField_1.setBounds(10, 40, 86, 20);
		turlarPanel.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_2.setColumns(10);
		textField_2.setBounds(106, 40, 86, 20);
		turlarPanel.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_3.setColumns(10);
		textField_3.setBounds(207, 40, 86, 20);
		turlarPanel.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_4.setColumns(10);
		textField_4.setBounds(321, 40, 86, 20);
		turlarPanel.add(textField_4);

		JLabel lblNewLabel_1 = new JLabel("Tur Id");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 15, 86, 14);
		turlarPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Taşıt Id");
		lblNewLabel_1_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(106, 15, 86, 14);
		turlarPanel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Hareket Zamanı");
		lblNewLabel_1_1_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_1_1_1.setBounds(207, 15, 95, 14);
		turlarPanel.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Varış Noktası");
		lblNewLabel_1_1_1_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_1_1_1_1.setBounds(321, 15, 86, 14);
		turlarPanel.add(lblNewLabel_1_1_1_1);
		
		
		
		scrollPane.setBounds(10, 127, 518, 288);
		turlarPanel.add(scrollPane);
		
		textField_5 = new JTextField();
		textField_5.setHorizontalAlignment(SwingConstants.LEFT);
		textField_5.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_5.setVisible(false);
		textField_5.setColumns(10);
		textField_5.setBounds(10, 96, 86, 20);
		turlarPanel.add(textField_5);
		
		JLabel lblNewLabel_1_2 = new JLabel("Tur id");
		lblNewLabel_1_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_1_2.setBounds(10, 71, 86, 14);
		lblNewLabel_1_2.setVisible(false);
		turlarPanel.add(lblNewLabel_1_2);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_6.setVisible(false);
		textField_6.setColumns(10);
		textField_6.setBounds(106, 96, 86, 20);
		turlarPanel.add(textField_6);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Taşıt Id");
		lblNewLabel_1_1_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_1_1_2.setVisible(false);
		lblNewLabel_1_1_2.setBounds(106, 71, 86, 14);
		turlarPanel.add(lblNewLabel_1_1_2);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_7.setVisible(false);
		textField_7.setColumns(10);
		textField_7.setBounds(207, 96, 86, 20);
		turlarPanel.add(textField_7);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("Hareket Zamanı");
		lblNewLabel_1_1_1_2.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_1_1_1_2.setVisible(false);
		lblNewLabel_1_1_1_2.setBounds(207, 71, 95, 14);
		turlarPanel.add(lblNewLabel_1_1_1_2);
		
		textField_8 = new JTextField();
		textField_8.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_8.setVisible(false);
		textField_8.setColumns(10);
		textField_8.setBounds(321, 96, 86, 20);
		turlarPanel.add(textField_8);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Varış Noktası");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel_1_1_1_1_1.setVisible(false);
		lblNewLabel_1_1_1_1_1.setBounds(321, 71, 86, 14);
		turlarPanel.add(lblNewLabel_1_1_1_1_1);

		JButton btnNewButton_3_1_1 = new JButton("Ekle");
		btnNewButton_3_1_1.setBackground(new Color(245, 245, 245));
		btnNewButton_3_1_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		
		JButton btnNewButton_3_1_1_1 = new JButton("Güncelle");
		btnNewButton_3_1_1_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton_3_1_1_1.setBackground(new Color(245, 245, 245));
		btnNewButton_3_1_1_1.setBounds(428, 93, 100, 23);
		turlarPanel.add(btnNewButton_3_1_1_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Verdana", Font.PLAIN, 11));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Id", "İsim", "Hareket Zamanı"}));
		comboBox.setBounds(72, 427, 67, 22);
		turlarPanel.add(comboBox);
		
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Bus", "Binek"}));
		comboBox_1.setBounds(106, 40, 75, 20);
		turlarPanel.add(comboBox_1);
		
		////////////////////////////////////////////////////////////// MENU  BUTONLARI \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		
		JButton btnTurlar = new JButton("Turlar");
		btnTurlar.setBackground(new Color(135, 206, 250));
		btnTurlar.addActionListener(new ActionListener() { 								//// MENU TURLAR
			public void actionPerformed(ActionEvent arg0) {
				
		        lblNewLabel_1.setText("Tur Id");
		        lblNewLabel_1_1.setText("Taşıt Id");
		        lblNewLabel_1_1_1.setText("Hareket Zamanı");
		        lblNewLabel_1_1_1_1.setText("Varış Noktası");;lblNewLabel_1_1_1_1.setVisible(true);
		        lblNewLabel_1_2.setVisible(false);
		        lblNewLabel_1_1_2.setVisible(false);
		        lblNewLabel_1_1_1_1_1.setText(""); lblNewLabel_1_1_1_1_1.setVisible(false);
		        lblNewLabel_1_1_1_2.setText(""); lblNewLabel_1_1_1_2.setVisible(false);
		        textField_1.setVisible(true);
		        textField_2.setVisible(true);
		        textField_3.setVisible(true);
		        textField_4.setVisible(true);
		        textField_5.setVisible(false);
		        textField_6.setVisible(false);
		        textField_7.setVisible(false);
		        comboBox_1.setVisible(false);
		        buttonTurn=1;
		        buttonSil="Turlar";
		        textSıfır();
		        
		        
		        /////////// ARA
		        comboBox.removeAllItems();
		        comboBox.insertItemAt("Id", 0);	
		        comboBox.insertItemAt("Hareket Zamanı", 1);
		        comboBox.setSelectedIndex(0);
		        
		        
				 btnNewButton_3_1_1.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	if (buttonTurn==1) {
								System.out.println("ButtonTurn = "+buttonTurn);
								ekleTur();
			            
			            	}
			            }
			        });
				 btnNewButton_3_1_1_1.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	if (buttonTurn==1) {
								System.out.println("ButtonTurn = "+buttonTurn);
								guncelleTur();
			            
			            	}
			            }
			        });
				
				
				try {
					CnDb connection = new CnDb();
					connection.connection();
					String[] allCols = { "turid", "tasitid", "harakettime", "destination" };
					populateTable(connection, TurQuery, turlarPanel, table_1, allCols); //////////////// PERSONELQUERY HATALI
				} catch (Exception e1) {
					System.out.println("HATA VERILER CEKILEMEDI.");
					// TODO: handle exception
				}
			}
		});
		panel.add(btnTurlar);

		JButton btnPersoneller = new JButton("Personeller");
		btnPersoneller.setBackground(new Color(135, 206, 250));
		btnPersoneller.addActionListener(new ActionListener() {							// MENU PERSONELLER
			public void actionPerformed(ActionEvent e) {
				
		        lblNewLabel_1.setText("Personel Id");
		        lblNewLabel_1_1.setText("Adı");
		        lblNewLabel_1_1_1.setText("Soyadı");
		        lblNewLabel_1_1_1_1.setText("Maaşı");lblNewLabel_1_1_1_1.setVisible(true);
		        lblNewLabel_1_2.setText("Doğum Tarihi"); lblNewLabel_1_2.setVisible(true);
		        lblNewLabel_1_1_2.setText("Başlama Tarihi"); lblNewLabel_1_1_2.setVisible(true);
		        lblNewLabel_1_1_1_2.setText(""); lblNewLabel_1_1_1_2.setVisible(false);
		        lblNewLabel_1_1_1_1_1.setText(""); lblNewLabel_1_1_1_1_1.setVisible(false);
		        textField_1.setVisible(true);
		        textField_2.setVisible(true);
		        textField_3.setVisible(true);
		        textField_4.setVisible(true);
		        textField_5.setVisible(true);
		        textField_6.setVisible(true);
		        textField_7.setVisible(false);
		        comboBox_1.setVisible(false);
		        buttonTurn=2;
		        buttonSil="Personeller";
		        textSıfır();
		        
		        
		        /////////// ARA
		        comboBox.removeAllItems();
		        comboBox.insertItemAt("Id", 0);	
		        comboBox.insertItemAt("İsim", 1);
		        comboBox.setSelectedIndex(0);
		        
		       /* btnNewButton_3_1_1.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	if (buttonTurn==2) {
		            		eklePersonel();
		            }
		            }
		        });
		        
		        btnNewButton_3_1_1_1.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	if (buttonTurn==2) {
		            		guncellePersonel();
		            }
		            }
		        });
		        */
				try {
					CnDb connection = new CnDb();
					connection.connection();
					String[] allCols = { "personelid", "personelname", "personelsurname", "personelsalary",
							"personelbirthdate", "personeljoindate" };
					populateTable(connection, PersonelQuery, turlarPanel, table_1, allCols);
				} catch (Exception e1) {
					System.out.println("HATA VERILER CEKILEMEDI.");
				}
			}
		});
		panel.add(btnPersoneller);

		JButton btnNewButton = new JButton("Müşteriler");
		btnNewButton.addActionListener(new ActionListener() {							//// MENU MUSTERILER
			public void actionPerformed(ActionEvent e) {
					
				 	lblNewLabel_1.setText("Müşteri Id");lblNewLabel_1.setVisible(true);
			        lblNewLabel_1_1.setText("Müşteri Adı");lblNewLabel_1_1.setVisible(true);
			        lblNewLabel_1_1_1.setText("Müşteri Soyadı");lblNewLabel_1_1_1.setVisible(true);
			        lblNewLabel_1_1_1_1.setText("Müşteri Milliyeti"); lblNewLabel_1_1_1_1.setVisible(true);
			        lblNewLabel_1_2.setText("Müşteri E-Mail"); lblNewLabel_1_2.setVisible(true);
			        lblNewLabel_1_1_2.setText("Müşteri Parola"); lblNewLabel_1_1_2.setVisible(true);
			        lblNewLabel_1_1_1_2.setText(""); lblNewLabel_1_1_1_2.setVisible(false);
			        lblNewLabel_1_1_1_1_1.setText(""); lblNewLabel_1_1_1_1_1.setVisible(false);
			        textField_2.setVisible(true);
			        textField_4.setVisible(true);
			        textField_5.setVisible(true);
			        textField_6.setVisible(true);
			        textField_7.setVisible(false);
			        textField_8.setVisible(false);
			        comboBox_1.setVisible(false);
			        buttonTurn=3;
			        buttonSil="Musteriler";
			        textSıfır();
			        
			        /////////// ARA
			        comboBox.removeAllItems();
			        comboBox.insertItemAt("Id", 0);	
			        comboBox.insertItemAt("İsim", 1);
			        comboBox.setSelectedIndex(0);
			        
			        btnNewButton_3_1_1.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			            	if (buttonTurn==3) {
			            		// FONKSİYON ÇAĞIRILACAK
			            		ekleMusteri();
			            }
			            }
			        });
					try {
						CnDb connection = new CnDb();
						connection.connection();
						populateTable(connection, MusteriQuery, turlarPanel, table_1, allColsMu);
					} catch (Exception e1) {
						System.out.println("HATA VERILER CEKILEMEDI.");
						// TODO: handle exception
					}
			}
		});
		btnNewButton.setBackground(new Color(135, 206, 250));
		panel.add(btnNewButton);

		
		
		
		
		
		
		
		/*
		 *  		MENU BILETLER	|
		 *							  
		 */
		
		
		
		JButton btnBiletler_1 = new JButton("Biletler");
		btnBiletler_1.addActionListener(new ActionListener() {							/// MENU BILETLER 
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_1.setText("Bilet Id");lblNewLabel_1.setVisible(true);
		        lblNewLabel_1_1.setText("Bilet Türü");lblNewLabel_1_1.setVisible(true);
		        lblNewLabel_1_1_1.setText("Bilet Fiyatı");lblNewLabel_1_1_1.setVisible(true);
		        lblNewLabel_1_1_1_1.setText("Tur Id"); lblNewLabel_1_1_1_1.setVisible(true);
		        lblNewLabel_1_2.setText("Müşteri Id"); lblNewLabel_1_2.setVisible(true);
		        lblNewLabel_1_1_2.setText("Taşıt Id"); lblNewLabel_1_1_2.setVisible(true);
		        lblNewLabel_1_1_1_2.setText("Personel Id"); lblNewLabel_1_1_1_2.setVisible(true);
		        lblNewLabel_1_1_1_1_1.setText(""); lblNewLabel_1_1_1_1_1.setVisible(false);
		        textField_2.setVisible(false);
		        textField_4.setVisible(true);
		        textField_5.setVisible(true);
		        textField_6.setVisible(true);
		        textField_7.setVisible(true);
		        textField_8.setVisible(false);
		        comboBox_1.setVisible(true);
		        comboBox_1.removeAllItems();
		        comboBox_1.insertItemAt("Eco", 0);
		        comboBox_1.insertItemAt("VIP", 1);
		        comboBox_1.setSelectedIndex(0);
		        
		        /////////// ARA
		        comboBox.removeAllItems();
		        comboBox.insertItemAt("Id", 0);
		        comboBox.insertItemAt("B. Türü", 0);	
		        comboBox.setSelectedIndex(0);
		        buttonTurn=4;
		        buttonSil="Biletler";
		        textSıfır();
		        btnNewButton_3_1_1.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	String type = comboBox_1.getSelectedItem().toString();
		            	if (buttonTurn==4) {
		            		ekleBilet(type);
		            }
		            }
		        });
				try {
					CnDb connection = new CnDb();
					connection.connection();
					populateTable(connection, BiletQuery, turlarPanel, table_1, allColsBi);
				} catch (Exception e1) {
					System.out.println("HATA VERILER CEKILEMEDI.");
				}
		
			}
		});
		
		
		
		
		/*
		 *  		MENU BILETLER	X
		 *							  
		 */
		
		
		
		
		/*
		 *  		MENU TASITLAR	|
		 *							  
		 */
		
		
		
		
		btnBiletler_1.setBackground(new Color(135, 206, 250));
		panel.add(btnBiletler_1);

		JButton btnBiletler_2 = new JButton("Taşıtlar");
		btnBiletler_2.addActionListener(new ActionListener() {							//// MENU TASITLAR
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_1.setText("Taşıt Id");lblNewLabel_1.setVisible(true);
		        lblNewLabel_1_1.setText("Taşıt Türü");lblNewLabel_1_1.setVisible(true);
		        lblNewLabel_1_1_1.setText("Taşıt Plaka");lblNewLabel_1_1_1.setVisible(true);
		        lblNewLabel_1_1_1_1.setText(""); lblNewLabel_1_1_1_1.setVisible(false);
		        lblNewLabel_1_2.setText(""); lblNewLabel_1_2.setVisible(false);
		        lblNewLabel_1_1_2.setText(""); lblNewLabel_1_1_2.setVisible(false);
		        lblNewLabel_1_1_1_2.setText(""); lblNewLabel_1_1_1_2.setVisible(false);
		        lblNewLabel_1_1_1_1_1.setText(""); lblNewLabel_1_1_1_1_1.setVisible(false);
		        textField_2.setVisible(false);
		        textField_4.setVisible(false);
		        textField_5.setVisible(false);
		        textField_6.setVisible(false);
		        textField_7.setVisible(false);
		        textField_8.setVisible(false);
		        comboBox_1.setVisible(true);
		        comboBox_1.removeAllItems();
		        comboBox_1.insertItemAt("Bus", 0);
		        comboBox_1.insertItemAt("Car", 1);
		        comboBox_1.setSelectedIndex(0);
		        
		        /////////// ARA
		        comboBox.removeAllItems();
		        comboBox.insertItemAt("Id", 0);	
		        comboBox.insertItemAt("Plaka", 1);
		        comboBox.insertItemAt("Tür", 2);
		        comboBox.setSelectedIndex(0);
		        
		        System.out.println("Tasıt Turu "+comboBox_1.getSelectedItem());
		        buttonTurn=5;
		        buttonSil="Tasitlar";
		        textSıfır();
		        btnNewButton_3_1_1.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	if (buttonTurn==5) {
		            		String turu = comboBox_1.getSelectedItem().toString();
		            		ekleTasit(turu);
		            }
		            }
		        });
		        
				try {
					CnDb connection = new CnDb();
					connection.connection();
					populateTable(connection, TasitQuery, turlarPanel, table_1, allColsTa);
				} catch (Exception e1) {
					System.out.println("HATA VERILER CEKILEMEDI.");
				}
			}	
		});
		
		
		
		
		
		
		
		/*
		 *  		MENU TASITLAR	X
		 *							  
		 */
		
		
		
		
		
		
		/*
		 *  		MENU CIKIS	|
		 *							  
		 */
		
		
		
		
		btnBiletler_2.setBackground(new Color(135, 206, 250));
		panel.add(btnBiletler_2);

		JButton btnBiletler = new JButton("Çıkış");
		btnBiletler.addActionListener(new ActionListener() {							/// MENU CIKIS
			public void actionPerformed(ActionEvent e) {
				
				int yanit = JOptionPane.showConfirmDialog(contentPane, "Çıkıs yapmak istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
				if (yanit == JOptionPane.YES_OPTION) {
					dispose();
	            	LoginFrame lf = new LoginFrame();
	            	lf.show();
		        } else {		            
		       }				 	
			}
		});
		btnBiletler.setBackground(new Color(240, 128, 128));
		panel.add(btnBiletler);
		
		
		
		
		
		
		
		/*
		 *  		MENU CIKIS	X
		 *							  
		 */
		
		

		JButton btnNewButton_3_1 = new JButton("Sil");
		btnNewButton_3_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton_3_1.setBackground(new Color(245, 245, 245));
		
		

		btnNewButton_3_1.setBounds(443, 424, 85, 23);
		turlarPanel.add(btnNewButton_3_1);

		
		btnNewButton_3_1_1.setBounds(428, 38, 100, 23);
		turlarPanel.add(btnNewButton_3_1_1);
		


		btnNewButton_3_1_1_1.addActionListener(new ActionListener() {	// GUNCELLE BUTONU STANDART TUR FONK ÇAĞIRMA 
            public void actionPerformed(ActionEvent e) {
            	if (buttonTurn==1) {
            		guncelleTur();
            	}
            }
        });
		btnNewButton_3_1_1.addActionListener(new ActionListener() {		// EKLE BUTONU STANDART TUR FONK ÇAĞIRMA 
            public void actionPerformed(ActionEvent e) {
            	if (buttonTurn==1) {
            		ekleTur();
            	}
            }
        });
		btnNewButton_3_1.addActionListener(new ActionListener() {		// SİL BUTONU STANDART TUR FONK ÇAĞIRMA 						
			public void actionPerformed(ActionEvent arg0) {
				if (buttonTurn==1) {
            		silTur();
            	}
			}
		});
		
		
		textField_9 = new JTextField();
		textField_9.setFont(new Font("Verdana", Font.PLAIN, 11));
		textField_9.setColumns(10);
		textField_9.setBounds(149, 427, 49, 20);
		turlarPanel.add(textField_9);
		
		
		try {
			System.out.println(buttonTurn);
			CnDb connection = new CnDb();
			connection.connection();
			String[] allCols = { "turid", "tasitid", "harakettime", "destination" };
			populateTable(connection, TurQuery, turlarPanel, table_1, allCols);
			
			JLabel lblArancakId = new JLabel("Aranıcak");
			lblArancakId.setHorizontalAlignment(SwingConstants.LEFT);
			lblArancakId.setFont(new Font("Verdana", Font.PLAIN, 11));
			lblArancakId.setBounds(10, 430, 63, 14);
			turlarPanel.add(lblArancakId);
			
			JButton btnNewButton_3_1_2 = new JButton("Ara");					/// ARA
			btnNewButton_3_1_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String ara = comboBox.getSelectedItem().toString();
					switch (buttonTurn) {
					case 1: {
						//silTur();
						araTur(ara);
						break;
					}
					
					case 2: {
						araPersonel(ara);
						break;
					}
					
					case 3: {
						araMusteri(ara);
						break;
					}
					
					case 4: {
						araBilet(ara);
						break;
					}
					
					case 5: {
						araTasit(ara);
						break;
					}
					
					default:
						textSıfır();
						break;
					}
				}
			});
			
			btnNewButton_3_1_1.addActionListener(new ActionListener() {		// EKLE
				public void actionPerformed(ActionEvent e) {
					String type = comboBox_1.getSelectedItem().toString();
					String turu = comboBox_1.getSelectedItem().toString();
					switch (buttonTurn) {
					case 1: {
						//silTur();
						ekleTur();
						break;
					}
					
					case 2: {
						eklePersonel();
						break;
					}
					
					case 3: {
						ekleMusteri();
						break;
					}
					
					case 4: {
						
						ekleBilet(type);
						break;
					}
					
					case 5: {
						
						ekleTasit(turu);
						break;
					}
					
					default:
						textSıfır();
						break;
					}
				}
			});
			
			
			
	        btnNewButton_3_1_1_1.addActionListener(new ActionListener() {			// GUNCELLE
	            public void actionPerformed(ActionEvent e) {
	            	String type = comboBox_1.getSelectedItem().toString();
	            	String turu = comboBox_1.getSelectedItem().toString();
	            	switch (buttonTurn) {
					case 1: {
						guncelleTur();
						break;
					}
					
					case 2: {
						guncellePersonel();
						break;
					}
					
					case 3: {
						guncelleMusteri();
						break;
					}
					
					case 4: {
						
						guncelleBilet(type);
						break;
					}
					
					case 5: {
						
						guncelleTasit(turu);
						break;
					}
					
					default:
						textSıfır();
						break;
					}
	            }
	        });
	        
	        btnNewButton_3_1.addActionListener(new ActionListener() {						// IN SIL
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("buttonTurn = "+buttonTurn);
					
					switch (buttonTurn) {
					case 1: {
						silTur();
						break;
					}
					case 2: {
						silPersonel();
						break;
					}
					case 3: {
						silMusteri();
						break;
					}
					case 4: {
						silBilet();
						break;
					}
					case 5: {
						silTasit();
						break;
					}				
					default:
						textSıfır();
						break;
					}
					}			
			});
			
			
			btnNewButton_3_1_2.setFont(new Font("Verdana", Font.PLAIN, 11));
			btnNewButton_3_1_2.setBackground(new Color(245, 245, 245));
			btnNewButton_3_1_2.setBounds(208, 426, 85, 23);
			turlarPanel.add(btnNewButton_3_1_2);
			

			

			

		} catch (Exception e) {
			System.out.println("HATA VERILER CEKILEMEDI.");
		}
		
		table_1.addMouseListener(new MouseAdapter() {	
			public void mouseClicked(MouseEvent e) {									/// TABLEDAN VERI CEKME
				int selectedRowIndex = table_1.getSelectedRow();
				System.out.println(comboBox.getSelectedItem()); 
				///////////////////////////////////////////////////////////////////////////	 
				textField.setText(table_1.getValueAt(selectedRowIndex, 0).toString());		
				textField_9.setText(table_1.getValueAt(selectedRowIndex, 0).toString());	
				textField_1.setText(table_1.getValueAt(selectedRowIndex, 0).toString());
				textField_2.setText(table_1.getValueAt(selectedRowIndex, 1).toString());
				textField_3.setText(table_1.getValueAt(selectedRowIndex, 2).toString());
				textField_4.setText(table_1.getValueAt(selectedRowIndex, 3).toString());
				textField_5.setText(table_1.getValueAt(selectedRowIndex, 4).toString());
				textField_6.setText(table_1.getValueAt(selectedRowIndex, 5).toString());
				textField_7.setText(table_1.getValueAt(selectedRowIndex, 6).toString());
				textField_8.setText(table_1.getValueAt(selectedRowIndex, 7).toString());
				///////////////////////////////////////////////////////////////////////////  VERI EKLEME
				
				
			}
		});
	}
}

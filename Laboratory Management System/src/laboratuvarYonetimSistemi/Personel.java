package laboratuvarYonetimSistemi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Personel extends Kullanici {
	static String host = "jdbc:mysql://localhost:3306/sqlim";
	static String root = "root";
	static String hostpsw = "65655396129Ek.";

	private String kullaniciAdi;
	private String sifre;

	public static void laboratuvarEkle(String name, String faculty, String department, float price) {
		try {	// Veritabanina laboratuvar ekleme fonksiyonu
			String query = "insert into labs(name,faculty,department,price) values (?,?,?,?)";
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, faculty);
			ps.setString(3, department);
			ps.setFloat(4, price);
			ps.execute();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--------Laboratuvar Basariyla Eklendi--------");
	}

	public static void laboratuvarSil(int size) {
		Scanner input = new Scanner(System.in);
		String query = "SELECT * FROM sqlim.labs WHERE owner = ?";
		try { // Veritabanindan laboratuvar silme fonksiyonu
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "NULL");
			ResultSet rs = ps.executeQuery();
			DBTablePrinterr.printResultSet(rs); // Konsola veritabanindan veri cekip yazdirma
			System.out.println("Sileceginiz laboratuvarin idlabs numarasini giriniz:");
			String silinen = input.next();
			if(Integer.valueOf(silinen)<=0) {
				System.err.println("--------Pozitif deger giriniz--------");
			}
			else if (Integer.valueOf(silinen)>size) {
				System.err.println("--------Buyuk deger girildi--------");
			}
			String query1 = "DELETE FROM labs WHERE idlabs= ?";
			try {
				ps = con.prepareStatement(query1);
				ps.setString(1, silinen);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void laboruvarlariListele(int size) {
		try {
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			DBTablePrinterr.printTable(con, "labs", size); // Konsola veritabanindan veri cekip yazdirma
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void laboratuvarlariGuncelle(ArrayList<Laboratuvar> laboratuvarList) {
		String query = "select * from sqlim.labs";
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlim", "root", "65655396129Ek.");
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery(query);
			laboratuvarList.clear();
			while (rs.next()) {
				Laboratuvar laboratuvar = new Laboratuvar(); // Constructorla yeni nesne olusturma
				laboratuvar.setName(rs.getString("name"));
				laboratuvar.setFaculty(rs.getString("faculty"));
				laboratuvar.setDepartment(rs.getString("department"));
				laboratuvar.setOwner(rs.getString("owner"));
				laboratuvar.setPrice(rs.getFloat("price"));
				laboratuvarList.add(laboratuvar);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Personel(String kullaniciAdi, String sifre, String isim, String soyisim) {
		super(isim, soyisim);
		this.kullaniciAdi = kullaniciAdi;
		this.sifre = sifre;
	}

	public String getKullaniciAdi() {
		return kullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

}

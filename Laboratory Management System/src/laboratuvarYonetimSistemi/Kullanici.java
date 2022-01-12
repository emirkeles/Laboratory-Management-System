package laboratuvarYonetimSistemi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class Kullanici {

	static String host = "jdbc:mysql://localhost:3306/sqlim";
	static String root = "root";
	static String hostpsw = "65655396129Ek.";

	private String isim;
	private String soyisim;
	private String kullaniciAdi;
	private String sifre;
	private float bakiye = 0;

	Kullanici() {

	}

	Kullanici(String isim, String soyisim) {
		this.isim = isim;
		this.soyisim = soyisim;
	}

	Kullanici(String isim, String soyisim, String kullaniciAdi, String sifre) {
		this(isim, soyisim);
		this.kullaniciAdi = kullaniciAdi;
		this.sifre = sifre;
	}

	Kullanici(String isim, String soyisim, String kullaniciAdi, String sifre, float bakiye) {
		this(isim, soyisim, kullaniciAdi, sifre);
		this.bakiye = bakiye;
	}

	public static void idyeGoreAra() {
		Scanner input = new Scanner(System.in);
		try { // ID'den kullanici arayip konsola yazdirma
			System.out.println("Aramak istediginz kullanicinin ID'sini girin:");
			String userid = input.next();
			String query = "Select * from user where iduser = ?";
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, userid);
			ResultSet rs = ps.executeQuery();
			DBTablePrinterr.printResultSet(rs); // Konsola veritabanindan veri cekip yazdirma
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void bakiyeGuncelle(ArrayList<Kullanici> k) {
		Scanner input = new Scanner(System.in);
		System.out.println("Yuklenecek miktari giriniz(TL):");
		float yuklenecek = input.nextFloat();
		if(yuklenecek > 0) {
			System.out.println("Islemi onayliyor musunuz?(E-H)");
			char sorgu = input.next().charAt(0);
			if (sorgu == 'E') {
				try {	// Veritabaninda kullanicinin bakiyesini arttirip guncelleme
					float a = k.get(SQLislemleri.index).getBakiye() + yuklenecek;
					String userid = Integer.toString(SQLislemleri.index + 1);
					Connection con = DriverManager.getConnection(host, root, hostpsw);
					Statement statement = con.createStatement();
					statement.executeUpdate("UPDATE sqlim.user SET balance='" + a + "' WHERE  iduser=" + userid);
					statement.close();
					System.out.println("--------Bakiye yukleme basarili--------");
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (sorgu == 'H') {
				System.err.println("--------Bakiye yukleme iptal edildi!--------");
			} else {
				System.err.println("--------Tanimlanamayan giris!--------");
			}
		}
		else {
			System.err.println("--------Yuklenecek bakiye pozitif olmali--------");
		}
		
	}

	public static void adaGoreAra() {
		Scanner input = new Scanner(System.in);
		try { // Kullanicinin isminden kullaniciyi bulup konsola yazdirma
			System.out.println("Aramak istediginz kullanicinin kullanici adini girin:");
			String kullaniciAdi = input.next();
			String query = "Select * from user where username = ?";
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, kullaniciAdi);
			ResultSet rs = ps.executeQuery();
			DBTablePrinterr.printResultSet(rs); // Konsola veritabanindan veri cekip yazdirma
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void kullaniciListele(int size) {
		try {	// Veritabanindaki kullanicilari konsola yazdirma
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			DBTablePrinterr.printTable(con, "user", size);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void laboratuvarKirala(ArrayList<Kullanici> kullanicilar) {
		float fiyat = 0;
		String sahip = "";
		a: try { // Laboratuvari kiralayip, owner'a kiralayan kullaniciyi yazdirma ve 
				 // laboratuvar fiyatini kullanici bakiyesinden dusen fonksiyon
			String query = "select owner,price from labs where idlabs = ?";
			Scanner input = new Scanner(System.in);
			System.out.println("Kiralamak istediginiz laboratuvarin ID'sini giriniz");
			String labId = input.next();
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, labId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				sahip = rs.getString(1);
				fiyat = rs.getFloat(2);
			}
			else if (!rs.next()) {
				System.err.println("--------Girilen ID'ye sahip laboratuvar bulunamadi!--------");
				break a;
			}
			
			if (!sahip.equals("NULL")) {
				System.err.println("--------Laboratuvar Zaten Kiralanmis Durumda!--------");
			} else {
				if (kullanicilar.get(SQLislemleri.index).getBakiye() < fiyat) {
					System.err.println("--------Yetersiz bakiye!--------");
				} else {
					String isim = kullanicilar.get(SQLislemleri.index).getIsim();
					kullanicilar.get(SQLislemleri.index).setBakiye(kullanicilar.get(SQLislemleri.index).getBakiye() - fiyat);
					float yeniBakiye = kullanicilar.get(SQLislemleri.index).getBakiye();
					query = "UPDATE labs SET owner = ? WHERE idlabs = ?";
					ps = con.prepareStatement(query);
					ps.setString(1, isim);
					ps.setString(2, labId);
					ps.executeUpdate();
					String query2 = "UPDATE user SET balance = ? where iduser = ?";
					ps = con.prepareStatement(query2);
					ps.setFloat(1, yeniBakiye);
					ps.setString(2, String.valueOf(SQLislemleri.index + 1));
					ps.executeUpdate();
					System.out.println("--------Laboratuvar Basariyla Kiralandi--------");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void laboratuvarListele(int size) {
		Scanner input = new Scanner(System.in);
		System.out.println("Butun Laboratuvarlari Listele\t\t[1]");
		System.out.println("Kiralamaya Uygun Laboratuvarlari Listele [2]");
		int secim = input.nextInt();
		if (secim == 1) {
			try { // Veritabanindan laboratuvar bilgileri cekilip konsola yazdirma
				Connection con = DriverManager.getConnection(host, root, hostpsw);
				DBTablePrinterr.printTable(con, "labs", size); // Konsola veritabanindan veri cekip yazdirma
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (secim == 2) {
			String query = "SELECT idlabs,name,faculty,department,price FROM labs WHERE owner = ?";
			try {
				Connection con = DriverManager.getConnection(host, root, hostpsw);
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, "NULL");
				ResultSet rs = ps.executeQuery();
				DBTablePrinterr.printResultSet(rs); // Konsola veritabanindan veri cekip yazdirma
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("--------Hatali Giris!--------");
		}

	}

	public static void bakiyeGoruntule(ArrayList<Kullanici> k) {
		System.out.println("--------Bakiyeniz:" + k.get(SQLislemleri.index).getBakiye() + "TL--------");
	}

	public String getIsim() {
		return isim;
	}

	public void setIsim(String isim) {
		this.isim = isim;
	}

	public String getSoyisim() {
		return soyisim;
	}

	public void setSoyisim(String soyisim) {
		this.soyisim = soyisim;
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

	public float getBakiye() {
		return bakiye;
	}

	public void setBakiye(float bakiye) {
		this.bakiye = bakiye;
	}
}

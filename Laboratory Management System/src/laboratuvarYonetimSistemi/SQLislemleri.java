
package laboratuvarYonetimSistemi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class SQLislemleri {
	static String host = "jdbc:mysql://localhost:3306/sqlim";
	static String root = "root";
	static String hostpsw = "65655396129Ek.";
	static Scanner input = new Scanner(System.in);
	public static int index; // Giris yapan kullanicinin listedeki indexi
	String username;
	String password;

	public static void listeGuncelle(ArrayList<Kullanici> kullaniciList) {
		String query = "select * from user";
		try {	// Veritabanindan bilgi cekme ve arrayliste atama islemi
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery("Select * from sqlim.user");
			kullaniciList.clear();
			while (rs.next()) {
				Kullanici kullanici = new Kullanici(); // Constructorla yeni nesne olusturma
				kullanici.setKullaniciAdi(rs.getString("username"));
				kullanici.setSifre(rs.getString("password"));
				kullanici.setIsim(rs.getString("name"));
				kullanici.setSoyisim(rs.getString("surname"));
				kullanici.setBakiye(rs.getFloat("balance"));
				kullaniciList.add(kullanici);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void register(String user, String pass, String isim, String soyisim) {
		try {	// Kayit olup veritabanina ekleme islemi
			String query = "select username from user where username = ?";
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.err.println("--------Kullanici Adi Kullanimda! Baska bir kullanici adi deneyin.--------");
			} else {
				try {
					query = "insert into user (username,password,name,surname) values (?,?,?,?)";
					ps = con.prepareStatement(query);
					ps.setString(1, user);
					ps.setString(2, pass);
					ps.setString(3, isim);
					ps.setString(4, soyisim);
					ps.execute();
					System.out.println("--------Kayit Olma Basarili--------");
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean verifyLogin(String user, String pass) throws InterruptedException {
		String query = "select iduser from sqlim.user where username = ? and password = ?";
		try {	// giris yaparken kullanici adi - sifre sorgusuna gore boolean deger dondurme
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				index = Integer.parseInt(rs.getString("iduser")) - 1;
				con.close();
				return true;
			} else {
				System.err.println("--------Hatali kullanici adi/sifre--------");
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verifyLoginEmployee(String user, String pass) throws InterruptedException {
		String query = "select * from sqlim.employee where username = ? and password = ?";
		try {	// giris yaparken kullanici adi - sifre sorgusuna gore boolean deger dondurme
			Connection con = DriverManager.getConnection(host, root, hostpsw);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				con.close();
				return true;
			} else {
				System.err.println("--------Hatali kullanici adi/sifre--------");
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

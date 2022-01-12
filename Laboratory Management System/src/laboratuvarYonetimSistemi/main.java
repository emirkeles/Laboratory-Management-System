package laboratuvarYonetimSistemi;

import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;

public class main extends SQLislemleri {
	public static ArrayList<Kullanici> kullaniciList = new ArrayList<>(); // Kullanicilarin tutuldugu arraylist
	public static ArrayList<Laboratuvar> laboratuvarList = new ArrayList<>();	// Personellerin tutuldugu arraylist

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		listeGuncelle(kullaniciList);	// Uygulama baslarken veritabanindaki bilgiyle guncelleme
		Personel.laboratuvarlariGuncelle(laboratuvarList);
		String user, pass;
		a: while (true) {
			System.out.println("Personel girisi yap\t[1]\nKullanici girisi yap\t[2]");
			System.out.println("Kayit ol\t\t[3]\nCikis yap\t\t[0]");
			int secim = input.nextInt();
			switch (secim) {
			case 1 -> {
				System.out.println("Kullanici adinizi giriniz:");
				user = input.next();
				System.out.println("Sifrenizi giriniz:");
				pass = input.next();
				try {
					if (verifyLoginEmployee(user, pass)) { // kullanici adi sifre veritabaninda dogrulanirsa
						b: while (true) {				   // true dogrulanmazsa false degeri donduren fonksiyon
							System.out.println(
									"Laboratuvar Ekle\t[1]\nLaboratuvar sil\t\t[2]\nLaboratuvarlari Listele\t[3]");
							System.out.println("Kullanici Ara\t\t[4]\nKullanicilari Listele\t[5]\nCikis Yap\t\t[0]");
							int secim2 = input.nextInt();
							switch (secim2) {
							case 1 -> {
								System.out.println("Laboratuvar ismini giriniz:");
								String labName = input.next();
								System.out.println("Fakulte ismini giriniz:");
								String labFaculty = input.next();
								System.out.println("Departman ismini giriniz:");
								String labDepartment = input.next();
								System.out.println("Laboratuvar kiralama ucreti giriniz:");
								float labPrice = input.nextFloat();
								Personel.laboratuvarEkle(labName, labFaculty, labDepartment, labPrice);
								Personel.laboratuvarlariGuncelle(laboratuvarList);// Islemlerden sonra guncelleme
							}
							case 2 -> {
								Personel.laboratuvarSil(laboratuvarList.size());
								Personel.laboratuvarlariGuncelle(laboratuvarList);
							}
							case 3 -> {
								Personel.laboruvarlariListele(laboratuvarList.size());
								Personel.laboratuvarlariGuncelle(laboratuvarList);
							}
							case 4 -> {
								System.out.println(
										"Kullanici adina gore ara [1]\nUser ID'ye gore ara\t [2]\nOnceki Sayfa\t\t [0]");
								int secenek = input.nextInt();
								if (secenek == 1) {
									Kullanici.adaGoreAra();
								} else if (secenek == 2) {
									Kullanici.idyeGoreAra();
								} else if (secenek == 0) {
								} else {
									System.err.println("Hatali Giris!");
								}

							}
							case 5 -> {
								Kullanici.kullaniciListele(kullaniciList.size());
							}
							case 0 -> {
								break b;
							}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			case 2 -> {
				System.out.println("Kullanici adinizi giriniz:");
				user = input.next();
				System.out.println("Sifrenizi giriniz:");
				pass = input.next();
				try {
					if (verifyLogin(user, pass)) { //kullanici adi sifre veritabaninda dogrulanirsa
						c: while (true) {		   //true dogrulanmazsa false degeri donduren fonksiyon
							System.out.println(
									"Laboratuvar Kirala\t[1]\nLaboratuvarlari Listele\t[2]\nBakiye Yukle\t\t[3]");
							System.out.println("Bakiye Goruntule\t[4]\nCikis yap\t\t[0]");
							int secim2 = input.nextInt();
							switch (secim2) {
							case 1 -> {
								Kullanici.laboratuvarKirala(kullaniciList);
							}
							case 2 -> {
								Kullanici.laboratuvarListele(laboratuvarList.size());
							}
							case 3 -> {
								Kullanici.bakiyeGuncelle(kullaniciList);
								listeGuncelle(kullaniciList);
							}
							case 4 -> {
								Kullanici.bakiyeGoruntule(kullaniciList);
							}
							case 0 -> {
								break c;
							}
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			case 3 -> {
				System.out.println("Kullanici adinizi giriniz");
				user = input.next();
				System.out.println("Sifrenizi giriniz");
				pass = input.next();
				System.out.println("Adinizi giriniz:");
				String isim = input.next();
				System.out.println("Soyadinizi giriniz:");
				String soyisim = input.next();
				register(user, pass, isim, soyisim);
				listeGuncelle(kullaniciList); // Kayit olan kullanicidan sonra guncelleme islemi
			}
			case 0 -> {
				break a;
			}
			}
		}
		input.close();
		System.out.println("--------Program basariyla sonlandirildi--------");
	}
}

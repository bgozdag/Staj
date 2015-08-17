/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proje3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author Buğra
 */
public class Main {

    /**
     *
     * @param args
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        int a = 0, b;
        try {

            Scanner reader = new Scanner(System.in);
            while (a != 4) {
                System.out.println("----------------------------------\nMENU:");
                System.out.println("1. Kullanıcı Ekle\n2. Kullanıcı Çıkar\n3. Kullanıcıları Listele\n4. Çıkış");
                try {
                    a = reader.nextInt();
                    switch (a) {
                        case 1:
                            try {
                                System.out.println("----------------------------------\nKullanıcı ekle:");
                                System.out.println("1. Öğrenci\n2. Stajyer\n3. Geliştirici");
                                b = reader.nextInt();

                                switch (b) {
                                    case 1:
                                        Ekle("Öğrenci");
                                        break;
                                    case 2:
                                        Ekle("Stajyer");
                                        break;
                                    case 3:
                                        Ekle("Geliştirici");
                                        break;
                                    default:
                                        System.out.println("----------------------------------\nHatalı Giriş.");
                                        break;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("----------------------------------\nHatalı Giriş.");
                                reader.nextLine();
                            }
                            break;
                        case 2:
                            cikar();
                            break;
                        case 3:
                            liste();
                            break;
                        case 4:
                            break;
                        default:
                            System.out.println("----------------------------------\nHatalı Giriş.");
                            break;
                    }
                } catch (InputMismatchException e) {

                    System.out.println("----------------------------------\nHatalı Giriş.");
                    reader.nextLine();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    static void Ekle(String meslek) throws SQLException {
        Scanner reader = new Scanner(System.in);
        String isim, soyisim, email, okuladi, birim, kurum;
        int no, yas;
        try {
            System.out.println("İsim:");
            isim = reader.nextLine();
            while (!isim.matches("[a-zA-Z]+")) {
                System.out.println("Lütfen geçerli bir isim girin.");
                isim = reader.nextLine();
            }
            System.out.println("Soyisim:");
            soyisim = reader.nextLine();
            while (!soyisim.matches("[a-zA-Z]+")) {
                System.out.println("Lütfen geçerli bir soyisim girin.");
                soyisim = reader.nextLine();
            }
            System.out.println("e-mail:");
            email = reader.nextLine();

            if (meslek != "Geliştirici") {
                System.out.println("Öğrenci no:");
                do {
                    try {

                        no = reader.nextInt();
                        reader.nextLine();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Lütfen geçerli bir sayı girin.");
                        reader.nextLine();
                    }
                } while (true);
                System.out.println("Okul adı:");
                okuladi = reader.nextLine();
                while (!okuladi.matches("[a-zA-Z]+")) {
                    System.out.println("Lütfen geçerli bir okul adı girin.");
                    okuladi = reader.nextLine();
                }

                if (meslek == "Stajyer") {
                    System.out.println("Çalıştığı birim:");
                    birim = reader.nextLine();
                    while (!birim.matches("[a-zA-Z]+")) {
                        System.out.println("Lütfen geçerli bir birim adı girin.");
                        birim = reader.nextLine();
                    }
                    URL surl = new URL("http://localhost:8080/WebApplication1/StajyerEkle?isim=" + isim + "&soyisim=" + soyisim + "&email=" + email + "&numara=" + no + "&okuladi=" + okuladi + "&birim=" + birim);
                    BufferedReader in = new BufferedReader(new InputStreamReader(surl.openStream()));
                    while (in.ready()) {
                        String line = in.readLine();
                        System.out.println(line);
                    }
                    in.close();
                } else {
                    URL surl = new URL("http://localhost:8080/WebApplication1/OgrenciEkle?isim=" + isim + "&soyisim=" + soyisim + "&email=" + email + "&numara=" + no + "&okuladi=" + okuladi);
                    BufferedReader in = new BufferedReader(new InputStreamReader(surl.openStream()));
                    while (in.ready()) {
                        String line = in.readLine();
                        System.out.println(line);
                    }
                    in.close();

                }
            } else {
                System.out.println("Yaş:");
                do {
                    try {

                        yas = reader.nextInt();
                        reader.nextLine();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Lütfen geçerli bir sayı girin.");
                        reader.nextLine();
                    }
                } while (true);
                System.out.println("Çalıştığı kurum:");
                kurum = reader.nextLine();
                while (!kurum.matches("[a-zA-Z]+")) {
                    System.out.println("Lütfen geçerli bir kurum adı girin.");
                    kurum = reader.nextLine();
                }
                URL surl = new URL("http://localhost:8080/WebApplication1/GelistiriciEkle?isim=" + isim + "&soyisim=" + soyisim + "&email=" + email + "&yas=" + yas + "&kurum=" + kurum);
                BufferedReader in = new BufferedReader(new InputStreamReader(surl.openStream()));
                while (in.ready()) {
                    String line = in.readLine();
                    System.out.println(line);
                }
                in.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void liste() throws SQLException {
        Statement stmt = null, stmto = null, stmts = null, stmtg = null;
        ResultSet rs = null, rso = null, rss = null, rsc = null, rsg = null;
        PreparedStatement pstmtc = null;

        try {
            URL surl = new URL("http://localhost:8080/WebApplication1/Liste");
            BufferedReader in = new BufferedReader(new InputStreamReader(surl.openStream()));
            while (in.ready()) {
                String line = in.readLine();
                System.out.println(line);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    static void cikar() throws SQLException {
        int i;
        try {
            System.out.println("Silinecek kayıt:");
            Scanner reader = new Scanner(System.in);
            i = reader.nextInt();
            URL surl = new URL("http://localhost:8080/WebApplication1/Cikar?i=" + i);
            BufferedReader in = new BufferedReader(new InputStreamReader(surl.openStream()));
            while (in.ready()) {
                String line = in.readLine();
                System.out.println(line);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

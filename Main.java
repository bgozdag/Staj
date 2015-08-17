/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Buğra
 */
public class Liste extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "jdbc:mysql://localhost:3306/proje2";
        String user = "root";
        String password = "12011991";
        Statement stmt = null, stmto = null, stmts = null, stmtg = null;
        ResultSet rs = null, rso = null, rss = null, rsc = null, rsg = null;
        PreparedStatement pstmtc = null;
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            stmto = conn.createStatement();
            stmts = conn.createStatement();
            stmtg = conn.createStatement();

            rs = stmt.executeQuery("select * from kullanicilar");
            if (!rs.next()) {
                out.println("----------------------------------\nListe boş.");
            } else {
                out.println("----------------------------------\nKullanıcı bilgileri:");
                rs = stmt.executeQuery("select * from kullanicilar");
                rso = stmto.executeQuery("select * from ogrenci");
                rss = stmts.executeQuery("select * from stajyer");
                rsg = stmtg.executeQuery("select * from gelistirici");
                while (rs.next()) {
                    out.print(rs.getString("id") + ", İsim:" + rs.getString("isim") + ", Soyisim:" + rs.getString("soyisim") + ", Meslek:" + rs.getString("meslek") + ", e-mail:" + rs.getString("email"));
                    pstmtc = conn.prepareStatement("select * from ogrenci where p_id=?");
                    pstmtc.setInt(1, rs.getInt("id"));
                    rsc = pstmtc.executeQuery();
                    if (rsc.next()) {
                        rso.next();
                        out.print(", Öğrenci no:" + rso.getString("numara") + ", Okul adı:" + rso.getString("okuladi"));
                    }
                    pstmtc = conn.prepareStatement("select * from stajyer where p_id=?");
                    pstmtc.setInt(1, rs.getInt("id"));
                    rsc = pstmtc.executeQuery();
                    if (rsc.next()) {
                        rss.next();
                        out.print(", Çalıştığı birim:" + rss.getString("birim"));
                    }
                    pstmtc = conn.prepareStatement("select * from gelistirici where p_id=?");
                    pstmtc.setInt(1, rs.getInt("id"));
                    rsc = pstmtc.executeQuery();
                    if (rsc.next()) {
                        rsg.next();
                        out.print(", Yaş:" + rsg.getString("yas") + ", Çalıştığı kurum:" + rsg.getString("kurum"));
                    }
                    out.println(", Eklenme tarihi:" + rs.getString("tarih"));
                }
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Liste.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Liste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Liste.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Liste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

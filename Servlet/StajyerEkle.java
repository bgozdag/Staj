package servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Buğra
 */
public class StajyerEkle extends HttpServlet {

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
            throws ServletException, IOException {
        String url = "jdbc:mysql://localhost:3306/proje2";
        String user = "root";
        String password = "12011991";
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        String isim = request.getParameter("isim");
        String soyisim = request.getParameter("soyisim");
        String email = request.getParameter("email");
        String numara = request.getParameter("numara");
        String okuladi = request.getParameter("okuladi");
        String birim = request.getParameter("birim");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            PreparedStatement pstmt = conn.prepareStatement("insert into kullanicilar (isim,soyisim,email,meslek,tarih) values (?,?,?,'Stajyer',now())");
            pstmt.setString(1, isim);
            pstmt.setString(2, soyisim);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("insert into ogrenci (numara,okuladi,p_id) values (?,?,(select id from kullanicilar where email=?))");
            pstmt.setString(1, numara);
            pstmt.setString(2, okuladi);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("insert into stajyer (birim,p_id) value (?,(select id from kullanicilar where email=?))");
            pstmt.setString(1, birim);
            pstmt.setString(2, email);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                out.print("----------------------------------\nKullanıcı eklendi.");
            }

        } catch (Exception e2) {
            System.out.println(e2);
        }

        out.close();

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
        processRequest(request, response);
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
        processRequest(request, response);
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

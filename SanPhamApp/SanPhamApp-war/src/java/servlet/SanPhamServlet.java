/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sessionbean.SanPhamSessionLocal;

/**
 *
 * @author Acer
 */
@WebServlet(name = "SanPhamServlet", urlPatterns = {"/SanPhamServlet"})
public class SanPhamServlet extends HttpServlet {
    @EJB
    private SanPhamSessionLocal bean;

    @Resource (mappedName = "jms/myQueueFactory")
    private QueueConnectionFactory connFactory;
    @Resource (mappedName = "jms/myQueue")
    private Queue queue;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String errorMessage = "";
        try {
            if ("add".equals(action)) {
                int maSP = Integer.parseInt(request.getParameter("maSP"));
                String tenSP = request.getParameter("tenSP");
                double gia = Double.parseDouble(request.getParameter("gia"));
                String moTa = request.getParameter("moTa");
                bean.addSanPham(new Product(maSP, tenSP, gia, moTa));
                guiThongDiep("Them", tenSP);
            } else if ("update".equals(action)) {
                int maSP = Integer.parseInt(request.getParameter("maSP"));
                String tenSP = request.getParameter("tenSP");
                double gia = Double.parseDouble(request.getParameter("gia"));
                String moTa = request.getParameter("moTa");
                bean.updateSanPham(new Product(maSP, tenSP, gia, moTa));
                guiThongDiep("Sua", tenSP);
            } else if ("delete".equals(action)) {
                int maSP = Integer.parseInt(request.getParameter("maSP"));
                String tenSP = request.getParameter("tenSP");
                bean.deleteSanPham(maSP);
                guiThongDiep("Xoa", tenSP);
            }
        } catch (NumberFormatException e) {
            errorMessage = "Lỗi nhập liệu: Vui lòng nhập số hợp lệ cho Ma SP hoặc Gia. Chi tiết: " + e.getMessage();
        } catch (Exception e) {
            errorMessage = "Lỗi không mong muốn: " + e.getMessage();
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<title>Quản Lý Sản Phẩm</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f7f9; }");
            out.println("h1 { color: #2c3e50; text-align: center; margin-bottom: 20px; }");
            out.println("table { width: 80%; margin: 0 auto; border-collapse: collapse; background-color: #ffffff; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
            out.println("th, td { padding: 12px; text-align: left; border: 1px solid #ddd; }");
            out.println("th { background-color: #3498db; color: white; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println("tr:hover { background-color: #e0f7fa; }");
            out.println("td form { margin: 0; }");
            out.println("td input[type='submit'] { background-color: #e74c3c; color: white; border: none; padding: 5px 10px; cursor: pointer; border-radius: 5px; }");
            out.println("td input[type='submit']:hover { background-color: #c0392b; }");
            out.println(".form-container { width: 80%; margin: 20px auto; padding: 20px; background-color: #ffffff; box-shadow: 0 2px 5px rgba(0,0,0,0.1); border-radius: 8px; }");
            out.println("input[type='number'], input[type='text'] { width: 100%; padding: 8px; margin: 5px 0; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }");
            out.println("input[type='submit'] { background-color: #2ecc71; color: white; border: none; padding: 10px 20px; margin: 5px; cursor: pointer; border-radius: 5px; }");
            out.println("input[type='submit']:hover { background-color: #27ae60; }");
            out.println(".error { color: red; text-align: center; margin-bottom: 10px; }");
            out.println("</style>");
            out.println("</head><body>");
            
            if (!errorMessage.isEmpty()) {
                out.println("<div class='error'>" + errorMessage + "</div>");
            }
            out.println("<h1>Danh sach San Pham</h1>");
            out.println("<table>");
            out.println("<tr><th>Ma SP</th><th>Ten SP</th><th>Gia</th><th>Mo Ta</th><th>Action</th></tr>");

            try {
                List<Product> list = bean.getAllSanPham();
                for (Product sp : list) {
                    out.println("<tr><td>" + sp.getMaSP() + "</td><td>" + sp.getTenSP() + "</td><td>" + sp.getGia() + "</td><td>" + sp.getMoTa() + "</td>");
                    out.println("<td><form method='post' action='SanPhamServlet'><input type='hidden' name='action' value='delete'><input type='hidden' name='maSP' value='" + sp.getMaSP() + "'><input type='submit' value='Xoa'></form></td></tr>");
                }
            } catch (Exception e) {
                out.println("<tr><td colspan='5' style='color:red;'>Lỗi load dữ liệu: " + e.getMessage() + "</td></tr>");
            }

            out.println("</table>");
            out.println("<div class='form-container'>");
            out.println("<h2>Them/Sua San Pham</h2>");
            out.println("<form method='post' action='SanPhamServlet'>");
            out.println("Ma SP: <input type='number' name='maSP' required><br>");
            out.println("Ten SP: <input type='text' name='tenSP'><br>");
            out.println("Gia: <input type='number' step='0.01' name='gia' required><br>");
            out.println("Mo Ta: <input type='text' name='moTa'><br>");
            out.println("<input type='hidden' name='action' value='add'>");
            out.println("<input type='submit' value='Them'>");
            out.println("<input type='submit' value='Sua' onclick=\"this.form.elements['action'].value='update'\">");
            out.println("</form>");
            out.println("</div>");
            out.println("</body></html>");
        }
    }
    private void guiThongDiep(String tacVu, String tenSP){
        try {
            Connection conn = connFactory.createConnection();
            Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            TextMessage msg = session.createTextMessage(tacVu + "|"+ tenSP);
            producer.send(msg);
        } catch (JMSException ex) {
            Logger.getLogger(SanPhamServlet.class.getName()).log(Level.SEVERE, null, ex);
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

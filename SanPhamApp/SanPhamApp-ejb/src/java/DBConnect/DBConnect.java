/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBConnect;

import Entity.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class DBConnect {
    private static String URL = "jdbc:mysql://localhost:3306/sanpham_db?useSSL=false&requireSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String user = "root";
    private static String pwd = "thiennga54102004";
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, user, pwd);
    }
    public void addProduct(String tenSP,double gia, String moTa) throws SQLException, ClassNotFoundException{
        String sql = "INSERT INTO SanPham (tenSP,gia,moTa) VALUES (?,?,?);";
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, tenSP);
            stmt.setDouble(2, gia);
            stmt.setString(3, moTa);
            stmt.executeUpdate();
            System.out.println("Them thanh cong");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteProduct(int id) throws SQLException, ClassNotFoundException{
        String sql = "DELETE FROM sanpham WHERE maSP = ?";
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Xoa thanh cong");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateProduct(int maSP, String tenSP, double gia, String moTa) throws SQLException, ClassNotFoundException{
        String sql = "UPDATE sanpham SET tenSP = ?, gia = ?, moTa = ? WHERE maSP = ? ";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)   ){
            stmt.setString(1, tenSP);
            stmt.setDouble(2, gia);
            stmt.setString(3, moTa);
            stmt.setInt(4, maSP);
            int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Cập nhật sản phẩm thành công!");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ma SP: " + maSP);
        }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<Product> getAllProduct() throws SQLException, ClassNotFoundException{
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM sanpham";
        try(Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
            int maSP = rs.getInt("maSP");
            String tenSP = rs.getString("tenSP");
            double gia = rs.getDouble("gia");
            String moTa = rs.getString("moTa");
            products.add(new Product(maSP,tenSP,gia,moTa));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return products;
    }
    public void addMessage(String loaiTacVu,String tenSP){
        String sql = "INSERT INTO nhatky (loaiTacVu, tenSP, thoigian) VALUES (?,?,NOW());";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,loaiTacVu);
            stmt.setString(2,tenSP);
            stmt.executeUpdate();
            System.out.println("Them message thanh cong");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

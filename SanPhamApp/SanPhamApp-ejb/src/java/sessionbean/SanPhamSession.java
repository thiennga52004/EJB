/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package sessionbean;

import DBConnect.DBConnect;
import Entity.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Acer
 */
@Stateless
public class SanPhamSession implements SanPhamSessionLocal {

    @Override
    public void addSanPham(Product sp) {
        Product pro = sp;
        String tenSP = pro.getTenSP();
        double gia = pro.getGia();
        String moTa = pro.getMoTa();
        DBConnect conn = new DBConnect();
        try {
            conn.addProduct(tenSP, gia, moTa);
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamSession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateSanPham(Product sp) {
        DBConnect conn = new DBConnect();
        try {
            conn.updateProduct(sp.getMaSP(),sp.getTenSP(),sp.getGia(),sp.getMoTa());
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamSession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteSanPham(int maSP) {
        DBConnect conn = new DBConnect();
        try {
            conn.deleteProduct(maSP);
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamSession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Product> getAllSanPham() {
        DBConnect conn = new DBConnect();
        List<Product> list = new ArrayList<>();
        try {
             list = conn.getAllProduct();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamSession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

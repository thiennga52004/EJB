/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package sessionbean;

import Entity.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Acer
 */
@Local
public interface SanPhamSessionLocal {
    public void addSanPham(Product sp);
    public void updateSanPham(Product sp);
    public void deleteSanPham(int maSP);
    public List<Product> getAllSanPham();
}

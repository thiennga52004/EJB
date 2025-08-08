/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Acer
 */
public class Product {
    private int maSP;
    private String tenSP;
    private double gia;
    private String moTa;
    public Product(String tenSP,double gia, String moTa){
        this.tenSP=tenSP;
        this.gia=gia;
        this.moTa=moTa;
    }
    public Product(int maSP, String tenSP, double gia, String moTa){
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.moTa = moTa;
    }

    public Product() {
    }

    public int getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public double getGia() {
        return gia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
}

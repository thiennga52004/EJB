/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Date;

/**
 *
 * @author Acer
 */
public class Diary {
    private int id;
    private String loaiTacVu;
    private String tenSP;
    public Diary(){
    }
    public Diary(int id, String loaiTacVu, String tenSP){
    this.id=id;
    this.loaiTacVu=loaiTacVu;
    this.tenSP=tenSP;
    }

    public int getId() {
        return id;
    }

    public String getLoaiTacVu() {
        return loaiTacVu;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLoaiTacVu(String loaiTacVu) {
        this.loaiTacVu = loaiTacVu;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    
}

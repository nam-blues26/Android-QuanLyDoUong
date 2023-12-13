package edu.xda.adn.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill {
    private int maHDB;
    private String ngayLap;
    private int tongGia;
    private int soBan;

    public Bill() {
    }

    public Bill(int maHDB, String ngayLap, int tongGia, int soBan) {
        this.maHDB = maHDB;
        this.ngayLap = ngayLap;
        this.tongGia = tongGia;
        this.soBan = soBan;
    }

    public int getMaHDB() {
        return maHDB;
    }

    public void setMaHDB(int maHDB) {
        this.maHDB = maHDB;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getTongGia() {
        return tongGia;
    }

    public void setTongGia(int tongGia) {
        this.tongGia = tongGia;
    }

    public int getSoBan() {
        return soBan;
    }

    public void setSoBan(int soBan) {
        this.soBan = soBan;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "maHDB=" + maHDB +
                ", ngayLap=" + ngayLap +
                ", tongGia=" + tongGia +
                ", soBan=" + soBan +
                '}';
    }

}

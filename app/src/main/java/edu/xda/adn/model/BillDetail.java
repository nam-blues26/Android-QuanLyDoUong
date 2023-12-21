package edu.xda.adn.model;

public class BillDetail {

    private int maHDB;
    private int soLuong;
    private int giaDoUong;
    private String tenDoUong;
    private String size;

    public BillDetail() {
    }

    public BillDetail(int maHDB, int soLuong, int giaDoUong, String tenDoUong) {
        this.maHDB = maHDB;
        this.soLuong = soLuong;
        this.giaDoUong = giaDoUong;
        this.tenDoUong = tenDoUong;
    }

    public int getMaHDB() {
        return maHDB;
    }

    public void setMaHDB(int maHDB) {
        this.maHDB = maHDB;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaDoUong() {
        return giaDoUong;
    }

    public void setGiaDoUong(int giaDoUong) {
        this.giaDoUong = giaDoUong;
    }

    public String getTenDoUong() {
        return tenDoUong;
    }

    public void setTenDoUong(String tenDoUong) {
        this.tenDoUong = tenDoUong;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "BillDetail{" +
                "maHDB=" + maHDB +
                ", soLuong=" + soLuong +
                ", giaDoUong=" + giaDoUong +
                ", tenDoUong='" + tenDoUong + '\'' +
                '}';
    }
}

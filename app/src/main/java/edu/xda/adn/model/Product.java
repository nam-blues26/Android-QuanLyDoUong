package edu.xda.adn.model;

public class Product {
    private int maDoUong;
    private String tenDoUong;

    private String tenLoai;
    private int gia;
    private boolean trangThai;
    private int maLoai;

    public Product() {
    }

    public Product(int maDoUong, String tenDoUong, int gia, int maLoai,String tenLoai) {
        this.maDoUong = maDoUong;
        this.tenDoUong = tenDoUong;
        this.gia = gia;
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(int maDoUong) {
        this.maDoUong = maDoUong;
    }

    public String getTenDoUong() {
        return tenDoUong;
    }

    public void setTenDoUong(String tenDoUong) {
        this.tenDoUong = tenDoUong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public String toString() {
        return "Product{" +
                "maDoUong=" + maDoUong +
                ", tenDoUong='" + tenDoUong + '\'' +
                ", gia=" + gia +
                ", trangThai=" + trangThai +
                ", maLoai=" + maLoai +
                '}';
    }
}


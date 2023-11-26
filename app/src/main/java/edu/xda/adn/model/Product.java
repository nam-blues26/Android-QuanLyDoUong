package edu.xda.adn.model;

public class Product {
    private int maDoUong;
    private String tenDoUong;
    private int gia;
    private boolean trangThai;
    private String image;

    public Product() {
    }

    public Product(int maDoUong, String tenDoUong, int gia, String image) {
        this.maDoUong = maDoUong;
        this.tenDoUong = tenDoUong;
        this.gia = gia;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


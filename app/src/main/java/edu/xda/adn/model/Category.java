package edu.xda.adn.model;

public class Category {
    private int maLoai;
    private String tenLoai;

    public Category() {
    }

    public Category(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
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
        return "Category{" +
                "maLoai=" + maLoai +
                ", tenLoai='" + tenLoai + '\'' +
                '}';
    }
}

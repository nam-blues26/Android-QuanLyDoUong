package edu.xda.adn.model;

public class Statistical {
    private int soLuongHD;
    private int doanhThu;

    public Statistical() {
    }

    public Statistical(int soLuongHD, int doanhThu) {
        this.soLuongHD = soLuongHD;
        this.doanhThu = doanhThu;
    }

    public int getSoLuongHD() {
        return soLuongHD;
    }

    public void setSoLuongHD(int soLuongHD) {
        this.soLuongHD = soLuongHD;
    }

    public int getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(int doanhThu) {
        this.doanhThu = doanhThu;
    }

    @Override
    public String toString() {
        return "Statistical{" +
                "soLuongHD=" + soLuongHD +
                ", doanhThu=" + doanhThu +
                '}';
    }
}

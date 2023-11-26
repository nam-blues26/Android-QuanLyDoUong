package edu.xda.adn.model;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable {
    private int id;
    private Date createDate;
    private String note;
    private double totalPrice;
    private String username;

    public Invoice() {
    }

    public Invoice(int id, Date createDate, String note, double totalPrice, String username) {
        this.id = id;
        this.createDate = createDate;
        this.note = note;
        this.totalPrice = totalPrice;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", note='" + note + '\'' +
                ", totalPrice=" + totalPrice +
                ", username='" + username + '\'' +
                '}';
    }
}

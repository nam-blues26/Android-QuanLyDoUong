package edu.xda.adn.model;

public class InvoiceDetailDB {
    private int id_invoice;
    private int id_product;
    private double singlePrice;
    private int quantity;
    private String username;

    public InvoiceDetailDB() {
    }

    public InvoiceDetailDB(int id_invoice, int id_product, double singlePrice, int quantity, String username) {
        this.id_invoice = id_invoice;
        this.id_product = id_product;
        this.singlePrice = singlePrice;
        this.quantity = quantity;
        this.username = username;
    }

    public int getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(int id_invoice) {
        this.id_invoice = id_invoice;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
                "id_invoice=" + id_invoice +
                ", id_product=" + id_product +
                ", singlePrice=" + singlePrice +
                ", quantity=" + quantity +
                ", username='" + username + '\'' +
                '}';
    }
}

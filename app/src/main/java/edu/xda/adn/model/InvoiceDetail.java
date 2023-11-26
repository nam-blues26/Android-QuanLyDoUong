package edu.xda.adn.model;

public class InvoiceDetail {
    private Invoice invoice;
    private Product product;
    private double singlePrice;
    private int quantity;
    private String username;

    public InvoiceDetail() {
    }

    public InvoiceDetail(Invoice invoice, Product product, double singlePrice, int quantity, String username) {
        this.invoice = invoice;
        this.product = product;
        this.singlePrice = singlePrice;
        this.quantity = quantity;
        this.username = username;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
                "invoice=" + invoice +
                ", product=" + product +
                ", singlePrice=" + singlePrice +
                ", quantity=" + quantity +
                ", username='" + username + '\'' +
                '}';
    }
}

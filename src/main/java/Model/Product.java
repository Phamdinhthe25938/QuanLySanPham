package Model;

public class Product {
    private int id;

    private String name;

    private String linkImg;

    private double price;

    public Product() {
    }

    public Product(int id, String name, String linkImg, double price) {
        this.id = id;
        this.name = name;
        this.linkImg = linkImg;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

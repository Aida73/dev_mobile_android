package sn.ept.devmobile.finalprojectandroid.models;

import com.google.gson.annotations.Expose;

import java.text.DecimalFormat;

public class Product {

    @Expose
    Long id;

    @Expose
    String title;

    @Expose
    String category;

    @Expose
    String description;

    @Expose
    Double price;

    @Expose
    String image;

    private static final DecimalFormat decimalformat = new DecimalFormat("0.00");

    public Product() {
    }

    public Product(Long id, String title, String category, String description, Double price, String image) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Product(String s) {
        this.title = s;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public String formatPrice(){
        return decimalformat.format(this.price*655)+"FCFA";
    }
}

package org.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private String price;

    @Column(name = "website_url", length = 5000)
    private String websiteUrl;

    @ManyToOne
    @JoinColumn(name = "watch_id")
    private Name name;

    // Constructors, getters, setters...

    public Price() {
        // Default constructor required by Hibernate
    }

    public Price(String price, String websiteUrl) {
        this.price = price;
        this.websiteUrl = websiteUrl;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}

package org.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "name")
public class Name {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 5000) // Adjust the length accordingly
    private String name;

    @Column(name = "image_url", columnDefinition = "VARCHAR(255) DEFAULT 'default_image_url'")
    private String imageUrl;

    // Constructors, getters, setters...

    public Name() {
        // Default constructor required by Hibernate
    }

    public Name(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

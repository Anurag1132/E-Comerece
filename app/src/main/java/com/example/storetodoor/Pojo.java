package com.example.storetodoor;

public class Pojo {

    String firstName,lastName,mobilenumber,City,price,nameofgrocery, imageUrl,email,category,Description;

    public Pojo(String firstName, String lastName, String mobilenumber, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilenumber = mobilenumber;
        City = city;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Pojo(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNameofgrocery() {
        return nameofgrocery;
    }

    public void setNameofgrocery(String nameofgrocery) {
        this.nameofgrocery = nameofgrocery;
    }

    public Pojo() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }


}
package com.example.storetodoor;

public class Pojo {

    String firstName,modeOfDelivery,lastName,mobilenumber,City,price,nameofgrocery, imageUrl,Email,category,Description,Aboutme, id,quantity,Address,Status,TotalPrice,userId,Message,AdminMessage;

    public Pojo(String price, String nameofgrocery, String imageUrl, String email, String category, String description) {
        this.price = price;
        this.nameofgrocery = nameofgrocery;
        this.imageUrl = imageUrl;
        this.Email = email;
        this.category = category;
        this.Description = description;
    }

    public Pojo(String firstName, String lastName, String mobilenumber, String city, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilenumber = mobilenumber;
        City = city;
        Email = email;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getModeOfDelivery() {
        return modeOfDelivery;
    }

    public void setModeOfDelivery(String modeOfDelivery) {
        this.modeOfDelivery = modeOfDelivery;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAboutme() {
        return Aboutme;
    }

    public void setAboutme(String aboutme) {
        Aboutme = aboutme;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public String getAdminMessage() {
        return AdminMessage;
    }

    public void setAdminMessage(String adminMessage) {
        AdminMessage = adminMessage;
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
